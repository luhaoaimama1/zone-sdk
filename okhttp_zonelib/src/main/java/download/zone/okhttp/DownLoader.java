package download.zone.okhttp;

import android.content.Context;

import download.zone.okhttp.helper.Dbhelper;
import download.zone.okhttp.helper.UIhelper;
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
 * TODO 重命名文件
 * TODO   暂停开始  来回弄会有bug  同步不是很懂
 * Created by Zone on 2016/2/14.
 */
public class DownLoader {
    private static DownLoader ourInstance = new DownLoader();
    private static Dbhelper dbhelper= new Dbhelper(ourInstance);
    private static final int CONTAIN_THREAD_COUNT = 3;
    private static boolean debug = true;
    public ExecutorService executorService;
    public OkHttpClient mOkHttpClient = new OkHttpClient();
    private Map<String,Integer> taskRunning;//下载中的 downloadInfo
    private Map<String,Integer> taskStatuMap;//下载中的 downloadInfo
    private Map<String,Boolean> makesureRunningTaskMap;//下载中的 downloadInfo

    private DownLoader() {
        executorService = Executors.newCachedThreadPool();
        taskStatuMap = new ConcurrentHashMap<>();
        taskRunning = new ConcurrentHashMap<>();
        makesureRunningTaskMap = new ConcurrentHashMap<>();
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
                setStartTag(urlString);
                runTask(urlString, targetFolder, rename, downloadListener);
            } else if (taskStatuMap.get(urlString) != null && taskStatuMap.get(urlString) == DownloadInfo.PAUSE) {
                if (dbhelper.getSaveStateMap().get(urlString) != null && dbhelper.getSaveStateMap().get(urlString)) {
                    //                暂停的时候 db保存完毕才可以继续
                    setStartTag(urlString);
                    runTask(urlString, targetFolder, rename, downloadListener);
                } else {
                    writeLog("数据库保存完毕才可以 继续任务");
                }

            } else {
                writeLog("任务正在下载中---------------------------------------");
            }
    }

    private void setStartTag(String urlString) {
        taskStatuMap.put(urlString, DownloadInfo.DOWNLOADING);//设置url这个标志 正在下载中
        dbhelper.getSaveStateMap().put(urlString, false);//设置横没保存
    }

    private void runTask(final String urlString, final File targetFolder, final String rename, final DownloadListener downloadListener) {
        executorService.execute(new Runnable() {
            long totalLength = 0;
            int threadCount = CONTAIN_THREAD_COUNT;
            boolean isRange = false;
            String fileName = "";
            DownloadInfo downloadInfo;
            UIhelper uiHelper = new UIhelper(ourInstance, downloadListener);

            @Override
            public void run() {
                //这里是找到文件名
                if (rename != null)
                    fileName = rename;
                else
                    fileName = getFileNameByUrl(urlString);
                File saveOutFile = new File(targetFolder, fileName);
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
                        //没有 在就改成下载中
                    } else
                        //没有下载过 就直接下载好了
                        firstTask2InitInfo(saveOutFile);
                    taskRunning.put(urlString, downloadInfo.getThreadInfo().size());
                    //开启线程 跑任务
                    for (ThreadInfo threadInfo : downloadInfo.getThreadInfo())
                        executorService.execute(new DownLoadTask(new File(downloadInfo.getTargetFolder(), downloadInfo.getTargetName())
                                , threadInfo, uiHelper, ourInstance, dbhelper));
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

    protected synchronized  void tryRemoveTask(String urlString){
        if (taskRunning.get(urlString)!=null) {
            int result = taskRunning.get(urlString) - 1;
            if(result==0){
                taskRunning.remove(urlString);
                makesureRunningTaskMap.remove(urlString);
            }else{
                taskRunning.put(urlString,result);
            }
        }

    }
    protected synchronized  void makesureRunningTask(String urlString){
        makesureRunningTaskMap.put(urlString,true);

    }
    //todo 暂停的时候没保存数据库   即没有那层循环
    public void stopTask(String urlString) {
        if (taskStatuMap.get(urlString) == null) {
            writeLog("任务未开始");
        } else {
            if (taskStatuMap.get(urlString) == DownloadInfo.DOWNLOADING) {
                //下载中 并且状态是可以暂停的时候才能暂停
                if(makesureRunningTaskMap.get(urlString)!=null&&makesureRunningTaskMap.get(urlString)){
                    //真正跑起来才可以  停止
                    taskStatuMap.put(urlString, DownloadInfo.PAUSE);
                    writeLog("停止任务 url：" + urlString);
                }
            } else if (taskStatuMap.get(urlString) == DownloadInfo.PAUSE) {
                writeLog("任务已经暂停");
            } else {
                writeLog("任务已经完成");
            }
        }
    }


    /**
     * 删除数据库数据  并删除本地文件
     *
     * @param urlString
     */
    public void deleteTask(String urlString) {
        DownloadInfo downloadInfo = getTaskInfo(urlString);
        if (downloadInfo!=null) {
            if(taskStatuMap.get(urlString)!=null)
                taskStatuMap.remove(urlString);
            File saveOutFile = new File(downloadInfo.getTargetFolder(), downloadInfo.getTargetName());
            if (saveOutFile != null && saveOutFile.exists())
                saveOutFile.delete();
            dbhelper.deleteTask(downloadInfo);
        }

    }

    //TODO 这个是否应该用handler去异步请求呢 毕竟查询数据库了。。。
    public DownloadInfo getTaskInfo(String urlString) {
        return dbhelper.queryTask(urlString);
    }

    public static void writeLog(String str) {
        if (debug) {
            System.out.println(str);
        }
    }



    public Map<String, Integer> getTaskStatuMap() {
        return taskStatuMap;
    }

    public void setTaskStatuMap(Map<String, Integer> taskStatuMap) {
        this.taskStatuMap = taskStatuMap;
    }

}
