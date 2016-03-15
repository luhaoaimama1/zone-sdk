package com.example.mylib_test.activity.http;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mylib_test.R;
import com.example.mylib_test.app.Constant;
import com.example.mylib_test.handler.HandlerTest;
import com.zone.okhttp.OkHttpUtils;
import com.zone.okhttp.callback.OkHttpSimpleListener;
import com.zone.okhttp.entity.LoadingParams;
import com.zone.okhttp.entity.RequestParams;

import org.apache.http.HttpResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import and.http.MyConn_Utils;
import and.http.MyConn_Utils.CallBack;
import and.http.MyConn_Utils.FileUpLoad_CallBack;
import and.http.client.MyHttpFilePostThread;
import and.http.client.MyHttpGetThread;
import and.http.client.MyHttpPostThread;
import and.http.downfile.DownLoader;
import and.http.downfile.DownLoader.ProgressListener;
import and.sd.FileUtils_SD;
import butterknife.Bind;
import butterknife.ButterKnife;
import download.zone.okhttp.callback.DownloadListener;
import download.zone.okhttp.entity.DownloadInfo;
import okhttp3.Call;
import okhttp3.Response;

public class Http_MainActivity extends Activity implements OnClickListener {
    final String UrlPath = Constant.ADDRESS;
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, String> params = new HashMap<String, String>();
    @Bind(R.id.tv_okHttp)
    TextView tvOkHttp;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_test);
        ButterKnife.bind(this);
        //这段　只是学习下handler～　
        new Thread() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            public void run() {
                Looper.prepare();
                //Handler.Callback 还必须这么写　
                Handler abc = new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {
                        return false;
                    }
                });
                Looper.loop();
                Looper.myLooper().quitSafely();
            }

            ;
        }.start();

    }

    @Override
    public void onClick(View v) {
        map.clear();
        params.clear();
        client(v);
        conn(v);
        tread(v);
        okHttp(v);
        switch (v.getId()) {
            case R.id.handle:
                Button hand = (Button) findViewById(R.id.handle);
                HandlerTest ht = new HandlerTest();
                ht.updateView(hand);
                break;
            case R.id.noPullTest:
                startActivity(new Intent(this, NetworkNoPull_TestActivity.class));
                break;
            case R.id.noPull_GloboTest:
                startActivity(new Intent(this, NetworkNoPull_Globlo_TestActivity.class));
                break;
            case R.id.pullGet:
                startActivity(new Intent(this, NetworkPull_TestActivity.class));
                break;

            default:
                break;
        }
    }


    OkHttpSimpleListener okListener = new OkHttpSimpleListener() {
        @Override
        public void onFailure(Call call, IOException e) {
            super.onFailure(call, e);
            System.err.println("IOException"+e.getMessage());
        }

        @Override
        public void onLoading(LoadingParams mLoadingParams) {
            super.onLoading(mLoadingParams);
            System.out.println(mLoadingParams.toString());
//            tvOkHttp.setText("progress:"+mLoadingParams.progress);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            super.onResponse(call, response);
            String htmlStr = response.body().string();
            System.out.println(htmlStr);
//            tvOkHttp.setText("onResponse");
        }

        @Override
        public void onStart() {
            super.onStart();
            System.out.println("OkHttpSimpleListener  onStart>>");
        }
    };

    private void okHttp(View v) {
        String urlPath = "http://down.360safe.com/360/inst.exe";
        switch (v.getId()) {
            case R.id.bt_okGet:
                //创建okHttpClient对象
//				OkHttpUtils.get(UrlPath + "?un=8&kb=ga").executeAsy(okListener);
                OkHttpUtils.get("http://www.baidu.com").tag(this).executeAsy(okListener);
                break;
            case R.id.bt_Https:
                OkHttpUtils.get("https://kyfw.12306.cn/otn/").tag(this).executeAsy(okListener);
                break;
            case R.id.bt_okPost:
                OkHttpUtils.post(UrlPath, new RequestParams().put("platform", "android")
                        .put("name", "bug").put("subject", 123)).tag(this).executeAsy(okListener);
                break;
            case R.id.bt_downLoader:
                download.zone.okhttp.DownLoader.getInstance(this).startTask(urlPath, FileUtils_SD.getFile(""), new DownloadListener() {

                    @Override
                    public void onStart() {
                        System.out.println("------------------------onStart!!!------------------------");
                    }

                    @Override
                    public void onProgress(int progress, boolean isDone, long networkSpeed) {
                        System.out.println("页面进度:" + progress + " \t 网速：" + networkSpeed + "k/s");
                        progressBar.setProgress(progress);
                        if (isDone) {
                            System.out.println("------------------------COMPLETE------------------------");
                        }
                    }

                    @Override
                    public void onStop() {
                        System.out.println("------------------------onStop!!!------------------------");
                    }

                    @Override
                    public void onDelete() {
                        System.out.println("------------------------onDelete!!!------------------------");
                    }

                    @Override
                    public void onError(Response response) {
                        System.out.println("onError");
                    }
                });
                break;
            case R.id.bt_downLoaderpause:
                download.zone.okhttp.DownLoader.getInstance(this).stopTask(urlPath);//;urlPath, FileUtils_SD.getFile(""),
                break;
            case R.id.bt_downLoaderDelete:
                download.zone.okhttp.DownLoader.getInstance(this).deleteTask(urlPath);//;urlPath, FileUtils_SD.getFile(""),
                break;
            case R.id.bt_okUpload:
                File f = new File(FileUtils_SD.getFile(""), "高达 - 00.mp3");
                File f2 = new File(FileUtils_SD.getFile("DCIM", "Camera"), "20150621_121327.jpg");
                map.put("String_uid", "love");
                OkHttpUtils.post(UrlPath, new RequestParams().put("String_uid", "love")
                        .put("mFile", f).put("subject", "1327.jpg", f2), true).tag(this).executeAsy(okListener);
                break;
            default:
                break;
        }
    }

    private void tread(View v) {
        switch (v.getId()) {
            case R.id.asyncTask:
                startActivity(new Intent(this, AsyncTask_TestActivity.class));
                break;

            default:
                break;
        }
    }

    private void client(View v) {
        switch (v.getId()) {
            case R.id.client_get:
                new MyHttpGetThread(UrlPath + "?un=8&kb=ga", "UTF-8") {

                    @Override
                    public void onSuccess(HttpResponse response, String responseStr) {
                        System.out.println(responseStr);
                    }
                }.start();
                break;
            case R.id.client_post:
                params.put("userName", "唉");
                params.put("passWord", "123");
                new MyHttpPostThread(UrlPath, params, "UTF-8") {
                    @Override
                    public void success(HttpResponse response, String responseStr) {
                        Log.i("MyHttpPostThread", responseStr);
//					System.out.println("回来的值:" + responseStr);
                    }
                }.start();
                break;
            case R.id.client_upload:
                //测试 封装的 文件提交表单
                File f = new File(FileUtils_SD.getFile(""), "高达 - 00.mp3");
//			DCIM\Camera
                File f2 = new File(FileUtils_SD.getFile("DCIM", "Camera"), "20150621_121327.jpg");
                map.put("String_uid", "love");
                map.put("File_upload", f);
                map.put("File_upload2", f2);
                new MyHttpFilePostThread(UrlPath, map, "utf-8") {
                    @Override
                    public void success(HttpResponse response, String responseStr) {
                        Log.e("result嘎嘎", "response的值:" + responseStr);
                    }
                }.start();

            default:
                break;
        }
    }

    private void conn(View v) {
        switch (v.getId()) {
            case R.id.con_get:
                MyConn_Utils.getInstance().executeHttpGet(UrlPath + "?un=8&kb=ga", "utf-8", new CallBack() {

                    @Override
                    public void onSuccess(String msg) {
                        System.out.println(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        System.err.println(msg);
                    }
                });
                break;
            case R.id.con_post:
                params.put("un", "luhaoaimama7");
                params.put("fr", "index");
                MyConn_Utils.getInstance().setNotPrintHeaders(true);
                MyConn_Utils.getInstance().executeHttpPost(UrlPath, params, "utf-8", new CallBack() {

                    @Override
                    public void onSuccess(String msg) {
                        System.out.println(msg);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
            case R.id.con_down:
//			String urlPath="http://img4.freemerce.com/ci49h5p.jpg";
                String urlPath = "http://down.360safe.com/360/inst.exe";
                DownLoader b = DownLoader.INSTANCE;
                final Button connectionDown = (Button) findViewById(R.id.con_down);
                b.downLoader(urlPath, FileUtils_SD.getFile(""), 4, new ProgressListener() {

                    @Override
                    public void onProgressUpdate(int current, int total,
                                                 float progress) {
                        connectionDown.setText(progress + "");
                        System.out.println("current:" + current + "  total:" + total + "  progress:" + progress);
                    }

                });
                break;
            case R.id.con_upload:
                //测试 封装的 文件提交表单
                File f = new File(FileUtils_SD.getFile(""), "高达 - 00.mp3");
//			DCIM\Camera
                File f2 = new File(FileUtils_SD.getFile("DCIM", "Camera"), "20150621_121327.jpg");
                map.put("String_uid", "love");
                map.put("File_upload", f);
                map.put("File_upload2", f2);
                MyConn_Utils.getInstance().executeHttpFile(UrlPath, map, "utf-8", new FileUpLoad_CallBack() {

                    @Override
                    public void onSuccess(String msg) {
                        System.out.println(msg);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onLoading(Long total, Long current) {
                        System.out.println("total:" + total + "/tcurrent" + current + "\t百分比：" + (float) current / total);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.cancelTag(this);
    }
}
