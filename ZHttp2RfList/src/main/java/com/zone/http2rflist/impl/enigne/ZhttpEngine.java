package com.zone.http2rflist.impl.enigne;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.zone.http2rflist.base.BaseNetworkEngine;
import com.zone.http2rflist.RequestParams;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.HttpTypeNet;
import com.zone.http2rflist.entity.SuccessType;
import com.zone.http2rflist.impl.enigne.helper.ParamsHelper;
import com.zone.http2rflist.impl.pop.NetPop;
import com.zone.http2rflist.utils.Pop_Zone;
import com.zone.okhttp.callback.SimpleProgressCallback;
import com.zone.okhttp.ok;
import com.zone.okhttp.wrapper.RequestBuilderProxy;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ZhttpEngine extends BaseNetworkEngine {

    private  NetworkListener listener;
    private  int handlerTag;
    private Object tag;
    private  Call call;

    public ZhttpEngine(Context context, Handler handler) {
        super(context, handler);
    }

    public ZhttpEngine(Context context, Handler handler, boolean showDialog) {
        super(context, handler, showDialog);
    }

    @Override
    protected void ab_Send(RequestParams request) {
        this.listener=request.listener;
        this.handlerTag =request.handlerTag;
        RequestBuilderProxy requestBuilderProxy = null;
        switch (request.params.getHttpTypeNet()){
            case GET:
                requestBuilderProxy= ok.get(request.urlString, ParamsHelper.setParamsNet(request.params),callBack);
                    break;
            case HEAD:
                requestBuilderProxy= ok.head(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                break;
            case DELETE:
                requestBuilderProxy= ok.delete(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                break;
            case POST:
                if(request.params.getHttpTypeNet().postType== HttpTypeNet.PostType.JSON)
                    requestBuilderProxy= ok.postJson(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                else
                    requestBuilderProxy= ok.post(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                break;
            case PUT:
                requestBuilderProxy=  ok.put(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                break;
            case PATCH:
                requestBuilderProxy= ok.patch(request.urlString, ParamsHelper.setParamsNet(request.params), callBack);
                break;
            default:
                break;
        }
        if (requestBuilderProxy!=null)
            call=requestBuilderProxy.tag(tag=(request.cancelTag==null?context:request.cancelTag)).executeSync();
    }
    private Object getTag(){
        return tag;
    }

    @Override
    public void cancelByContext() {
        ok.cancelTag(context);
    }

    @Override
    public void cancel() {
        call.cancel();
    }


    @Override
    protected Dialog createDefaultDialog(Context context) {
        return null;
    }

    @Override
    protected Pop_Zone createDefaultPopWindow(Context context) {
        if(context instanceof Activity)
            return new NetPop((Activity) context);
        else
            return null;
    }

    SimpleProgressCallback callBack=new SimpleProgressCallback() {
        @Override
        public void onStart() {
            if (listener != null)
                listener.onStart();
        }

        @Override
        public void onSuccess(final String result, Call call, Response response) {
//            System.out.println("onSuccess");
//            System.err.println("pagenumber:"+pageNumber);
            //延迟 测试下  上啦 加载又没~
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    sendhandlerMsg(result, handlerTag);
//                }
//            },2000);
            sendhandlerMsg(result, handlerTag);
            if (listener != null) {
                listener.onSuccess(result, SuccessType.HTTP);

            }
        }

        @Override
        public void onError(Call call, IOException e) {
//            System.out.println("onError");
//            System.err.println("message:"+e.getMessage());
            sendhandlerMsg(e.getMessage(), handlerTag);
            if (listener != null) {
                listener.onFailure(e.getMessage());
            }
        }

        @Override
        public void onFinished() {
//            System.out.println("onFinished");
            //因为 listener  网络请求类自带了  咱们在叼一次就出现重复
            if (listener != null)
                listener.onCancelled();
        }

        @Override
        public void onLoading(long total, long current, long networkSpeed, boolean isDownloading) {
            if (listener != null)
                listener.onLoading(total,current,networkSpeed,isDownloading);
        }
    };
}
