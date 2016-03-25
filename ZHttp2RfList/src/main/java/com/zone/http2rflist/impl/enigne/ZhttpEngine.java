package com.zone.http2rflist.impl.enigne;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.zone.http2rflist.BaseNetworkQuest;
import com.zone.http2rflist.RequestParamsNet;
import com.zone.http2rflist.callback.NetworkListener;
import com.zone.http2rflist.entity.HttpTypeNet;
import com.zone.http2rflist.entity.SuccessType;
import com.zone.http2rflist.impl.enigne.helper.ParamsHelper;
import com.zone.okhttp.callback.SimpleProgressCallback;
import com.zone.okhttp.ok;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ZhttpEngine extends BaseNetworkQuest {

    private  NetworkListener listener;
    private  int tag;
    public ZhttpEngine(Context context, Handler handler) {
        super(context, handler);
    }

    public ZhttpEngine(Context context, Handler handler, boolean showDialog) {
        super(context, handler, showDialog);
    }

    @Override
    protected void ab_Send(String urlString, RequestParamsNet params, final int tag, final NetworkListener listener) {
        this.listener=listener;
        this.tag=tag;
        switch (params.getHttpTypeNet()){
            case GET:
                ok.get(urlString, ParamsHelper.setParamsNet(params),callBack).tag(context).executeSync();
                break;
            case HEAD:
                ok.head(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                break;
            case DELETE:
                ok.delete(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                break;
            case POST:
                if(params.getHttpTypeNet().postType== HttpTypeNet.PostType.JSON)
                    ok.postJson(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                else
                    ok.post(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                break;
            case PUT:
                ok.put(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                break;
            case PATCH:
                ok.patch(urlString, ParamsHelper.setParamsNet(params), callBack).tag(context).executeSync();
                break;
            default:
                break;
        }

    }

    @Override
    protected void cancelAllRequest() {
        ok.cancelTag(context);
    }


    @Override
    protected Dialog createDefaultDialog(Context context) {
        return null;
    }

    SimpleProgressCallback callBack=new SimpleProgressCallback() {
        @Override
        public void onStart() {
            if (listener != null)
                listener.onStart();
        }

        @Override
        public void onSuccess(String result, Call call, Response response) {
            sendhandlerMsg(result, tag);
            if (listener != null) {
                listener.onSuccess(result, SuccessType.HTTP);
                listener.onCancelled();

            }
        }

        @Override
        public void onError(Call call, IOException e) {
            sendhandlerMsg(e.getMessage(), tag);
            if (listener != null) {
                listener.onFailure(e.getMessage());
                listener.onCancelled();
            }
        }

        @Override
        public void onFinished() {
            //因为 listener  网络请求类自带了  咱们在叼一次就出现重复
//            if (listener != null)
//                listener.onCancelled();
        }

        @Override
        public void onLoading(long total, long current, long networkSpeed, boolean isDownloading) {
            if (listener != null)
                listener.onLoading(total,current,networkSpeed,isDownloading);
        }
    };
}
