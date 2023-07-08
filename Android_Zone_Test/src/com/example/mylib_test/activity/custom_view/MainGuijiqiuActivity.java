package com.example.mylib_test.activity.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mylib_test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainGuijiqiuActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "MainActivity";
    private Button testBtn, focusBtn, normalBtn;
    private List<WatchFeedback> watchFeedbacks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_guijiqiu);
        initView();
        Observable.just(1).delay(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("=====》onNext:"+integer);
                    }
                });
    }

    private void initView() {
        focusBtn = findViewById(R.id.btn_focus);
        normalBtn = findViewById(R.id.btn_normal);

        focusBtn.setOnClickListener(this);
        normalBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_focus:
                for (int i = 0; i < watchFeedbacks.size(); i ++) {
                    WatchFeedback watchFeedback = watchFeedbacks.get(i);
                    watchFeedback.focus();
                    Log.e(TAG, "fb : " + watchFeedback);
                }
                break;

            case R.id.btn_normal:
                for (int i = 0; i < watchFeedbacks.size(); i ++) {
                    WatchFeedback watchFeedback = watchFeedbacks.get(i);
                    watchFeedback.normal();
                }
                break;
        }
    }

    //    public WatchFeedback watchFeedback;
    public void addWatchFeedback(WatchFeedback watchFeedback) {
//        this.watchFeedback = watchFeedback;
        watchFeedbacks.add(watchFeedback);
    }
    public interface WatchFeedback {
        void focus();
        void normal();
    }
}