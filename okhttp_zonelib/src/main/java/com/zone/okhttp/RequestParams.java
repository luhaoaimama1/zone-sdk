package com.zone.okhttp;
import com.zone.okhttp.entity.HttpType;
import com.zone.okhttp.utils.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * TODO  既然那头已经可以随便设置了 这个还有什么用啊
 * Created by Zone on 2016/2/10.
 */
public class RequestParams {
    private Map<String, String> headerAddMap;
    private Map<String, String> headerReplaceMap;
    private Map<String, String> paramsMap;
    private Map<String, File> fileMap;
    private Map<String, String> fileNameMap;
    private HttpType mHttpType = HttpType.GET;
    private String encoding ;

    public RequestParams() {
        initCommon();
    }

    //TODO 可以初始化头部
    private void initCommon() {
        //添加公共参数
        if (paramsMap == null)
            paramsMap = new ConcurrentHashMap<>();
        paramsMap.putAll(ok.getHttpConfig().getCommonParamsMap());
        //添加公共header
        if (headerAddMap == null)
            headerAddMap = new ConcurrentHashMap<>();
        headerAddMap.putAll(ok.getHttpConfig().getCommonHeaderAddMap());
        //最后放头部
        if (headerReplaceMap == null)
            headerReplaceMap = new ConcurrentHashMap<>();
        headerAddMap.putAll(ok.getHttpConfig().getCommonHeaderReplaceMap());
        if(StringUtils.isEmptyTrim(encoding))
            headerReplaceMap.put("charset", ok.getHttpConfig().getEncoding());
        else
            headerReplaceMap.put("charset", encoding);
    }

    public RequestParams put(String key, File file) {
        return  put(key,null,file);
    }

    public RequestParams put(String key, String value, File file) {
        if (fileMap == null)
            fileMap = new ConcurrentHashMap<>();
        if (fileNameMap == null)
            fileNameMap = new ConcurrentHashMap<>();
        fileMap.put(key, file);
        fileNameMap.put(key, value == null ? file.getName() : value);
        return this;
    }

    public RequestParams put(String key, String value) {
        paramsMap.put(key, value);
        return this;
    }

    public RequestParams headsReplace(String key, String value) {
        headerReplaceMap.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams headsAdd(String key, String value) {
        headerAddMap.put(key, String.valueOf(value));
        return this;
    }


    public Map<String, File> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, File> fileMap) {
        this.fileMap.putAll(fileMap);
        for (Map.Entry<String, File> stringFileEntry : fileMap.entrySet())
            fileNameMap.put(stringFileEntry.getKey(),stringFileEntry.getValue().getName());
    }

    public Map<String, String> getHeaderAddMap() {
        return headerAddMap;
    }

    public void setHeaderAddMap(Map<String, String> headerAddMap) {
        this.headerAddMap.putAll(headerAddMap);
    }

    public Map<String, String> getHeaderReplaceMap() {
        return headerReplaceMap;
    }

    public void setHeaderReplaceMap(Map<String, String> headerReplaceMap) {
        this.headerReplaceMap.putAll(headerReplaceMap);
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap.putAll(paramsMap);
    }

    public HttpType getmHttpType() {
        return mHttpType;
    }

    public void setmHttpType(HttpType mHttpType) {
        this.mHttpType = mHttpType;
    }

    public  String getEncoding() {
        return encoding;
    }

    public  void setEncoding(String encoding) {
        Charset charset = Charset.forName(encoding);
        if (charset!=null) {
            this.encoding = encoding;
        }
    }

    public Map<String, String> getFileNameMap() {
        return fileNameMap;
    }
}
