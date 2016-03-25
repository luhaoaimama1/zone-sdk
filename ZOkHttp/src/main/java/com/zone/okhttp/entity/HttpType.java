package com.zone.okhttp.entity;

/**
 * Created by Zone on 2016/3/17.
 */
public enum HttpType {
    GET,POST,PUT,DELETE,HEAD,PATCH;
    public PostType postType=PostType.NORMAL ;
    public HttpType postJson(){
        postType=PostType.JSON;
        return this;
    }
    public enum PostType {
        NORMAL,JSON;
    }
}
