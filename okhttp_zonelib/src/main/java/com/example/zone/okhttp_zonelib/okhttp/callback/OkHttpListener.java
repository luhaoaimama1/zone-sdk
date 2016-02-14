package com.example.zone.okhttp_zonelib.okhttp.callback;

import okhttp3.Callback;

public interface OkHttpListener extends Callback,ProgressListener {
    public abstract void onStart();
}
