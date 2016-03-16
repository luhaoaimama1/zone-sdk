package com.zone.okhttp.entity;
import com.zone.okhttp.OkHttpUtils;
import com.zone.okhttp.wrapper.RequestBuilderProxy;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import zone.Callback;


/**
 * TODO  既然那头已经可以随便设置了 这个还有什么用啊
 * Created by Zone on 2016/2/10.
 */
public class RequestParams {
    private Map<String, String> headerParamsAdd;
    private Map<String, String> headerParamsReplace;
    private Map<String, String> urlParams;
    private Map<String, File> fileParams;
    private Map<String, String> fileNameParams;
    private HttpType mHttpType = HttpType.GET;
    private RequestBuilderProxy mRequestBuilder;

    public  enum HttpType {
        GET, POST
    }

    public RequestParams() {
        initCommon();
    }

    //TODO 可以初始化头部
    private void initCommon() {
        //添加公共参数
        if (urlParams == null)
            urlParams = new ConcurrentHashMap<>();
        urlParams.putAll(OkHttpUtils.getCommonParamsMap());
        //添加公共header
        if (headerParamsAdd == null)
            headerParamsAdd = new ConcurrentHashMap<>();
        headerParamsAdd.putAll(OkHttpUtils.getCommonHeaderMap());
        //最后放头部
        if (headerParamsReplace == null)
            headerParamsReplace = new ConcurrentHashMap<>();
        headerParamsReplace.put("charset", OkHttpUtils.getEncoding());
    }

    public RequestParams put(String key, File file) {
        return  put(key,null,file);
    }

    public RequestParams put(String key, String value, File file) {
        if (fileParams == null)
            fileParams = new ConcurrentHashMap<>();
        if (fileNameParams == null)
            fileNameParams = new ConcurrentHashMap<>();
        fileParams.put(key, file);
        fileNameParams.put(key, value == null ? file.getName() : value);
        return this;
    }

    public RequestParams put(String key, String value) {
        urlParams.put(key, value);
        return this;
    }

    public RequestParams headsReplace(String key, String value) {
        if (value == null || key == null || "".equals(key))
            return this;
        headerParamsReplace.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams headsAdd(String key, String value) {
        if (value == null || key == null || "".equals(key))
            return this;
        headerParamsAdd.put(key, String.valueOf(value));
        return this;
    }

    public Map<String, String> getFileNameParams() {
        return fileNameParams;
    }

    public void setFileNameParams(Map<String, String> fileNameParams) {
        this.fileNameParams = fileNameParams;
    }

    public Map<String, File> getFileParams() {
        return fileParams;
    }

    public void setFileParams(Map<String, File> fileParams) {
        this.fileParams = fileParams;
    }

    public Map<String, String> getHeaderParamsAdd() {
        return headerParamsAdd;
    }

    public void setHeaderParamsAdd(Map<String, String> headerParamsAdd) {
        this.headerParamsAdd = headerParamsAdd;
    }

    public Map<String, String> getHeaderParamsReplace() {
        return headerParamsReplace;
    }

    public void setHeaderParamsReplace(Map<String, String> headerParamsReplace) {
        this.headerParamsReplace = headerParamsReplace;
    }

    public Map<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public HttpType getmHttpType() {
        return mHttpType;
    }

    public void setmHttpType(HttpType mHttpType) {
        this.mHttpType = mHttpType;
    }



    public RequestBuilderProxy getmRequestBuilder() {
        return mRequestBuilder;
    }

    public void setmRequestBuilder(RequestBuilderProxy mRequestBuilder) {
        this.mRequestBuilder = mRequestBuilder;
    }
}
