package com.example.mylib_test.activity.http.retrofit;

import com.example.mylib_test.app.Constant;
import com.zone.okhttp.ok;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/4/18.
 */
public class RetrofitClient {
    public static GitApiInterface getClient() {

//        OkHttpClient okClient = new OkHttpClient();
//        okClient.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Response response = chain.proceed(chain.request());
//                return response;
//            }
//        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ADDRESS_RetrofitClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(ok.getClient())
                .build();
        return  retrofit.create(GitApiInterface.class);
    }
    public interface GitApiInterface {
        @GET("{user}")
        Call<String> getZone(@Path("user") String user, @QueryMap Map<String,String> map);
    }
}
