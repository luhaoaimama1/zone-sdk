package com.zone.okhttp.utils;

import com.zone.okhttp.ok;
import com.zone.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Zone on 2016/3/17.
 */
public class MainHandlerUtils {
    public static void onStart(final Callback.CommonCallback listener) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onStart();
            }
        });
    }

    public static void onFailure(final Callback.CommonCallback listener, final Call call, final IOException e) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onError(call, e);
            }
        });
    }

    public static void onFinished(final Callback.CommonCallback listener) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onFinished();
            }
        });
    }

    public static void onResponse(final Callback.CommonCallback listener, final Call call, final Response response, final String result) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(result, call, response);
            }
        });
    }
    public static void onLoading(final Callback.ProgressCallback listener,final long total, final long current, final long networkSpeed,final boolean isDownloading) {
        ok.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onLoading(total, current, networkSpeed,isDownloading);
            }
        });
    }
}
