package com.zone.http2rflist.callback;

public interface NetworkListener {
    void onStart();

    void onCancelled();

    void onLoading(long total, long current,long networkSpeed,boolean isUploading);
    //TODO  加个tag  分  内存缓存  磁盘缓存  http
    void onSuccess(String msg);

    void onFailure(String msg);


}
