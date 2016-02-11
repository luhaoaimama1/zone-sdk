package and.http.okhttp.wrapper;
import java.io.IOException;
import and.http.okhttp.OkHttpUtils;
import and.http.okhttp.callback.OkHttpListener;
import and.http.okhttp.entity.RequestParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/10.
 */
public class RequestBuilder extends Request.Builder {
    private RequestParams requestParams;
    private OkHttpListener mOkHttpListener;



    //这个不怎么用。。。   不是异步
    public Response execute() {
        Call call = OkHttpUtils.client.newCall(this.build());
        Response temp = null;
        try {
            temp = call.execute();
        } catch (IOException e) {
//            e.printStackTrace();
            System.err.println("cause:" + e.getCause() + "\t message:" + e.getMessage());
        }
        return temp;
    };

    public Call executeAsy(OkHttpListener listener) {
        this.mOkHttpListener=listener;
        Call call = OkHttpUtils.client.newCall(this.build());
        if(mOkHttpListener!=null)
            onStartMainCall(mOkHttpListener);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(mOkHttpListener!=null)
                    onFailureMainCall(mOkHttpListener,call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(mOkHttpListener!=null)
                    onResponseMainCall(mOkHttpListener,call,response);
            }
        });
        return call;
    };


    public RequestParams getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        this.requestParams = requestParams;
        this.requestParams.setmRequestBuilder(this);
    }

    public OkHttpListener getmOkHttpListener() {
        return mOkHttpListener;
    }

    public void setmOkHttpListener(OkHttpListener mOkHttpListener) {
        this.mOkHttpListener = mOkHttpListener;
    }
    private void onStartMainCall(OkHttpListener listener){
        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mOkHttpListener.onStart();
            }
        });
    }
    private void onFailureMainCall(OkHttpListener listener, final Call call, final IOException e){
        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mOkHttpListener.onFailure(call, e);
            }
        });
    }
    private void onResponseMainCall(OkHttpListener listener, final Call call, final  Response response){
        OkHttpUtils.mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mOkHttpListener.onResponse(call, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
