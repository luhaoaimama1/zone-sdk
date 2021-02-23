package com.example.mylib_test.app;
/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance load the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import com.zone.KeepLives;
import com.zone.NotificationFactory;
import com.zone.lib.Configure;
import com.zone.lib.utils.executor.ExecutorUtils;
import com.zone.lib.utils.data.info.PrintLog;
import com.zone.okhttp.HttpConfig;
import com.zone.okhttp.ok;
import com.zone.utils.NotificationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class Apps extends Application {
    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";

    public static Application context;

    // SDCard路径
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Configure.init(this);
        CrashDefaultHandler.init2();
        PrintLog.restart();
        init(this);
        PDFBoxResourceLoader.init(getApplicationContext());

        if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
//		try {
        Map<String, String> commonParamMap = new HashMap<>();
        commonParamMap.put("commonParamMap", "param_Common");
        Map<String, String> commonHeaderMap = new HashMap<>();
        commonHeaderMap.put("commonHeaderMap", "header_Common");
        Map<String, String> commonHeaderReMap = new HashMap<>();
        commonHeaderReMap.put("commonHeaderMap", "header_CommonReplace");
//			OkHttpUtils.setClient(OkHttpUtils.Certificates(getAssets().open("srca.cer")).perform());
//		try {
        ok.initConfig(new HttpConfig().setCommonHeaderAddMap(commonHeaderMap)
                        .setCommonHeaderReplaceMap(commonHeaderReMap).setCommonParamsMap(commonParamMap)
//					.Certificates(CER_12306)
//					.Certificates(getAssets().open("srca.cer")
        );
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

        Fresco.initialize(this);
//        refWatcher = LeakCanary.install(this);

        ExecutorUtils.execute(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        });
//        KeepLives.keepRelife(this,new KeepLives.Config()
//                .notificationDescription("嘎嘎")
//                .notificationIconRes(R.drawable.aaaaaaaaaaaab)
//                .notificationTitle("嘎嘎Title")
//                .builder()
//        );
        KeepLives.config(this, new NotificationFactory() {
            @Override
            public boolean click(Intent intent) {
                Intent intent2 = new Intent("android.intent.action.MAIN");
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.setComponent(new ComponentName("com.example.mylib_test", "com.example.mylib_test.MainActivity2"));
                context.startActivity(intent2);
                return true;
            }

            @Override
            public void getNotification(Context context, Intent clickIntent, Callback callback) {
                new Thread(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    GetExample example = new GetExample();
                    String response = null;
                    try {
                        response = example.run("https://www.baidu.com");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TIANZone", response);

                    String title = "title";
                    String content = "content_test";
                    NotiEntity entity = null;
                    clickIntent.putExtra("",entity);
                    Notification notification = NotificationUtils.createNotification(context, title, content, com.zone.R.mipmap.account_launcher, clickIntent);
                    callback.onNotification(notification);
                }).start();
            }
        });
    }

    public class GetExample {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    class NotiEntity implements Serializable {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        Apps application = (Apps) context.getApplicationContext();
        return application.refWatcher;
    }

    public static void init(Context context) {
        if (context instanceof Application) {
            //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    //x5內核初始化完成的回调，为true表示x5内核加载成功
                    //否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is " + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    Log.d("app", " onCoreInitFinished ");
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(context, cb);
        } else {
            throw new UnsupportedOperationException("context must be application...");
        }
    }


}
