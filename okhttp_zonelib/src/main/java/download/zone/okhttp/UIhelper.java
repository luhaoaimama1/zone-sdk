package download.zone.okhttp;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public class UIhelper {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Map<String,Float> taskPauseProgressMap=new ConcurrentHashMap<>();;//下载中的 downloadInfo
    private  DownloadInfo downloadInfo;
    private  DownloadListener downloadListener;
    private  DownLoader ourInstance;
    private OnProgressRunnable onProgressRunnable;
    private OnPauseRunnable onPauseRunnable;

    public UIhelper( DownLoader ourInstance,DownloadListener downloadListener, DownloadInfo downloadInfo) {
        this.ourInstance = ourInstance;
        this.downloadListener=downloadListener;
        this.downloadInfo=downloadInfo;
    }


    public void onError(final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener!=null)
                    downloadListener.onError(response);
            }
        });
    }

    public void onStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener!=null)
                    downloadListener.onStart();
            }
        });
    }
    public void onDelete() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener!=null)
                    downloadListener.onDelete();
            }
        });
    }

    public  void onProgress(ThreadInfo threadInfo) {
        synchronized (UIhelper.class) {
            if (onProgressRunnable==null)
                onProgressRunnable=new OnProgressRunnable(threadInfo);
        }
        mHandler.post(onProgressRunnable);
    }

    public void onPause(ThreadInfo threadInfo, Dbhelper dbhelper) {
        synchronized (UIhelper.class) {
            if (onPauseRunnable==null)
                onPauseRunnable=new OnPauseRunnable(threadInfo, dbhelper);
        }
        mHandler.post(onPauseRunnable);
    }



    public class OnProgressRunnable implements Runnable {
//        private ThreadInfo threadInfo;
        private long lastDownLoadTotalLength;
        private long lastRefreshUiTime;
        //暂停的时候保存信息 然后的都不走了
        public OnProgressRunnable(ThreadInfo threadInfo) {
//            this.threadInfo = threadInfo;
            lastDownLoadTotalLength = 0;
            lastRefreshUiTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
                Map<String, Integer> statuMap = ourInstance.getTaskStatuMap();
//                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                if (statuMap.get(downloadInfo.getUrl()) == DownloadInfo.DOWNLOADING) {
                    //这里计算进度 ，下载长度
                    long downLoadTotalLength = 0;
                    for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                        downLoadTotalLength += info.getDownloadLength();
                    }
                    float progress = downLoadTotalLength * 1F / downloadInfo.getTotalLength();
                    downloadInfo.setProgress(progress);
                    downloadInfo.setDownloadLength(downLoadTotalLength);
                    //计算速度  超过一秒算一次   kb/s   downLoadTotalLength - lastDownLoadTotalLength 是bytes 不是kb
                    long refreshUiTime = System.currentTimeMillis();
                    if (refreshUiTime - lastRefreshUiTime > 1000) {
                        long networkSpeed = ((downLoadTotalLength - lastDownLoadTotalLength) * 1000L) /
                                ((refreshUiTime - lastRefreshUiTime) * 1024);
                        System.out.println("networkSpeed----------------:"+networkSpeed);
                        downloadInfo.setNetworkSpeed(networkSpeed);
                        lastRefreshUiTime = refreshUiTime;
                        lastDownLoadTotalLength = downLoadTotalLength;
                    }
                    if (statuMap.get(downloadInfo.getUrl()) == DownloadInfo.DOWNLOADING){
                        if (downloadListener != null)
                            if(taskPauseProgressMap.get(downloadInfo.getUrl())==null||downloadInfo.getProgress()>=taskPauseProgressMap.get(downloadInfo.getUrl()))
                                downloadListener.onProgress((int)(downloadInfo.getProgress()*100),downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息

                    }
            }

        }
    };
    //暂停 一个任务几个线程就可能走几次 但是 db只会存最后一次
    public class OnPauseRunnable implements Runnable {
        private Dbhelper dbhelper;
//        private ThreadInfo threadInfo;

        //暂停的时候保存信息 然后的都不走了
        public OnPauseRunnable(ThreadInfo threadInfo, Dbhelper dbhelper) {
//            this.threadInfo = threadInfo;
            this.dbhelper = dbhelper;
        }

        @Override
        public void run() {
            synchronized (UIhelper.this) {
//                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                //这里计算进度 ，下载长度
                long downLoadTotalLength = 0;
                for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                    downLoadTotalLength += info.getDownloadLength();
                }
                float progress = downLoadTotalLength * 1F / downloadInfo.getTotalLength();
                downloadInfo.setProgress(progress);
                downloadInfo.setDownloadLength(downLoadTotalLength);
                //暂停的时候 速度归零
                downloadInfo.setNetworkSpeed(0);
                Map<String, Integer> statuMap = ourInstance.getTaskStatuMap();
                if (statuMap.get(downloadInfo.getUrl()) == DownloadInfo.PAUSE) {
                    if (downloadListener != null)
                        downloadListener.onProgress((int)(downloadInfo.getProgress()*100),downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息
                    dbhelper.saveTask(downloadInfo);//db保存信息
                    taskPauseProgressMap.put(downloadInfo.getUrl(), downloadInfo.getProgress());//暂停进度信息保存
                    downloadListener.onStop();
                }

            }
        }
    };

    /**
     * pause的时候也会走此方法
     * @param threadInfo
     * @param dbhelper
     * @param saveOutFile
     */
    public void onFinish(final ThreadInfo threadInfo, final Dbhelper dbhelper, final File saveOutFile) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                    if (!info.isComplete())
                        return;
                }
                //完成以后
                downloadInfo.setIsDone(true);
                //从命名
                saveOutFile.renameTo(new File(downloadInfo.getTargetFolder(),downloadInfo.getTargetName()));
                taskPauseProgressMap.remove(downloadInfo.getUrl());//移除key
                dbhelper.deleteTask(downloadInfo);//db移除信息
                if (downloadListener!=null)
                    downloadListener.onProgress((int)(downloadInfo.getProgress()*100),downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息
            }
        });
    }

    public static Map<String, Float> getTaskPauseProgressMap() {
        return taskPauseProgressMap;
    }

    public static void setTaskPauseProgressMap(Map<String, Float> taskPauseProgressMap) {
        UIhelper.taskPauseProgressMap = taskPauseProgressMap;
    }
}
