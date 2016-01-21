package com.example.mylib_test.activity.http;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.example.mylib_test.R;
import com.example.mylib_test.handler.HandlerTest;

import and.http.MyConn_Utils;
import and.http.MyConn_Utils.CallBack;
import and.http.MyConn_Utils.FileUpLoad_CallBack;
import and.http.client.MyHttpFilePostThread;
import and.http.client.MyHttpGetThread;
import and.http.client.MyHttpPostThread;
import and.http.downfile.DownLoader;
import and.http.downfile.DownLoader.ProgressListener;
import and.sd.FileUtils_SD;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Http_MainActivity extends Activity implements OnClickListener {
	 final	String UrlPath = "http://182.254.243.254:8080/Test/log";
	Map<String,Object> map= new HashMap<String, Object>();
	Map<String, String> params = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_http_test);
		//这段　只是学习下handler～　
		new Thread(){
			@SuppressLint("NewApi")
			public void run() {
				Looper.prepare();
				//Handler.Callback 还必须这么写　
				Handler abc=new Handler(new Handler.Callback() {
					
					@Override
					public boolean handleMessage(Message msg) {
						return false;
					}
				});
				Looper.loop();
				Looper.myLooper().quitSafely();
			};
		}.start();
		
	}
	@Override
	public void onClick(View v) {
		map.clear();
		params.clear();
		client(v);
		conn(v);
		tread(v);
		switch (v.getId()) {
		case R.id.handle:
			Button hand=(Button) findViewById(R.id.handle);
			HandlerTest ht=new HandlerTest();
			ht.updateView(hand);
			break;
		case R.id.noPullTest:
			startActivity(new Intent(this,NetworkNoPull_TestActivity.class));
			break;
		case R.id.noPull_GloboTest:
			startActivity(new Intent(this,NetworkNoPull_Globlo_TestActivity.class));
			break;
		case R.id.pullGet:
			startActivity(new Intent(this,NetworkPull_TestActivity.class));
			break;

		default:
			break;
		}
	}
	private void tread(View v) {
		switch (v.getId()) {
		case  R.id.asyncTask:
			startActivity(new Intent(this, AsyncTask_TestActivity.class));
			break;

		default:
			break;
		}
	}
	private void client(View v) {
		switch (v.getId()) {
		case R.id.client_get:
		new MyHttpGetThread(UrlPath+"?un=8&kb=ga", "UTF-8") {
			
			@Override
			public void onSuccess(HttpResponse response, String responseStr) {
				System.out.println(responseStr);
			}
		}.start();;
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
			File f2 = new File(FileUtils_SD.getFile("DCIM","Camera"), "20150621_121327.jpg");
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
			MyConn_Utils.getInstance().executeHttpGet(UrlPath+"?un=8&kb=ga", "utf-8", new CallBack() {
				
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
			String urlPath="http://down.360safe.com/360/inst.exe";
			DownLoader b=DownLoader.INSTANCE;
			final Button connectionDown=(Button) findViewById(R.id.con_down);
			b.downLoader(urlPath,  FileUtils_SD.getFile(""), 4,new ProgressListener() {

				@Override
				public void onProgressUpdate(int current, int total,
						float progress) {
					connectionDown.setText(progress+"");
					System.out.println("current:"+current+"  total:"+total+"  progress:"+progress);
				}
				
			});
			break;
		case R.id.con_upload:
			//测试 封装的 文件提交表单
			File f = new File(FileUtils_SD.getFile(""), "高达 - 00.mp3");
//			DCIM\Camera
			File f2 = new File(FileUtils_SD.getFile("DCIM","Camera"), "20150621_121327.jpg");
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
					System.out.println("total:"+total+"/tcurrent"+current+"\t百分比："+(float)current/total);
				}
				
				@Override
				public void onFailure(String msg) {
					
				}
			});

		default:
			break;
		}
	}


}
