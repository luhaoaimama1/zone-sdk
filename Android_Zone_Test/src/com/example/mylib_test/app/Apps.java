package com.example.mylib_test.app;
/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
import and.image.imageloader.ImageLoaderConfigUtils;
import and.image.imageloader.ImageLoaderOptionsUtils;
import and.log.Logger_Zone;
import and.log.Logger_Zone.LogStatue;
import network.engine.XutilsEngine;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.example.mylib_test.R;
import com.example.zone.http2rflist_zonelib.NetworkGlobalEngine;
import com.zone.okhttp.HttpConfig;
import com.zone.okhttp.ok;

import java.util.HashMap;
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
//			OkHttpUtils.setClient(OkHttpUtils.Certificates(getAssets().open("srca.cer")).build());
//		try {
			ok.initConfig(new HttpConfig().setCommonHeaderAddMap(commonHeaderMap).setCommonHeaderReplaceMap(commonHeaderReMap).setCommonParamsMap(commonParamMap)
//					.Certificates(CER_12306)
//					.Certificates(getAssets().open("srca.cer")
			);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		NetworkGlobalEngine.setGlobalEngine(XutilsEngine.class);
		//全局控制　打印日志
		Logger_Zone.setAllLogStatue(LogStatue.Close,false);
		
		//记得设置加载中图片 不然下拉加载http的时候慢了 会显示复用之前的背景就会造成 从复用过来的图变成 加载后的图！！！
		ImageLoaderOptionsUtils.initShowImage(R.drawable.ic_stub, R.drawable.ic_empty, R.drawable.ic_error);
		//初始化ImageLoader
		ImageLoaderConfigUtils.initImageLoader(getApplicationContext(),ImageLoaderOptionsUtils.getNormalOption＿NotBuild().build(),false);
		
//		CrashHandler.getInstance().init(this);

//		每次loading页 发送bug日志  如果发送成功就删除
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(
//				this));


	}
}
