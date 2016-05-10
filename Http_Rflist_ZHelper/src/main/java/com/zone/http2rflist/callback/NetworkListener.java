package com.zone.http2rflist.callback;

import com.zone.http2rflist.entity.SuccessType;

public interface NetworkListener {
    void onStart();

    //成功和失败 最后都走这个
    void onCancelled();

    void onLoading(long total, long current,long networkSpeed,boolean isUploading);

    void onSuccess(String msg,SuccessType type);

    void onFailure(String msg);


}
