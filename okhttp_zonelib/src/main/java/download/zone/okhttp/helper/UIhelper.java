package download.zone.okhttp.helper;

import android.os.Handler;
import android.os.Looper;
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
    private DownloadListener downloadListener;
    private Map<String, OnProgressRunnable> map;
    private Map<String, Float> mapProPause;

    public UIhelper(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        map = new ConcurrentHashMap<>();
        mapProPause = new ConcurrentHashMap<>();
    }

    public void onError(final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onError(response);
            }
        });
    }

    public void onProgress(ThreadInfo threadInfo, Dbhelper dbhelper) {
        if (map.get(threadInfo.getDownloadInfo().getUrl()) == null)
            map.put(threadInfo.getDownloadInfo().getUrl(), new OnProgressRunnable(threadInfo, dbhelper));
        mHandler.post(map.get(threadInfo.getDownloadInfo().getUrl()));
    }

    public class OnProgressRunnable implements Runnable {
        private Dbhelper dbhelper;
        private ThreadInfo threadInfo;
        private long lastDownLoadTotalLength;
        private long lastRefreshUiTime;
        //暂停的时候保存信息 然后的都不走了
        private boolean pause =false;

        public OnProgressRunnable(ThreadInfo threadInfo, Dbhelper dbhelper) {
            this.threadInfo = threadInfo;
            lastDownLoadTotalLength = 0;
            lastRefreshUiTime = System.currentTimeMillis();
            this.dbhelper = dbhelper;
        }

        @Override
        public void run() {
            synchronized (UIhelper.this) {
                if (!pause) {
                    DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
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
                        downloadInfo.setNetworkSpeed(networkSpeed);
                        lastRefreshUiTime = refreshUiTime;
                        lastDownLoadTotalLength = downLoadTotalLength;
                    }
                    Float lastPauseProgress = mapProPause.get(downloadInfo.getUrl());
                    if (lastPauseProgress==null||(lastPauseProgress!=null&&lastPauseProgress<=downloadInfo.getProgress())) {
                        if (downloadInfo.getState() == DownloadInfo.DOWNLOADING)
                            downloadListener.onProgress(downloadInfo);//发布进度信息
                        if(downloadInfo.getState() == DownloadInfo.PAUSE){
                            downloadListener.onProgress(downloadInfo);//发布进度信息
                            mapProPause.put(downloadInfo.getUrl(),downloadInfo.getProgress());
                            dbhelper.saveTask(downloadInfo);//db保存信息
                            pause =true;
                        }
                    }
                }
            }

        }
    };

    /**
     * pause的时候也会走此方法
     * @param threadInfo
     * @param dbhelper
     */
    public void onFinish(final ThreadInfo threadInfo, final Dbhelper dbhelper) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                    if (!info.isComplete())
                        return;
                }
                //完成以后
                downloadInfo.setState(DownloadInfo.COMPLETE);
                dbhelper.deleteTask(threadInfo.getDownloadInfo());//db移除信息
                downloadListener.onProgress(downloadInfo);//ui通知完成
            }
        });
    }
}
