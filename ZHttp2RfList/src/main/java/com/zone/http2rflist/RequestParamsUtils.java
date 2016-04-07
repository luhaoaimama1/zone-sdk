package com.zone.http2rflist;

/**
 * Created by Administrator on 2016/3/25.
 */
public class RequestParamsUtils {
    public static BaseRequestParams.Builder get(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().get().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder get(String url){
        return new BaseRequestParams.Builder().get().url(url);
    }
    public static BaseRequestParams.Builder head(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().head().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder head(String url){
        return new BaseRequestParams.Builder().head().url(url);
    }
    public static BaseRequestParams.Builder delete(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().delete().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder delete(String url){
        return new BaseRequestParams.Builder().delete().url(url);
    }
    public static BaseRequestParams.Builder post(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().post().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder postJson(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().postJson().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder put(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().put().url(url).params(requestParamsNet);
    }
    public static BaseRequestParams.Builder patch(String url, NetworkParams requestParamsNet){
        return new BaseRequestParams.Builder().patch().url(url).params(requestParamsNet);
    }
}
