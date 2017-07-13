package com.example.mylib_test.activity.http;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.util.HandlerTest;
import com.example.mylib_test.app.Apps;
import com.squareup.leakcanary.RefWatcher;

import java.util.HashMap;
import java.util.Map;

import com.zone.lib.utils.zeventbus.ZEventBus;
import butterknife.ButterKnife;

public class Http_MainActivity extends Activity implements OnClickListener {
    Map<String, Object> map = new HashMap<>();
    Map<String, String> params = new HashMap<>();
    private static ZEventBus  zb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_test);
//        findViewById(R.id.zeventBus3).setOnClickListener(this);
        ButterKnife.bind(this);
        //这段　只是学习下handler～　
        new Thread() {
            @Override
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
        tread(v);
        retrofit(v);
        switch (v.getId()) {
            case R.id.handle:
                Button hand = (Button) findViewById(R.id.handle);
                HandlerTest ht = new HandlerTest();
                ht.updateView(hand);
                break;

            default:
                break;
        }
    }

    private void retrofit(View v) {
        switch (v.getId()) {
            case R.id.eventBus:
                startActivity(new Intent(this, EventBusActivity.class));
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


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
//        ok.cancelTag(this);
        super.onDestroy();
        RefWatcher refWatcher = Apps.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
