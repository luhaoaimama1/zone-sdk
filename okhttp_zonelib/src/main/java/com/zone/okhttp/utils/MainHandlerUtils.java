package com.zone.okhttp.utils;

import com.zone.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Zone on 2016/3/17.
 */
public class MainHandlerUtils {
    public static void onStart(final zone.Callback.CommonCallback listener) {
        OkHttpUtils.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onStart();
            }
        });
    }

    public static void onFailure(final zone.Callback.CommonCallback listener, final Call call, final IOException e) {
        OkHttpUtils.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onError(call, e);
            }
        });
    }

    public static void onFinished(final zone.Callback.CommonCallback listener) {
        OkHttpUtils.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onFinished();
            }
        });
    }

    public static void onResponse(final zone.Callback.CommonCallback listener, final Call call, final Response response, final String result) {
        OkHttpUtils.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(result, call, response);
            }
        });
    }
    public static void onLoading(final zone.Callback.ProgressCallback listener,final long total, final long current, final long networkSpeed,final boolean isDownloading) {
        OkHttpUtils.getmHandler().post(new Runnable() {
            @Override
            public void run() {
                listener.onLoading(total, current, networkSpeed,isDownloading);
            }
        });
    }
}
