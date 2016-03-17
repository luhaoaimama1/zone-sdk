package download.zone.okhttp.callback2;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/16.
 */
public interface Callback {
    public interface CommonCallback {
        void onStart();
        void onSuccess(String result, Call call, Response response);
        void onError(Call call, IOException e);
        //onSuccess  onError 最后都会调用此
        void onFinished();
    }

    public interface ProgressCallback extends CommonCallback {
        //todo 进度条怎么办
        void onLoading(long total, long current, long networkSpeed, boolean isDownloading);
//        void onProgress(int progress, boolean isDone, long networkSpeed);
    }
    public interface DownLoadCallback extends ProgressCallback {
        void onStop() ;
        void onDelete();
    }
}
