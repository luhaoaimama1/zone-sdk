package com.zone.http2rflist.callback;

public interface NetworkListener {
    public abstract void onStart();

    public abstract void onCancelled();

    public abstract void onLoading(long total, long current, boolean isUploading);
    //TODO  加个tag  分  内存缓存  磁盘缓存  http
    public abstract void onSuccess(String msg);

    public abstract void onFailure(String msg);

}
