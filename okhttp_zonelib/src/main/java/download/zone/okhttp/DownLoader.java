package download.zone.okhttp;
import download.zone.okhttp.UIhelper.UIhelper;
import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public enum DownLoader {
    INSTANCE;

    private static final int CONTAIN_THREAD_COUNT = 5;
    private boolean debug=false;
    public ExecutorService executorService = Executors.newCachedThreadPool();
    public OkHttpClient mOkHttpClient=new OkHttpClient();

    public  void startTask(String url, File targetFolder) {
        startTask(url, targetFolder, null,null);
    }
    public  void startTask(String url, File targetFolder,DownloadListener downloadListener) {
        startTask(url, targetFolder, null,downloadListener);
    }
    public  void startTask(String url, File targetFolder, String rename) {
        startTask(url, targetFolder, rename,null);
    }
    public  void startTask(final String urlString, final File targetFolder, final String rename, final DownloadListener downloadListener) {
        executorService.execute(new Runnable() {
            //todo 判断是否有此url  与其 状态
            long totalLength = 0;
            int threadCount = CONTAIN_THREAD_COUNT;
            boolean isRange = false;
            String fileName = "";
            DownloadInfo downloadInfo = new DownloadInfo();
            UIhelper uiHelper=new UIhelper(downloadListener);
            @Override
            public void run() {
                uiHelper.onStart();
                downloadInfo.setUrl(urlString);
                try {
                    downloadInfo.setTargetFolder(targetFolder.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Request request = new Request.Builder().url(urlString).build();
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
                        isRange=true;
                    writeLog("Accept-Ranges   " + rangeStr);
                    writeLog("----文件总长度----" + totalLength);

                    //这里是找到文件名
                    if (rename != null) {
                        fileName = rename;
                    } else {
                        String[] lin = urlString.split("[/]");
                        for (int i = lin.length - 1; i >= 0; i--) {
                            if (lin[i].contains(".")) {
                                fileName = lin[i];
                                break;
                            }

                        }
                    }


                    int supportStatue = Integer.parseInt(rangeStr);
                    File saveOutFile = new File(targetFolder, fileName);
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
                    if(!isRange){
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
                        executorService.execute(new DownLoadTask(startIndex, endIndex, urlString,
                                saveOutFile, threadId,threadInfo,uiHelper));
                    }

                }
            }
        });
    }



    public void pauseTask(){

    }
    public void deleteTask(){

    }
    public void writeLog(String str){
        if (debug) {
            System.out.println(str);
        }
    }
}
