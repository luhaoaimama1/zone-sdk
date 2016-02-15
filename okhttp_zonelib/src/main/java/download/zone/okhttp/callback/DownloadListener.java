package download.zone.okhttp.callback;

import download.zone.okhttp.entity.DownloadInfo;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public interface DownloadListener {
    /**
     *  pause的时候也会走此方法
     * @param downloadInfo
     */
    public void onProgress(DownloadInfo downloadInfo);
    public void onError(Response response);

}
