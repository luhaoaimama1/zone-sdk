package com.zone.http2rflist.impl.enigne.helper;

import com.zone.http2rflist.NetworkParams;
import com.zone.okhttp.RequestParams;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ParamsHelper {
    //注意请求模式不要过去！！！！！！
    public static RequestParams setParamsNet(NetworkParams params){
        RequestParams rParams = new RequestParams();
        if (params.getHeaderReplaceMap()!=null)
            rParams.setHeaderReplaceMap(params.getHeaderReplaceMap());
        if (params.getHeaderAddMap()!=null)
            rParams.setHeaderAddMap(params.getHeaderAddMap());
        if (params.getParamsMap()!=null)
            rParams.setParamsMap(params.getParamsMap());
        if (params.getJsonStr()!=null)
            rParams.setJsonStr(params.getJsonStr());
        if (params.getFileMap()!=null)
            for (Map.Entry<String, File> stringFileEntry : params.getFileMap().entrySet())
                rParams.put(stringFileEntry.getKey(),
                        params.getFileNameMap().get(stringFileEntry.getKey()),
                        stringFileEntry.getValue());
        rParams.setEncoding(params.getEncoding());
        return rParams;
    }
}
