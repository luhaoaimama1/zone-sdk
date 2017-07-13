package com.example.mylib_test.activity.http.retrofit;

import com.example.mylib_test.activity.http.entity.Data;
import com.example.mylib_test.app.Constant;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zone.lib.utils.data.file2io2data.IOUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyRetrofitTest {
    public static ZoneApiInterface getClient(){
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(Constant.ADDRESS_RetrofitClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(ok.getClient())
                .build();
        return retrofit2.create(ZoneApiInterface.class);
    }

    public interface ZoneApiInterface {
        //注意 最后不要加   /
        @GET("{user}")
        Call<Data> getZone(@Path("user") String user, @QueryMap Map<String,String> map);

        @GET("{user}")
        Call<Data> getZone(@Path("user") String user, @Query("name") String str);

        @GET
        Call<Data> getZoneUrl(@Url String str);

        @POST("{user}")
        Call<Data> postZone(@Path("user") String user, @Body Map<String,String> map);


        //@Field   这个应该和@body一样什么也能上传文件吧没尝试
        @FormUrlEncoded
        @POST("log")
        Call<Data> formUrlEncoded(@Field("name") String name, @Field("password") String password);

        @FormUrlEncoded
        @POST("log")
        Call<Data> formUrlEncodedFile(@FieldMap Map<String, String> map);


        @Multipart
        @POST("log")
        Call<ResponseBody> method(@PartMap List<Object> parts) ;

        @GET
        @Streaming
        public Call<ResponseBody> down(@Url String url);//downLoad


        @POST("log")
        Call<Data> postZoneFile( @Body MultipartBody mb);//upload最为标准的

        @Multipart
        @POST("log")
        Call<Data> sendFile(@Part(value = "myFile",encoding = "utf-8") RequestBody file);//upload

        @Multipart
        @POST ("log")
        Call<Data> sendFiles (@PartMap Map<String, RequestBody> params);
    }

    public static void main(String... args) throws IOException {

        HashMap<String, String> map = new HashMap<>();
        //----------------get------------------------
        map.put("type", "get");
        get(map);
        //----------------post------------------------
        map.put("type", "post");
        post(map);
//----------------formUrlEncoded------------------------
        map.put("type", "formUrlEncoded");
        formUrlEncoded(map);
//----------------postFile------------------------
        File file = new File("D:\\psb.jpg");
        File file2 = new File("D:\\mei.jpg");
        postFile(file, file2);
//----------------download------------------------
        download();   //好使

    }

    private static void formUrlEncoded(Map<String, String> map) {
        getClient().formUrlEncoded("ZoneForm", "123456").enqueue(callback);

        getClient().formUrlEncodedFile(map).enqueue(callback);
    }

    private static void postFile(File file1, File file) {
        fileExist(file1);
        fileExist(file);

        //多个文件上传(已此为标准)  文件的时候item.isFormField()=false
        MultipartBody.Builder form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        form.addFormDataPart("keyName","Zone");
        form.addFormDataPart("file","gaga.jpg", RequestBody.create(MediaType.parse("image/*"), file1));
        form.addFormDataPart("file2","meinv.jpg", RequestBody.create(MediaType.parse("image/*"), file));
        getClient().postZoneFile(form.build()).enqueue(callback);

        //另一种方式 不太靠谱的 和上边仅仅是服务器的标志 不一样 item.isFormField()=true
        HashMap<String, RequestBody> mapFile = new HashMap<>();
        mapFile.put("keyName", RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),"Zone"));
        mapFile.put("file1",RequestBody.create(MediaType.parse("image/*"), file));
        getClient().sendFiles(mapFile).enqueue(callback);
//        _-------------单文件上传 和第二一样不标准-------------------
        getClient().sendFile(RequestBody.create(MediaType.parse("image/*"), file)).enqueue(callback);
    }

    private static void fileExist(File file) {
        if (file.exists()) {
            System.out.println("you ");
        } else {
            System.out.println("没有");
        }
    }

    private static void post(HashMap<String, String> map) {
        getClient().postZone("log", map).enqueue(callback);
    }

    private static void get(Map map) {
        retrofit2.Call<Data> call2 =  getClient().getZone("log", map);//返回的时候call 可以灵活运用
        call2.enqueue(callback);

        getClient().getZone("log","Zone").enqueue(callback);

        getClient().getZoneUrl(Constant.ADDRESS+"?a=1").enqueue(callback);

    }

    private static void download() {
        getClient().down("http://down.360safe.com/360/inst.exe").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                File file = new File("D:\\temp","inst.exe");
                IOUtils.write(file,response.body().byteStream());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    static Callback callback =new Callback<Data>() {
        @Override
        public void onResponse(Call<Data> call, Response<Data> response) {
            System.out.println("url:"+  call.request().url()+"\t --->"+new Gson().toJson(response.body())+"\n\n");
        }

        @Override
        public void onFailure(Call<Data> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
