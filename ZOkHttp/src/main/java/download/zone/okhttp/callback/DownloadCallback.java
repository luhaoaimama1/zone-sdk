package download.zone.okhttp.callback;

import download.zone.okhttp.entity.DownloadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public interface DownloadCallback {
     void onStart();
     void onProgress(int progress,boolean isDone,long networkSpeed);
    //可能调用好几次 todo  有个boolean  删除 开始   结束  为了弹出dialog
     void onStop();
     void onDelete();
     void onError(Response response);
    //todo 最后总走这个
     void onfinished();
}
