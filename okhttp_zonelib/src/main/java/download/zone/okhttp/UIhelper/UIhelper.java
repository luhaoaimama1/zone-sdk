package download.zone.okhttp.UIhelper;

import android.os.Handler;
import android.os.Looper;

import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public  class UIhelper {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private  DownloadListener downloadListener;

    public UIhelper(DownloadListener downloadListener) {
        this.downloadListener=downloadListener;
    }

    public void onStart(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onStart();
            }
        });
    }
    public void onError(final Response response){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                downloadListener.onError(response);
            }
        });
    }
    public void onProgress(final ThreadInfo threadInfo){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                //todo 这里计算进度  速度  下载长度

                downloadListener.onProgress(downloadInfo);
            }
        });
    }
    public void onFinish(final ThreadInfo threadInfo){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                DownloadInfo downloadInfo = threadInfo.getDownloadInfo();
                for (ThreadInfo info : downloadInfo.getThreadInfo()) {
                    if(!info.isComplete())
                       return ;
                }
                downloadListener.onFinish(downloadInfo);
                //todo  数据库移除任务
            }
        });
    }
}
