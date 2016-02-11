package and.http.okhttp.callback;

import and.http.okhttp.entity.LoadingParams;

/**
 * Created by Zone on 2016/2/10.
 */
public interface ProgressListener {
    public abstract void onLoading(LoadingParams mLoadingParams);

}
