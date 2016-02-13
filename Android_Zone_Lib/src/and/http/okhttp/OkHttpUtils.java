package and.http.okhttp;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import and.http.okhttp.entity.RequestParams;
import and.http.okhttp.https.ClientHttpsWrapper;
import and.http.okhttp.utils.MediaTypeUtils;
import and.http.okhttp.utils.StringUtils;
import and.http.okhttp.wrapper.ProgressRequestBody;
import and.http.okhttp.wrapper.RequestBuilderProxy;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;

import static and.http.okhttp.entity.RequestParams.HttpType.*;

/**
 * TODO json cook 下载   错误400-599问题应该如何返回？  正确返回是否应该返回response?
 * TODO  返回值是否应该为主线程呢？
 * 继续参考那两个OKhttp  GitHub
 * Created by Zone on 2016/2/10.
 */
public class OkHttpUtils {
    private static OkHttpClient client = new OkHttpClient();
    private static String encoding = "utf-8";
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Map<String, String> commonParamsMap = new HashMap<String, String>();
    private static Map<String, String> commonHeaderMap = new HashMap<String, String>();


    public static RequestBuilderProxy get(String urlString, RequestParams requestParams) {
        requestParams.setmHttpType(GET);
        return requestCon(urlString, requestParams);
    }

    public static RequestBuilderProxy get(String urlString) {
        RequestParams requestParams = new RequestParams();
        requestParams.setmHttpType(GET);
        return requestCon(urlString, requestParams);
    }

    public static RequestBuilderProxy post(String urlString, RequestParams requestParams) {
        return post(urlString, requestParams, false);
    }

    public static RequestBuilderProxy post(String urlString, RequestParams requestParams, boolean openProgress) {
        requestParams.setmHttpType(POST);
        requestParams.setOpenProgress(openProgress);
        return requestCon(urlString, requestParams);
    }

    //TODO  RequestParams 这个东西应该拿到 RequestBuilderProxy这里 这样就可以直接用而不用new了
    public static RequestBuilderProxy postString(String urlString,String json) {
        return postString(urlString,json,encoding);
    }
    public static RequestBuilderProxy postString(String urlString,String json, String encode) {
        Charset charset = Charset.forName(encode);
        if (charset!=null) {
            RequestBuilderProxy request = new RequestBuilderProxy();
            RequestParams requestParams=new RequestParams();
            requestParams.setmHttpType(POST);
            request=initHeader(request, requestParams);

            MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset="+encode);
            request.url(urlString).post(RequestBody.create(MEDIA_TYPE_PLAIN,json));
            return request;
        }else
            return null;
    }
    //初始化 头部
    private static RequestBuilderProxy initHeader(RequestBuilderProxy request, RequestParams requestParams) {

        if (requestParams.getHeaderParamsAdd() != null)
            for (Map.Entry<String, String> entry : requestParams.getHeaderParamsAdd().entrySet())
                request.addHeader(entry.getKey(), entry.getValue());
        if (requestParams.getHeaderParamsReplace() != null)
            for (Map.Entry<String, String> entry : requestParams.getHeaderParamsReplace().entrySet())
                request.header(entry.getKey(), entry.getValue());
        return request;
    }

    private static RequestBuilderProxy requestCon(String urlString, RequestParams requestParams) {
        RequestBuilderProxy request = new RequestBuilderProxy();
        request.setRequestParams(requestParams);
        initHeader(request, requestParams);

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
        if (requestParams.getUrlParams() != null) {
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
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag()))
                call.cancel();
        }
        System.out.println("走了");
    }


//--------------------------------------------https 开始------------------------------------------------------
    /**
     * 这里是支持https的
     */
    private static List<InputStream> mCertificateList;

    public static OkHttpClient.Builder getBuilderWithCertificates(InputStream... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (InputStream inputStream : certificates) {
                if (inputStream != null)
                    mCertificateList.add(inputStream);
            }
        }
        return initCertificates();
    }

    public static OkHttpClient.Builder getBuilderWithCertificates(String... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (String certificate : certificates) {
                if (!StringUtils.isEmpty(certificate))
                    mCertificateList.add(new Buffer().writeUtf8(certificate).inputStream());
            }
        }
        return initCertificates();
    }

    private static void checkCertificateList_Init() {
        if (mCertificateList == null)
            mCertificateList = new ArrayList<InputStream>();
    }

    private static OkHttpClient.Builder initCertificates() {
        if (mCertificateList != null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
            new ClientHttpsWrapper(clientBuilder).setCertificates(mCertificateList);
            return clientBuilder;
        } else
            return null;
    }
//--------------------------------------------https结束------------------------------------------------------


    public static OkHttpClient getClient() {
        return client;
    }

    public static void setClient(OkHttpClient client) {
        OkHttpUtils.client = client;
    }

    public static Map<String, String> getCommonParamsMap() {
        return commonParamsMap;
    }

    public static void setCommonParamsMap(Map<String, String> commonParamsMap) {
        OkHttpUtils.commonParamsMap = commonParamsMap;
    }

    public static Map<String, String> getCommonHeaderMap() {
        return commonHeaderMap;
    }

    public static void setCommonHeaderMap(Map<String, String> commonHeaderMap) {
        OkHttpUtils.commonHeaderMap = commonHeaderMap;
    }

    public static List<InputStream> getmCertificateList() {
        return mCertificateList;
    }

    public static void setmCertificateList(List<InputStream> mCertificateList) {
        OkHttpUtils.mCertificateList = mCertificateList;
    }

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        Charset charset = Charset.forName(encoding);
        if (charset!=null) {
            OkHttpUtils.encoding = encoding;
        }
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static void setmHandler(Handler mHandler) {
        OkHttpUtils.mHandler = mHandler;
    }
}
