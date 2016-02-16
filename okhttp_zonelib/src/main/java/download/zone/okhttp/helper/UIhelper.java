package download.zone.okhttp.helper;
import android.os.Handler;
import android.os.Looper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import download.zone.okhttp.DownLoader;
import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public class UIhelper {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private  DownloadListener downloadListener;
    private  DownLoader ourInstance;
    private static Map<String,Float> taskPauseProgressMap=new ConcurrentHashMap<>();;//下载中的 downloadInfo

    public UIhelper( DownLoader ourInstance,DownloadListener downloadListener) {
        this.ourInstance = ourInstance;
        this.downloadListener=downloadListener;
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

    public void onProgress(ThreadInfo threadInfo, Dbhelper dbhelper) {
        mHandler.post(new OnProgressRunnable(threadInfo, dbhelper));
    }



    public class OnProgressRunnable implements Runnable {
        private Dbhelper dbhelper;
        private ThreadInfo threadInfo;
        private long lastDownLoadTotalLength;
        private long lastRefreshUiTime;
        //暂停的时候保存信息 然后的都不走了
        private boolean pause=false;
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
                    Map<String, Integer> statuMap = ourInstance.getTaskStatuMap();
                    if (statuMap.get(downloadInfo.getUrl()) == DownloadInfo.DOWNLOADING){
                        if (downloadListener != null)
                            if(taskPauseProgressMap.get(downloadInfo.getUrl())==null||downloadInfo.getProgress()>=taskPauseProgressMap.get(downloadInfo.getUrl()))
                                downloadListener.onProgress(downloadInfo);//发布进度信息

                    }else{
                        if (downloadListener != null)
                            downloadListener.onProgress(downloadInfo);//发布进度信息
                        dbhelper.saveTask(downloadInfo);//db保存信息
                        taskPauseProgressMap.put(downloadInfo.getUrl(), downloadInfo.getProgress());
                        pause=true;
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
                downloadInfo.setIsDone(true);
                taskPauseProgressMap.remove(threadInfo.getDownloadInfo().getUrl());//移除key
                dbhelper.deleteTask(threadInfo.getDownloadInfo());//db移除信息
                if (downloadListener!=null)
                    downloadListener.onProgress(downloadInfo);//ui通知完成
            }
        });
    }
}
