package download.zone.okhttp.callback;

import download.zone.okhttp.entity.DownloadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public interface DownloadListener {
    public void onStart();
    public void onFinish(DownloadInfo downloadInfo);
    public void onProgress(DownloadInfo downloadInfo);
    public void onError(Response response);

}
