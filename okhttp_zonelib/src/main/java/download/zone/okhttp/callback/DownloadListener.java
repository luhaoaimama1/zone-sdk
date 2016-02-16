package download.zone.okhttp.callback;

import download.zone.okhttp.entity.DownloadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public interface DownloadListener {
    public void onStart();
    public void onProgress(int progress,boolean isDone,long networkSpeed);
    //可能调用好几次
    public void onStop();
    public void onDelete();
    public void onError(Response response);
}
