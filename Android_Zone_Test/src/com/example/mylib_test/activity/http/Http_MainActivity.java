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
import com.example.mylib_test.handler.HandlerTest;
import com.zone.okhttp.ok;
import java.util.HashMap;
import java.util.Map;
import butterknife.ButterKnife;

public class Http_MainActivity extends Activity implements OnClickListener {
    Map<String, Object> map = new HashMap<>();
    Map<String, String> params = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_test);
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
        super.onDestroy();
        ok.cancelTag(this);
    }
}
