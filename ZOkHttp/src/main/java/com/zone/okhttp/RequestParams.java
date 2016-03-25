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
    protected Map<String, String> headerAddMap;
    protected Map<String, String> headerReplaceMap;
    protected Map<String, String> paramsMap;
    protected Map<String, File> fileMap;
    protected Map<String, String> fileNameMap;
    protected HttpType mHttpType = HttpType.GET;
    protected String jsonStr;
    protected String encoding;

    public RequestParams() {
        initCommon();
    }

    private void initCommon() {
        //添加公共参数
        setParamsMap(ok.getHttpConfig().getCommonParamsMap());
        //添加公共header
        setHeaderAddMap(ok.getHttpConfig().getCommonHeaderAddMap());
        //最后放头部
        setHeaderReplaceMap(ok.getHttpConfig().getCommonHeaderReplaceMap());
        if(StringUtils.isEmptyTrim(encoding))
            headsReplace("charset", ok.getHttpConfig().getEncoding());
        else
            headsReplace("charset", encoding);
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    private void file2NameMapChecked(){
        if (fileMap == null)
            fileMap = new ConcurrentHashMap<>();
        if (fileNameMap == null)
            fileNameMap = new ConcurrentHashMap<>();
    }

    public RequestParams put(String key, File file) {
        return  put(key,null,file);
    }

    public RequestParams put(String key, String value, File file) {
        file2NameMapChecked();
        fileMap.put(key, file);
        fileNameMap.put(key, value == null ? file.getName() : value);
        return this;
    }
    public RequestParams setFileMap(Map<String, File> fileMap) {
        file2NameMapChecked();
        this.fileMap.putAll(fileMap);
        for (Map.Entry<String, File> stringFileEntry : fileMap.entrySet())
            fileNameMap.put(stringFileEntry.getKey(),stringFileEntry.getValue().getName());
        return this;
    }
    public Map<String, File> getFileMap() {
        return fileMap;
    }
    public Map<String, String> getFileNameMap() {
        return fileNameMap;
    }

    private void paramsMapChecked(){
        if (paramsMap == null)
            paramsMap = new ConcurrentHashMap<>();
    }
    public RequestParams put(String key, String value) {
        paramsMapChecked();
        paramsMap.put(key, value);
        return this;
    }
    public RequestParams setParamsMap(Map<String, String> paramsMap) {
        paramsMapChecked();
        this.paramsMap.putAll(paramsMap);
        return this;
    }
    public Map<String, String> getParamsMap() {
        return paramsMap;
    }



    private void headerReplaceMapChecked(){
        if (headerReplaceMap == null)
            headerReplaceMap = new ConcurrentHashMap<>();
    }
    public RequestParams headsReplace(String key, String value) {
        headerReplaceMapChecked();
        headerReplaceMap.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams setHeaderReplaceMap(Map<String, String> headerReplaceMap) {
        headerReplaceMapChecked();
        this.headerReplaceMap.putAll(headerReplaceMap);
        return this;
    }
    public Map<String, String> getHeaderReplaceMap() {
        return headerReplaceMap;
    }
    private void  headerAddMapChecked(){
        if (headerAddMap == null)
            headerAddMap = new ConcurrentHashMap<>();
    }
    public RequestParams headsAdd(String key, String value) {
        headerAddMapChecked();
        headerAddMap.put(key, String.valueOf(value));
        return this;
    }

    public RequestParams setHeaderAddMap(Map<String, String> headerAddMap) {
        headerAddMapChecked();
        this.headerAddMap.putAll(headerAddMap);
        return this;
    }
    public Map<String, String> getHeaderAddMap() {
        return headerAddMap;
    }


    public HttpType getmHttpType() {
        return mHttpType;
    }

    public RequestParams setmHttpType(HttpType mHttpType) {
        this.mHttpType = mHttpType;
        return this;
    }

    public  String getEncoding() {
        return encoding;
    }

    public RequestParams setEncoding(String encoding) {
        if (!StringUtils.isEmptyTrim(encoding)) {
            Charset charset = Charset.forName(encoding);
            if (charset!=null) {
                this.encoding = encoding;
            }
            return this;
        }
        return this;
    }


}
