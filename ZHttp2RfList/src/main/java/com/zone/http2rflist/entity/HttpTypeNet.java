package com.zone.http2rflist.entity;

/**
 * Created by Administrator on 2016/3/24.
 */
public enum HttpTypeNet {
    GET,POST,PUT,DELETE,HEAD,PATCH;
    public PostType postType=PostType.NORMAL ;
    public HttpTypeNet postJson(){
        postType=PostType.JSON;
        return this;
    }
    public enum PostType {
        NORMAL,JSON;
    }
}
