package and.http.okhttp.entity;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import and.http.okhttp.OkHttpUtils;
import and.http.okhttp.callback.OkHttpListener;
import and.http.okhttp.callback.ProgressListener;
import and.http.okhttp.wrapper.RequestBuilder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/10.
 */
public class RequestParams {
    private Map<String, String> headerParamsAdd;
    private Map<String, String> headerParamsReplace;
    private Map<String, String> urlParams;
    private Map<String, File> fileParams;
    private Map<String, String> fileNameParams;
    private boolean openProgress = false;
    private HttpType mHttpType = HttpType.GET;
    private ProgressListener mProgressListener;
    private RequestBuilder mRequestBuilder;

    public static enum HttpType {
        GET, POST
    }

    public RequestParams() {
//        init();
    }

    //TODO 可以初始化头部
    private void init() {
        headerParamsReplace.put("charset", "UTF-8");
//        //添加公共参数
//        Map<String, String> commonParams = OkHttpFinal.getOkHttpFinal().getCommonParams();
//        if ( commonParams != null && commonParams.size() > 0 ) {
//            urlParams.putAll(commonParams);
//        }
//
//        //添加公共header
//        Map<String, String> commonHeader = OkHttpFinal.getOkHttpFinal().getCommonHeaderMap();
//        if ( commonHeader != null && commonHeader.size() > 0 ) {
//            headerParamsAdd.putAll(commonHeader);
//        }
//
//        if ( httpCycleContext != null ) {
//            httpTaskKey = httpCycleContext.getHttpTaskKey();
//        }
    }


    public RequestParams put(String key, Object value, File file) {
        if (file == null || key == null || "".equals(key))
            return this;

        if (fileParams == null)
            fileParams = new ConcurrentHashMap<>();
        if (fileNameParams == null)
            fileNameParams = new ConcurrentHashMap<>();
        fileParams.put(key, file);
        fileNameParams.put(key, value == null ? "" : String.valueOf(value));
        return this;
    }

    public RequestParams putHeadsReplace(String key, String value) {
        if (value == null || key == null || "".equals(key))
            return this;
        if (headerParamsReplace == null)
            headerParamsReplace = new ConcurrentHashMap<>();
        urlParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams putHeadsAdd(String key, String value) {
        if (value == null || key == null || "".equals(key))
            return this;
        if (headerParamsAdd == null)
            headerParamsAdd = new ConcurrentHashMap<>();
        urlParams.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams put(String key, Object value) {
        if (value == null || key == null || "".equals(key))
            return this;
        if (urlParams == null)
            urlParams = new ConcurrentHashMap<>();
        urlParams.put(key, String.valueOf(value));
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

    public boolean isOpenProgress() {
        return openProgress;
    }

    public void setOpenProgress(boolean openProgress) {
        this.openProgress = openProgress;
    }

    public ProgressListener getmProgressListener() {
        if(mProgressListener==null&&openProgress)
            mProgressListener=new ProgressListener() {
                @Override
                public void onLoading(LoadingParams mLoadingParams) {
                        if(mRequestBuilder!=null&&mRequestBuilder.getmOkHttpListener()!=null)
                            onLoadingMainCall(mRequestBuilder.getmOkHttpListener(),mLoadingParams);
                }
            };
        return mProgressListener;
    }
    private void onLoadingMainCall(final OkHttpListener listener, final LoadingParams mLoadingParams){
        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                    listener.onLoading(mLoadingParams);
            }
        });
    }

    public void setmProgressListener(ProgressListener mProgressListener) {
        this.mProgressListener = mProgressListener;
    }

    public RequestBuilder getmRequestBuilder() {
        return mRequestBuilder;
    }

    public void setmRequestBuilder(RequestBuilder mRequestBuilder) {
        this.mRequestBuilder = mRequestBuilder;
    }
}
