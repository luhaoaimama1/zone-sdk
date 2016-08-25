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

import and.utils.data.convert.DateUtil;
import and.utils.data.file2io2data.SharedUtils;
import and.utils.image.imageloader.ImageLoaderConfigUtils;
import and.utils.image.imageloader.ImageLoaderOptionsUtils;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.example.mylib_test.R;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.zone.okhttp.HttpConfig;
import com.zone.okhttp.ok;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // SDCard路径
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
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

        //记得设置加载中图片 不然下拉加载http的时候慢了 会显示复用之前的背景就会造成 从复用过来的图变成 加载后的图！！！
        ImageLoaderOptionsUtils.initShowImage(R.drawable.ic_stub, R.drawable.ic_empty, R.drawable.ic_error);
        //初始化ImageLoader
        ImageLoaderConfigUtils.initImageLoader(getApplicationContext(), ImageLoaderOptionsUtils.getNormalOption＿NotBuild().build(), false);

//		CrashHandler.getInstance().init(this);

//		每次loading页 发送bug日志  如果发送成功就删除
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(
//				this));


//        loggerTest();
        spUesrTest();

    }

    private void spUesrTest() {
        SharedUtils.User user = new SharedUtils.User();
        user.setDate(new Date());
        user.setUserName("zone");
        user.setUserPassword("123456");
        user.setIsRememberPassword(true);
        user.setLoginNum(1);
        SharedUtils.User user2 = new SharedUtils.User();
        user2.setDate(DateUtil.str2Date("2000-02-10 10:30:22", "yyyy-MM-dd HH:mm:ss"));
        user2.setUserName("zone2");
        user2.setUserPassword("1234567");
        user2.setIsRememberPassword(false);
        user2.setLoginNum(1);
        SharedUtils.User user3 = new SharedUtils.User();
        user3.setDate(DateUtil.str2Date("2007-02-10 10:30:22", "yyyy-MM-dd HH:mm:ss"));
        user3.setUserName("zone3");
        user3.setUserPassword("1234567");
        user3.setIsRememberPassword(false);
        user3.setLoginNum(1);
        SharedUtils.getInstance(this).setUser(user3);
        SharedUtils.getInstance(this).setUser(user2);
        SharedUtils.getInstance(this).setUser(user);
        List<SharedUtils.User> all = SharedUtils.getInstance(this).getAllUser();
        System.out.println("spUitls user的测试");
    }

    private void loggerTest() {
        Logger.initialize(new Settings()
                        .isShowMethodLink(true)
                        .isShowThreadInfo(false)
                        .setMethodOffset(0)
//						.setLogPriority(Log.VERBOSE)
        );

        levTest();
        objTest();
        jsonTest();
        locationTest();
        largeDataTest();
    }

    private void levTest() {
        Logger.v(null);
        Logger.d("%s test", "kale"); // 多参数 可以解决release版本中字符拼接带来的性能消耗
        String test = "abc %s def %s gh";
        Logger.d(test);

        //Logger.d(test, "s"); // Note:incorrect

        Logger.w("logger load custom tag");
        try {
            Class.forName("kale");
        } catch (ClassNotFoundException e) {
            Logger.e(e, "something happened"); // exception
        }

        Logger.d("first\nsecond\nthird");
        test();
    }

    private void test() {
        Logger.d("just test");
    }

    private void objTest() {
        // object
        Logger.object(new User("jack", "f"));
        // list
        Logger.object(Arrays.asList("kale", "jack", "tony"));
        // array
        Logger.object(new String[]{"Android", "ios", "wp"});
        double[][] doubles = {
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}
        };
        Logger.object(doubles);
    }

    private void jsonTest() {
        Logger.json(Dummy.SMALL_SON_WITH_NO_LINE_BREAK); // json
        String j = "[" + Dummy.JSON_WITH_NO_LINE_BREAK + "," + Dummy.JSON_WITH_LINE_BREAK + "]";
        Logger.json(j);
    }

    private void locationTest() {
        Foo.foo(); // other class
        new User("kale", "m").show();// Internal class
    }

    private void largeDataTest() {
        for (int i = 0; i < 20; i++) {
            Logger.d("No." + i);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部类中打log测试
    ///////////////////////////////////////////////////////////////////////////

    public static class User {

        private String name;

        private String sex;

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public void show() {
            Logger.d("name:%s sex:%s", name, sex);
        }
    }
}
