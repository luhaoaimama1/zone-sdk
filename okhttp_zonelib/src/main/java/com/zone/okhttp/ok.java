package com.zone.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.zone.okhttp.entity.HttpType;
import com.zone.okhttp.utils.MediaTypeUtils;
import com.zone.okhttp.wrapper.ProgressRequestBody;
import com.zone.okhttp.wrapper.RequestBuilderProxy;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import com.zone.okhttp.callback.Callback;

/**
 * TODO ing的: 全局未测试  https未测试
 * <p>TODO 不着急的: 2. cook session的保持 <p>
 * TODO 疑惑的: 1.缓存我打算在那个框架里弄  如果换框架 不至于换缓存学习方法  2. 返回的不用解析  我在那个框架里面弄了
 * TODO 疑惑的: 3.Activity和Fragment生命周期结束后终止请求  在框架里弄不是这里
 * 继续参考那两个OKhttp  GitHub
 * Created by Zone on 2016/2/10.
 */
public class ok {//到时候z.ok()就是网络~学学Xutils 我感觉我应该叫Zutils
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private static HttpConfig httpConfig = new HttpConfig();
    private static OkHttpClient client = httpConfig.build();

    public static RequestBuilderProxy get(String urlString) {
        return get(urlString, null, null);
    }

    public static RequestBuilderProxy get(String urlString, Callback.CommonCallback listener) {
        return get(urlString, null, listener);
    }

    public static RequestBuilderProxy get(String urlString, RequestParams requestParams) {
        return get(urlString, requestParams, null);
    }

    public static RequestBuilderProxy get(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.GET, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy head(String urlString) {
        return head(urlString, null, null);
    }

    public static RequestBuilderProxy head(String urlString, RequestParams requestParams) {
        return head(urlString, requestParams, null);
    }

    public static RequestBuilderProxy head(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.HEAD, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy delete(String urlString) {
        return delete(urlString, null, null);
    }

    public static RequestBuilderProxy delete(String urlString, RequestParams requestParams) {
        return delete(urlString, requestParams, null);
    }

    public static RequestBuilderProxy delete(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.DELETE, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy post(String urlString, RequestParams requestParams) {
        return post(urlString, requestParams, null);
    }

    public static RequestBuilderProxy post(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.POST, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy put(String urlString, RequestParams requestParams) {
        return put(urlString, requestParams, null);
    }

    public static RequestBuilderProxy put(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.PUT, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy patch(String urlString, RequestParams requestParams) {
        return patch(urlString, requestParams, null);
    }

    public static RequestBuilderProxy patch(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
        return Helper.setHttpType(HttpType.PATCH, urlString, requestParams, listener);
    }

    public static RequestBuilderProxy postString(String urlString, String json) {
        return postString(urlString, json, httpConfig.getEncoding());
    }

    public static RequestBuilderProxy postString(String urlString, String json, String encode) {
        RequestBuilderProxy request = new RequestBuilderProxy();
        RequestParams requestParams = new RequestParams();
        requestParams.setmHttpType(HttpType.POST);
        request = Helper.initCommonHeader(request, requestParams);

        MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=" + encode);
        request.url(urlString).post(RequestBody.create(MEDIA_TYPE_PLAIN, json));
        return request;
    }


    public static void cancelTag(Object tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public static void initConfig(HttpConfig config) {
        httpConfig = config;
        client = config.build();
    }

    final static class Helper {
        //--------------------------------------------------内部工具类-------------------------------------------------------
        private static RequestBuilderProxy setHttpType(HttpType httpType, String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
            if (requestParams == null)
                requestParams = new RequestParams();
            requestParams.setmHttpType(httpType);
            return requestCon(urlString, requestParams, listener);
        }

        ;

        //初始化 头部
        private static RequestBuilderProxy initCommonHeader(RequestBuilderProxy request, RequestParams requestParams) {

            if (requestParams.getHeaderAddMap() != null)
                for (Map.Entry<String, String> entry : requestParams.getHeaderAddMap().entrySet())
                    request.addHeader(entry.getKey(), entry.getValue());
            if (requestParams.getHeaderReplaceMap() != null)
                for (Map.Entry<String, String> entry : requestParams.getHeaderReplaceMap().entrySet())
                    request.header(entry.getKey(), entry.getValue());
            return request;
        }

        private static RequestBuilderProxy requestCon(String urlString, RequestParams requestParams, Callback.CommonCallback listener) {
            RequestBuilderProxy request = new RequestBuilderProxy();
            request.setmOkHttpListener(listener);
            initCommonHeader(request, requestParams);
            switch (requestParams.getmHttpType()) {
                case GET:
                    request.url(getUrlCon(urlString, requestParams)).get();
                    break;
                case HEAD:
                    request.url(getUrlCon(urlString, requestParams)).head();
                    break;
                case DELETE:
                    //todo 还有一种带参数的
                    request.url(getUrlCon(urlString, requestParams)).delete();
                    break;
                case POST:
                    request.url(urlString).post(createRequestBody(requestParams, listener));
                    break;
                case PUT:
                    request.url(urlString).put(createRequestBody(requestParams, listener));
                    break;
                case PATCH:
                    request.url(urlString).patch(createRequestBody(requestParams, listener));
                    break;
                default:
                    break;
            }
            return request;

        }

        private static RequestBody createRequestBody(RequestParams requestParams, Callback.CommonCallback listener) {
            RequestBody formBody = null;
            if (requestParams.getFileMap() == null && requestParams.getFileNameMap() == null) {
                //无文件 post
                FormBody.Builder form = new FormBody.Builder();
                for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet())
                    form.add(item.getKey(), item.getValue());
                formBody = form.build();
            } else {
                //有文件 post
                MultipartBody.Builder form = new MultipartBody.Builder();
                form.setType(MultipartBody.FORM);
                for (Map.Entry<String, String> item : requestParams.getParamsMap().entrySet())
                    form.addFormDataPart(item.getKey(), item.getValue());
                for (Map.Entry<String, File> item : requestParams.getFileMap().entrySet()) {
                    form.addFormDataPart(item.getKey(), requestParams.getFileNameMap().get(item.getKey()),
                            RequestBody.create(MediaType.parse(MediaTypeUtils.getFileType(item.getValue())), item.getValue()));
                }

                //requestParams.getmProgressListener()  这个方法已经经过处理了 判断是否开了

                if (Callback.ProgressCallback.class.isAssignableFrom(listener.getClass()))
                    formBody = new ProgressRequestBody(form.build(), (Callback.ProgressCallback) listener);
                else
                    formBody = new ProgressRequestBody(form.build(), null);
            }
            return formBody;
        }


        private static String getUrlCon(String urlString, RequestParams requestParams) {
            if (requestParams.getParamsMap() != null) {
                String get = "";
                for (Map.Entry<String, String> entry : requestParams.getParamsMap().entrySet()) {
                    get += entry.getKey() + "=" + entry.getValue() + "&";
                }
                urlString += "?" + get;
                return urlString.substring(0, urlString.length() - 1);
            }
            return urlString;
        }
    }

}
