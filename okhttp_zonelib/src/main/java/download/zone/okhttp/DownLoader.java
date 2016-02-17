package download.zone.okhttp;

import android.content.Context;

import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public class DownLoader {
    private static DownLoader ourInstance = new DownLoader();
    private static Dbhelper dbhelper= new Dbhelper(ourInstance);
    private static final int CONTAIN_THREAD_COUNT = 3;
    private static boolean debug = true;
    private ExecutorService executorService;
    protected OkHttpClient mOkHttpClient = new OkHttpClient();
    /**
     *  为了一个url只有一个task运行
     */
    private Map<String,Integer> taskRunning;
    /**
     * 保存任务的状态
     */
    private Map<String,Integer> taskStatuMap;
    /**
     * 内存中的任务  暂停状态也算  只有完成的时候才需要清除
     */
    private Map<String,UIhelper> taskUIhelperMap;

    private DownLoader() {
        executorService = Executors.newCachedThreadPool();
        taskStatuMap = new ConcurrentHashMap<>();
        taskRunning = new ConcurrentHashMap<>();
        taskUIhelperMap = new ConcurrentHashMap<>();
    }

    public static DownLoader getInstance(Context context) {
        dbhelper.initDb(context);
        return ourInstance;
    }


    public void startTask(String url, File targetFolder) {
        startTask(url, targetFolder, null, null);
    }

    public void startTask(String url, File targetFolder, DownloadListener downloadListener) {
        startTask(url, targetFolder, null, downloadListener);
    }

    public void startTask(String url, File targetFolder, String rename) {
        startTask(url, targetFolder, rename, null);
    }

    /**
     * 如果 db中有urlString 则继续按照数据库中的下载 路径也是数据库中的
     * 下载完成后 则自动删除数据库的数据
     *
     * @param urlString
     * @param targetFolder
     * @param rename
     * @param downloadListener
     */
    public void startTask(final String urlString, final File targetFolder, final String rename, final DownloadListener downloadListener) {
        //TODO 在这里 主线程中 维护个 start  pause的map
        //开始有两种  一个是pause  一个是第一次开始任务（包括第一续传）
            if (taskStatuMap.get(urlString) == null) {
                //第一次开始任务（包括第一续传）
                runTask(urlString, targetFolder, rename, downloadListener);
            } else if (taskStatuMap.get(urlString) != null && taskStatuMap.get(urlString) == DownloadInfo.PAUSE) {
                if (dbhelper.getSaveStateMap().get(urlString) != null && dbhelper.getSaveStateMap().get(urlString)) {
                    //                暂停的时候 db保存完毕才可以继续
                    runTask(urlString, targetFolder, rename, downloadListener);
                } else {
                    writeLog("数据库保存完毕才可以 继续任务");
                }

            } else if(taskStatuMap.get(urlString) != null && taskStatuMap.get(urlString) == DownloadInfo.DELETE) {
                writeLog("任务正在删除中---------------------------------------");
            }else {
                writeLog("任务正在下载中---------------------------------------");
            }
    }


    private void runTask(final String urlString, final File targetFolder, final String rename, final DownloadListener downloadListener) {
        //todo 这个tag的时机
        taskStatuMap.put(urlString, DownloadInfo.DOWNLOADING);//设置url这个标志 正在下载中
        dbhelper.getSaveStateMap().put(urlString, false);//设置db没保存  除非异常 不然就算出现 error自己定义的也会走保存
        //除非一种情况就是  这个url的任务已经运行的时候 那么 设置上面的tag也是没事  数据库也不会保存
        executorService.execute(new Runnable() {
            long totalLength = 0;
            int threadCount = CONTAIN_THREAD_COUNT;
            boolean isRange = false;
            String fileName = "";
            DownloadInfo downloadInfo;
            UIhelper uiHelper;

            @Override
            public void run() {
                //这里是找到文件名
                if (rename != null)
                    fileName = rename;
                else
                    fileName = getFileNameByUrl(urlString);
                getTempFileName(fileName);
                File saveOutFile = new File(targetFolder, getTempFileName(fileName));
                //如果在内存中就发现了任务  就不要在下载了
                synchronized (DownLoader.class) {
                    if (taskRunning.get(urlString) != null)//确定只有一个线程
                        return;
                    //找到数据库任务
                    DownloadInfo task = null;
                    task = dbhelper.queryTask(urlString);
                    if (task != null) {
                        //TODO 如果是 非断点的那种呢 没做
                        //有了就直接下被
                        downloadInfo = task;
                        if(downloadInfo.getThreadInfo().size()==0){
                            dbhelper.deleteTaskOnly(downloadInfo);
                            //没有下载过 就直接下载好了
                            firstTask2InitInfo(saveOutFile);
                        }
                    } else
                        //没有下载过 就直接下载好了
                        firstTask2InitInfo(saveOutFile);
                    downloadInfo.setDownloadListener(downloadListener);
                    taskRunning.put(urlString, downloadInfo.getThreadInfo().size());
                    uiHelper = new UIhelper(ourInstance, downloadListener, downloadInfo);
                    taskUIhelperMap.put(urlString, uiHelper);
                    //开启线程 跑任务

                    uiHelper.onStart();
                    for (ThreadInfo threadInfo : downloadInfo.getThreadInfo())
                        executorService.execute(new DownLoadTask(saveOutFile, threadInfo, uiHelper, ourInstance, dbhelper));
                }
            }


            private void firstTask2InitInfo(File saveOutFile) {
                downloadInfo = new DownloadInfo();
                downloadInfo.setUrl(urlString);
                try {
                    downloadInfo.setTargetFolder(targetFolder.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Request request = new Request.Builder().url(urlString).tag(urlString).build();
                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response == null || !response.isSuccessful()) {
                    uiHelper.onError(response);
                    writeLog("下载文件失败了~ code=" + response.code() + "url=" + urlString);
                } else {
                    // 服务器返回的数据的长度，实际就是文件的长度
                    totalLength = response.body().contentLength();
                    String rangeStr = response.header("Accept-Ranges");
                    if (rangeStr != null)
                        isRange = true;
                    writeLog("Accept-Ranges   " + rangeStr);
                    writeLog("----文件总长度----" + totalLength);
                    try {
                        RandomAccessFile raf = new RandomAccessFile(
                                saveOutFile, "rwd");
                        // 指定创建的这个文件的长度
                        raf.setLength(totalLength);
                        writeLog("byte文件长度：" + totalLength);
                        // 关闭raf
                        raf.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!isRange) {
                        threadCount = 1;
                    }
                    downloadInfo.setTotalLength(totalLength);
                    downloadInfo.setRange(isRange);
                    downloadInfo.setTargetName(fileName);
                    long blockSize = (long) Math.ceil(totalLength / threadCount);
                    for (int threadId = 1; threadId <= threadCount; threadId++) {
                        ThreadInfo threadInfo = new ThreadInfo();
                        // 计算每个线程下载的开始位置和结束位置
                        long startIndex = (threadId - 1) * blockSize;
                        long endIndex = threadId * blockSize - 1;
                        if (threadId == threadCount) {
                            endIndex = totalLength;
                        }
                        writeLog("----threadId---" + threadId
                                + "--startIndex--" + startIndex
                                + "--endIndex--" + endIndex);
                        threadInfo.setStartIndex(startIndex);
                        threadInfo.setEndIndex(endIndex);
                        threadInfo.setThreadId(threadId);
                        threadInfo.setDownloadInfo(downloadInfo);
                        downloadInfo.getThreadInfo().add(threadInfo);
                    }
                }
            }

            private String getFileNameByUrl(String urlString) {
                String[] lin = urlString.split("[/]");
                for (int i = lin.length - 1; i >= 0; i--) {
                    if (lin[i].contains("."))
                        return lin[i];
                }
                throw new IllegalStateException("not found  file name!");
            }


        });
    }


    // 暂停的时候直接保存数据  更新进度 不再循环中更新了
    public boolean stopTask(String urlString) {
        if (taskStatuMap.get(urlString) == null) {
            writeLog("任务未开始");
        } else {
            if (taskStatuMap.get(urlString) == DownloadInfo.DOWNLOADING) {
                //下载中 并且状态是可以暂停的时候才能暂停
                    //真正跑起来才可以  停止
                    taskStatuMap.put(urlString, DownloadInfo.PAUSE);//只是为了让流停止 节省流浪
                    if(taskUIhelperMap.get(urlString)!=null)
                        taskUIhelperMap.get(urlString).onPause(dbhelper);//这个则是保存数据 和前台显示的一样 但是和内容真正的不一样但是不影响
                    writeLog("停止任务 url：" + urlString);
                    return true;
            } else if (taskStatuMap.get(urlString) == DownloadInfo.PAUSE) {
                writeLog("任务已经暂停");
            } else  if (taskStatuMap.get(urlString) == DownloadInfo.DELETE) {
                writeLog("任务已经删除");
            } else {
                writeLog("任务已经完成");
            }
        }
        return false;
    }


    /**
     * 删除数据库数据 既是下载未完成的 因为完成的db都不存在了  并删除本地文件
     * @param urlString
     */
    public synchronized void deleteTask(final String urlString) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskStatuMap.put(urlString, DownloadInfo.DELETE);
                while (taskRunning.get(urlString) != null) {
                    //知道完成 urlString 任务停止的时候才
                }
                DownloadInfo downloadInfo = dbhelper.queryTask(urlString);
                if (downloadInfo != null) {
                    //既然是未完成的 都是临时文件
                    File saveOutFile = new File(downloadInfo.getTargetFolder(), getTempFileName(downloadInfo.getTargetName()));
                    if (saveOutFile != null && saveOutFile.exists())
                        saveOutFile.delete();
                    dbhelper.deleteTaskOnly(downloadInfo);
                }
                if (taskUIhelperMap.get(urlString) != null)
                    taskUIhelperMap.get(urlString).onDelete();
                clearUrlMemory(urlString);
            }
        });
    }


    /**
     * 为了一个url只有一个task运行
     * @param urlString
     */
    protected synchronized  void tryRemoveTask(String urlString){
        if (taskRunning.get(urlString)!=null) {
            int result = taskRunning.get(urlString) - 1;
            if(result==0){
                taskRunning.remove(urlString);
            }else{
                taskRunning.put(urlString,result);
            }
        }

    }

    /**
     * @param urlString
     */
    protected void clearUrlMemory(String urlString){
        if(taskStatuMap.get(urlString)!=null)
            taskStatuMap.remove(urlString);
        if(taskUIhelperMap.get(urlString)!=null)
            taskUIhelperMap.remove(urlString);
        if( UIhelper.getTaskPauseProgressMap().get(urlString)!=null)
            UIhelper.getTaskPauseProgressMap().remove(urlString);
        if( dbhelper.getSaveStateMap().get(urlString)!=null)
            dbhelper.getSaveStateMap().remove(urlString);
    }


    protected static void writeLog(String str) {
        if (debug) {
            System.out.println(str);
        }
    }

    protected Map<String, Integer> getTaskStatuMap() {
        return taskStatuMap;
    }
    private String getTempFileName(String mfileName) {
        String[] names = mfileName.split("[.]");
        return names[0]+".temp";
    }
}
