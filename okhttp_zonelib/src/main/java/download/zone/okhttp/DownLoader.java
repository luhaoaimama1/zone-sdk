package download.zone.okhttp;

import android.content.Context;

import download.zone.okhttp.callback.DownloadCallback;
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
    private static Dbhelper dbhelper= new Dbhelper();
    private static final int CONTAIN_THREAD_COUNT = 3;
    private static boolean debug = true;
    private ExecutorService ConcurrentExecutor;
    private ExecutorService serialExecutor;
    protected OkHttpClient mOkHttpClient = new OkHttpClient();
    public static Map<String,DownloadInfo> downLoadInfoMap= new ConcurrentHashMap<>();

    private DownLoader() {
        ConcurrentExecutor = Executors.newCachedThreadPool();
        serialExecutor = Executors.newSingleThreadExecutor();
    }

    public static DownLoader getInstance(Context context) {
        dbhelper.initDb(context);
        return ourInstance;
    }


    public void startTask(String url, File targetFolder) {
        startTask(url, targetFolder, null, null);
    }

    public void startTask(String url, File targetFolder, DownloadCallback downloadListener) {
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
    public synchronized void startTask(final String urlString, final File targetFolder, final String rename, final DownloadCallback downloadListener) {
        DownloadInfo downloadInfo =downLoadInfoMap.get(urlString);
        if (downloadInfo != null) {
            //内存中有任务
            if (downloadInfo.isDeleteing())
                writeLog("任务正在删除中---------------------------------------");
            else if (downloadInfo.isSaving())
                writeLog("数据库正在保存中---------------------------------------");
            else {
                switch (downloadInfo.getTaskState()) {
                    //todo 暂时不做 内存缓存  暂停的时候内存中就移除
//                    case DownloadInfo.PAUSE:
//                        //内存中暂停了  续传
//                        runTask(urlString, targetFolder, rename, downloadListener);
//                        break;
                    case DownloadInfo.DOWNLOADING:
                        writeLog("任务正在下载中---------------------------------------");
                        break;
                }
            }
        }else
            //内存中没有任务
            runTask(urlString, targetFolder, rename, downloadListener);
    }



    private synchronized void runTask(final String urlString, final File targetFolder, final String rename, final DownloadCallback downloadListener) {
//        dbhelper.getUrlDbState_IsOpenMap().put(urlString, false);//设置db没保存  除非异常 不然就算出现 error自己定义的也会走保存
        //除非一种情况就是  这个url的任务已经运行的时候 那么 设置上面的tag也是没事  数据库也不会保存
        serialExecutor.execute(new Runnable() {
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
                File saveOutFile = new File(targetFolder, getTempFileName(fileName));

                //找到数据库任务
                DownloadInfo task = null;
                task = dbhelper.queryTask(urlString);
                uiHelper = new UIhelper(downloadListener);
                if (task != null) {
                    //TODO 如果是 非断点的那种呢 没做
                    //有了就直接下被
                    downloadInfo = task;
                } else
                    //没有下载过 就直接下载好了
                    firstTask2InitInfo(saveOutFile);
                downloadInfo.setDownloadListener(downloadListener);
                downloadInfo.setUihelper(uiHelper);

                //  加到内存中
                downLoadInfoMap.put(urlString, downloadInfo);
                //开启线程 跑任务
                uiHelper.onStart();
                for (ThreadInfo threadInfo : downloadInfo.getThreadInfo()){
                    if (!threadInfo.isComplete()) {
                        ConcurrentExecutor.execute(new DownLoadTask(saveOutFile, threadInfo, uiHelper, ourInstance, dbhelper));
                        threadInfo.setStoping(false);
                    }
                }
            }


            private void firstTask2InitInfo(File saveOutFile) {
                downloadInfo = new DownloadInfo();
                downloadInfo.setUrl(urlString);
                try {
                    downloadInfo.setTargetFolder(targetFolder.getCanonicalPath());
                } catch (IOException e) {
                    ExceptionHelper.quiet(e);
                }
                Request request = new Request.Builder().url(urlString).tag(urlString).build();
                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    ExceptionHelper.quiet(e);
                }
                if (response == null || !response.isSuccessful()) {
                    uiHelper.onError(response);
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
                        ExceptionHelper.quiet(e);
                    } catch (IOException e) {
                        ExceptionHelper.quiet(e);
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
                        if (threadId == threadCount)
                            endIndex = totalLength;
                        writeLog("----threadId---" + threadId+ "--startIndex--" + startIndex + "--endIndex--" + endIndex);
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
    public synchronized boolean stopTask(String urlString) {
        DownloadInfo downLoadInfo = downLoadInfoMap.get(urlString);
        if (downLoadInfo == null) {
            writeLog("任务未开始(内存中没了 db里可能有 总之内存中没有 )");
        } else {
            if (downLoadInfo.getTaskState() == DownloadInfo.DOWNLOADING) {
                downLoadInfo.setThreadWork(false);//只是为了让流停止 节省流浪
                if(downLoadInfo.getUihelper()!=null)
                    downLoadInfo.getUihelper().onPause(dbhelper);//这个则是保存数据 和前台显示的一样 但是和内容真正的不一样但是不影响
                writeLog("停止任务 url：" + urlString);
                return true;
            }
        }
        return false;
    }


    /**
     * 删除数据库数据 既是下载未完成的 因为完成的db都不存在了  并删除本地文件
     * @param urlString
     */
    public synchronized void deleteTask(final String urlString) {
        ConcurrentExecutor.execute(new Runnable() {
            @Override
            public void run() {
                DownloadInfo downLoadInfo = downLoadInfoMap.get(urlString);
                if (downLoadInfo!= null&&!downLoadInfo.isDeleteing()) {
                    downLoadInfo.setDeleteing(true);
                    downLoadInfo.setThreadWork(false);//这个不会保存 db
                    DownloadInfo downloadInfo = dbhelper.queryTask(urlString);

                    //既然是未完成的 都是临时文件
                    File saveOutFile = new File(downloadInfo.getTargetFolder(), getTempFileName(downloadInfo.getTargetName()));
                    if (saveOutFile != null && saveOutFile.exists())
                        saveOutFile.delete();
                    dbhelper.deleteTaskOnly(downloadInfo);//db删除
                    downloadInfo.getUihelper().onDelete();
                    downLoadInfo.setDeleteing(false);
                    CleanMemoryUtils.clear(urlString);
                }
            }
        });
    }

    protected static void writeLog(String str) {
        if (debug) {
            System.out.println(str);
        }
    }

    private String getTempFileName(String mfileName) {
        String[] names = mfileName.split("[.]");
        return names[0]+".temp";
    }
}
