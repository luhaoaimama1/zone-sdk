package and.http.okhttp.callback;

import java.io.IOException;

import and.http.okhttp.entity.LoadingParams;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/10.
 */
public class OkHttpSimpleListener implements OkHttpListener {
    @Override
    public void onStart() {

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }

    @Override
    public void onLoading(LoadingParams mLoadingParams) {

    }
}
