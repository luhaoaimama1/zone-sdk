package and.http.okhttp.callback;

import okhttp3.Callback;

public interface OkHttpListener extends Callback,ProgressListener {
    public abstract void onStart();
}
