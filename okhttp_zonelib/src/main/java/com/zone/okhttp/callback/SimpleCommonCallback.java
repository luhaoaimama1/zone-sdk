package com.zone.okhttp.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/16.
 */
public class SimpleCommonCallback implements Callback.CommonCallback {

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(String result, Call call, Response response) {

    }

    @Override
    public void onError(Call call, IOException e) {

    }

    @Override
    public void onFinished() {

    }
}
