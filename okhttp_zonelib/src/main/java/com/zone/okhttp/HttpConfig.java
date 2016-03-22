package com.zone.okhttp;
import com.zone.okhttp.https.ClientBuilderHttpsWrapper;
import com.zone.okhttp.https.SkirtHttpsHostnameVerifier;
import com.zone.okhttp.utils.StringUtils;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import okhttp3.OkHttpClient;
import okio.Buffer;
/**
 * Created by Administrator on 2016/3/17.
 * 查看配置好的参数的时候需要 {@link ok .getClient()}
 */
public class HttpConfig {
    //默认值的话就是 clientBuilder里面的
    private OkHttpClient.Builder clientBuilder;
    private  String encoding = "utf-8";
    private  Map<String, String> commonParamsMap = new HashMap<String, String>();
    private  Map<String, String> commonHeaderAddMap = new HashMap<String, String>();
    private  Map<String, String> commonHeaderReplaceMap = new HashMap<String, String>();

    public HttpConfig() {
        clientBuilder = new OkHttpClient().newBuilder();
        //超时时间的默认设置
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
    }

    public HttpConfig readTimeout(long timeout, TimeUnit unit) {
        clientBuilder.readTimeout(timeout,unit);
        return this;
    }
    public HttpConfig writeTimeout(long timeout, TimeUnit unit) {
        clientBuilder.writeTimeout(timeout, unit);
        return this;
    }
    public HttpConfig connectTimeout(long timeout, TimeUnit unit) {
        clientBuilder.connectTimeout(timeout, unit);
        return this;
    }

    /**
     * {@link SkirtHttpsHostnameVerifier 当未设置证书的时候,https的链接信任~}
     * @param hostnameVerifier
     * @return
     */
    public HttpConfig hostnameVerifier(HostnameVerifier hostnameVerifier) {
        clientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }



     OkHttpClient build(){
        return clientBuilder.build();
    }


    //--------------------------------------------https 开始------------------------------------------------------
    /**
     * 这里是支持https的
     */
    private  List<InputStream> mCertificateList;

    public HttpConfig Certificates(InputStream... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (InputStream inputStream : certificates) {
                if (inputStream != null)
                    mCertificateList.add(inputStream);
            }
        }
        return initCertificates();
    }

    public HttpConfig Certificates(String... certificates) {
        if (certificates.length != 0) {
            checkCertificateList_Init();
            for (String certificate : certificates) {
                if (!StringUtils.isEmptyTrim(certificate))
                    mCertificateList.add(new Buffer().writeUtf8(certificate).inputStream());
            }
        }
        return initCertificates();
    }

    private  void checkCertificateList_Init() {
        if (mCertificateList == null)
            mCertificateList = new ArrayList<InputStream>();
    }

    private HttpConfig initCertificates() {
        if (mCertificateList != null) {
            new ClientBuilderHttpsWrapper(clientBuilder).setCertificates(mCertificateList);
        }
       return this;
    }
//--------------------------------------------https结束------------------------------------------------------

    public  String getEncoding() {
        return encoding;
    }

    public  HttpConfig setEncoding(String encoding) {
        Charset charset = Charset.forName(encoding);
        if (charset!=null) {
            this.encoding = encoding;
        }
        return this;
    }
    public  Map<String, String> getCommonParamsMap() {
        return commonParamsMap;
    }

    public  HttpConfig setCommonParamsMap(Map<String, String> commonParamsMap) {
       this.commonParamsMap = commonParamsMap;
        return this;
    }

    public  Map<String, String> getCommonHeaderAddMap() {
        return commonHeaderAddMap;
    }

    public  HttpConfig setCommonHeaderAddMap(Map<String, String> commonHeaderAddMap) {
        this.commonHeaderAddMap = commonHeaderAddMap;
        return this;
    }

    public Map<String, String> getCommonHeaderReplaceMap() {
        return commonHeaderReplaceMap;
    }

    public HttpConfig setCommonHeaderReplaceMap(Map<String, String> commonHeaderReplaceMap) {
        this.commonHeaderReplaceMap = commonHeaderReplaceMap;
        return this;
    }
}
