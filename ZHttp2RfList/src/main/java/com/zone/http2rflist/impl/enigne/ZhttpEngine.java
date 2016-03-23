package com.zone.http2rflist.impl.enigne;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.zone.http2rflist.BaseNetworkQuest;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.okhttp.RequestParams;
import com.zone.okhttp.callback.Callback;
import com.zone.okhttp.ok;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/23.
 *
 */
public class ZhttpEngine extends BaseNetworkQuest {
    public ZhttpEngine(Context context, Handler handler) {
        super(context, handler);
    }

    public ZhttpEngine(Context context, Handler handler, boolean showDialog) {
        super(context, handler, showDialog);
    }

    @Override
    protected void ab_Send(String urlString, Map<String, String> map, final int tag, final NetworkListener listener) {
        ok.get(urlString, new RequestParams().setParamsMap(map),listener==null?null:new Callback.CommonCallback() {
            @Override
            public void onStart() {
                if (listener!=null)
                    listener.onStart();
            }

            @Override
            public void onSuccess(String result, Call call, Response response) {
                if (listener!=null){
                    listener.onSuccess(result);
                    sendhandlerMsg(result,tag);
                }
            }

            @Override
            public void onError(Call call, IOException e) {
                if (listener!=null)
                    listener.onFailure(e.getMessage());
            }

            @Override
            public void onFinished() {
                if (listener!=null)
                    listener.onCancelled();
            }
        }).executeSync();
    }

    @Override
    protected void ab_SendPost(String urlString, Map<String, String> map, int tag, NetworkListener listener) {

    }

    @Override
    protected void ab_SendFile(String urlString, Map<String, String> map, Map<String, File> fileMap, int tag, NetworkListener listener) {

    }

    @Override
    protected Dialog createDefaultDialog(Context context) {
        return null;
    }
}
