package com.zone.http2rflist;

/**
 * Created by Administrator on 2016/3/25.
 */
public class RequestUtils {
    public static Request.Builder get(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().get().url(url).params(requestParamsNet);
    }
    public static Request.Builder head(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().head().url(url).params(requestParamsNet);
    }
    public static Request.Builder delete(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().delete().url(url).params(requestParamsNet);
    }
    public static Request.Builder post(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().post().url(url).params(requestParamsNet);
    }
    public static Request.Builder postJson(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().postJson().url(url).params(requestParamsNet);
    }
    public static Request.Builder put(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().put().url(url).params(requestParamsNet);
    }
    public static Request.Builder patch(String url, RequestParamsNet requestParamsNet){
        return new Request.Builder().patch().url(url).params(requestParamsNet);
    }
}
