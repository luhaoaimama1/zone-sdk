package download.zone.okhttp;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import download.zone.okhttp.callback.DownloadCallback;
import download.zone.okhttp.entity.DownloadInfo;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 * todo 完成后 pause 删除  清除  内存中的信息
 */
public class UIhelper {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    //todo 到时候弄完试试 把这个去掉
    private static Map<String,Float> taskPauseProgressMap=new ConcurrentHashMap<>();//下载中的 downloadInfo
    public   DownloadInfo downloadInfo;
    private DownloadCallback downloadListener;
    private OnProgressRunnable onProgressRunnable;
    private OnPauseRunnable onPauseRunnable;

    public UIhelper(DownloadCallback downloadListener) {
        this.downloadListener=downloadListener;
    }


    public void onError(final Response response) {
        DownLoader.writeLog("下载文件失败了~ code=" + response.code() + "url=" + downloadInfo.getUrl());
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
    public void onFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener!=null)
                    downloadListener.onfinished();
            }
        });
    }

    public  void onProgress() {
        synchronized (UIhelper.class) {
            if (onProgressRunnable==null)
                onProgressRunnable=new OnProgressRunnable();
        }
        mHandler.post(onProgressRunnable);
    }

    public void onPause( Dbhelper dbhelper) {
        synchronized (UIhelper.class) {
            if (onPauseRunnable==null)
                onPauseRunnable=new OnPauseRunnable(dbhelper);
        }
        mHandler.post(onPauseRunnable);
    }



    //不用加同步 因為 handler就是 一个个按顺序执行的
    public class OnProgressRunnable implements Runnable {
        private long lastDownLoadTotalLength;
        private long lastRefreshUiTime;
        //暂停的时候保存信息 然后的都不走了
        public OnProgressRunnable() {
            lastDownLoadTotalLength = 0;
            lastRefreshUiTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
                if (downloadInfo.isThreadWork()&&downloadListener != null) {
                    //这里计算进度 ，下载长度
                    long downLoadTotalLength = 0;
                    for (ThreadInfo info : downloadInfo.getThreadInfo())
                        downLoadTotalLength += info.getDownloadLength();
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
                    if(taskPauseProgressMap.get(downloadInfo.getUrl())==null||downloadInfo.getProgress()>=taskPauseProgressMap.get(downloadInfo.getUrl()))
                        downloadListener.onProgress((int)(downloadInfo.getProgress()*100),downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息

            }

        }
    };
    //不用加同步 因為 handler就是 一个个按顺序执行的
    //暂停 一个任务几个线程就可能走几次 但是 db只会存最后一次
    public class OnPauseRunnable implements Runnable {
        private Dbhelper dbhelper;

        //暂停的时候保存信息 然后的都不走了
        public OnPauseRunnable( Dbhelper dbhelper) {
            this.dbhelper = dbhelper;
        }

        @Override
        public void run() {
            if (!downloadInfo.isThreadWork()) {
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
                if (downloadListener != null)
                    downloadListener.onProgress((int)(downloadInfo.getProgress()*100),downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息
                dbhelper.pauseSaveTaskSync(downloadInfo);//db保存信息
                taskPauseProgressMap.put(downloadInfo.getUrl(), downloadInfo.getProgress());//暂停进度信息保存
                if (downloadListener != null)
                    downloadListener.onStop();
             }
        }
    };

    /**
     * @param dbhelper
     * @param saveOutFile
     */
    public void onSuccess(final Dbhelper dbhelper, final File saveOutFile) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                    if (!info.isComplete())
                        return;
                }
                downloadInfo.setDeleteing(true);
                //完成以后
                downloadInfo.setIsDone(true);
                //从命名
                saveOutFile.renameTo(new File(downloadInfo.getTargetFolder(),downloadInfo.getTargetName()));
                taskPauseProgressMap.remove(downloadInfo.getUrl());//移除key
                dbhelper.completeDeleteTaskSync(downloadInfo);//db移除信息
                if (downloadListener!=null)
                    downloadListener.onProgress(100,downloadInfo.isDone(),downloadInfo.getNetworkSpeed());//发布进度信息
            }
        });
    }

    public static Map<String, Float> getTaskPauseProgressMap() {
        return taskPauseProgressMap;
    }
}
