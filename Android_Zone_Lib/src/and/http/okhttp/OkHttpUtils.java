package and.http.okhttp;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import and.http.okhttp.entity.RequestParams;
import and.http.okhttp.utils.MediaTypeUtils;
import and.http.okhttp.wrapper.ProgressRequestBody;
import and.http.okhttp.wrapper.RequestBuilder;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import static and.http.okhttp.entity.RequestParams.HttpType.*;

/**
 * TODO json  全局配置 cook  https  下载   错误400-599问题应该如何返回？  正确返回是否应该返回response?
 * TODO  返回值是否应该为主线程呢？
 * 继续参考那两个OKhttp  GitHub
 * Created by Zone on 2016/2/10.
 */
public class OkHttpUtils {
    public static OkHttpClient client = new OkHttpClient();
    public static String encoding = "utf-8";
    public static Handler mHandler=new Handler(Looper.getMainLooper());

    private Map<Object, Call> callMap = new ConcurrentHashMap<Object, Call>();

//    public OkHttpUtils() {
//        mHandler = new Handler(Looper.getMainLooper());
//    }
//private static OkHttpUtils mInstance;
    //    public static OkHttpUtils getInstance()
//    {
//        if (mInstance == null)
//        {
//            synchronized (OkHttpUtils.class)
//            {
//                if (mInstance == null)
//                {
//                    mInstance = new OkHttpUtils();
//                }
//            }
//        }
//        return mInstance;
//    }
    public static RequestBuilder get(String urlString, RequestParams requestParams) {
        requestParams.setmHttpType(GET);
        return requestCon(urlString, requestParams);
    }

    public static RequestBuilder get(String urlString) {
        RequestParams requestParams = new RequestParams();
        requestParams.setmHttpType(GET);
        return requestCon(urlString, requestParams);
    }

    public static RequestBuilder post(String urlString, RequestParams requestParams) {
        return post(urlString, requestParams, false);
    }

    public static RequestBuilder post(String urlString, RequestParams requestParams, boolean openProgress) {
        requestParams.setmHttpType(POST);
        requestParams.setOpenProgress(openProgress);
        return requestCon(urlString, requestParams);
    }

    private static RequestBuilder requestCon(String urlString, RequestParams requestParams) {
        RequestBuilder request = new RequestBuilder();
        request.setRequestParams(requestParams);
        switch (requestParams.getmHttpType()) {
            case GET:
                request.url(getUrlCon(urlString, requestParams));
                break;
            case POST:
                RequestBody requestBody = createRequestBody(requestParams);
                request.url(urlString).post(requestBody);
                break;
            default:
                break;
        }
        //初始化 头部
        if (requestParams.getHeaderParamsAdd() != null)
            for (Map.Entry<String, String> entry : requestParams.getHeaderParamsAdd().entrySet())
                request.addHeader(entry.getKey(), entry.getValue());
        if (requestParams.getHeaderParamsReplace() != null)
            for (Map.Entry<String, String> entry : requestParams.getHeaderParamsReplace().entrySet())
                request.header(entry.getKey(), entry.getValue());
        return request;

    }

    private static RequestBody createRequestBody(RequestParams requestParams) {
        RequestBody formBody = null;
        if (requestParams.getFileParams() == null && requestParams.getFileNameParams() == null) {
            //无文件 post
            FormBody.Builder form = new FormBody.Builder();
            for (Map.Entry<String, String> item : requestParams.getUrlParams().entrySet())
                form.add(item.getKey(), item.getValue());
            formBody = form.build();
        } else {
            //有文件 post
            MultipartBody.Builder form = new MultipartBody.Builder();
            form.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> item : requestParams.getUrlParams().entrySet())
                form.addFormDataPart(item.getKey(), item.getValue());
            for (Map.Entry<String, File> item : requestParams.getFileParams().entrySet()) {
                form.addFormDataPart(item.getKey(), requestParams.getFileNameParams().get(item.getKey()),
                        RequestBody.create(MediaType.parse(MediaTypeUtils.getFileType(item.getValue())), item.getValue()));
            }

            //requestParams.getmProgressListener()  这个方法已经经过处理了 判断是否开了
            formBody = new ProgressRequestBody(form.build(), requestParams.getmProgressListener());

        }
        return formBody;
    }


    private static String getUrlCon(String urlString, RequestParams requestParams) {
        if (requestParams.getUrlParams()!=null) {
            String get = "";
            for (Map.Entry<String, String> entry : requestParams.getUrlParams().entrySet()) {
                get += entry.getKey() + "=" + entry.getValue() + "&";
            }
            urlString += "?" + get;
            return urlString.substring(0, urlString.length() - 1);
        }
        return urlString;
    }

    public static void cancelTag(Object tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()){
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
    }
}
