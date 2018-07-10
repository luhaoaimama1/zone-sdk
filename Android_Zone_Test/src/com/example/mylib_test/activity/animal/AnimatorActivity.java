package com.example.mylib_test.activity.animal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mylib_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends Activity {
    private final String TAG = "AnimatorActivity";
    @BindView(R.id.iv)
    ImageView iv;
    private ObjectAnimator objAni;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animal_animator);
        ButterKnife.bind(this);
        objAni = ObjectAnimator.ofFloat(iv, "translationX", 0, 300).setDuration(1000);
        objAni.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                log("onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                log("onAnimationResume");
            }
        });
        objAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                log("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                log("onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                log("onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                log("onAnimationRepeat");
            }
        });

        log("克隆是否同一对象？" + (objAni.clone() == objAni));

    }

    private void log(String str) {
        Log.e(TAG, str);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @OnClick({R.id.bt_startDelay,R.id.bt_start, R.id.bt_reverse, R.id.bt_pause, R.id.bt_resume, R.id.bt_information, R.id.bt_setCurrentFraction, R.id.bt_setCurrentPlayTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_startDelay:
                if (objAni.isRunning())
                    objAni.end();
                objAni.setStartDelay(1500);
                objAni.start();
                log("延迟一秒开始执行");
                break;
            case R.id.bt_start:
                if (objAni.isRunning())
                    objAni.end();
                objAni.start();
                break;
            case R.id.bt_reverse:
                if (objAni.isRunning())
                    objAni.end();
                objAni.reverse();
                break;
            case R.id.bt_pause:
                if (!objAni.isPaused())
                    objAni.pause();
                break;
            case R.id.bt_resume:
                if (objAni.isPaused())
                    objAni.resume();
                break;
            case R.id.bt_information:
                information();
                break;
            case R.id.bt_setCurrentFraction:
                if (objAni.isRunning())
                    objAni.cancel();
                objAni.setCurrentFraction(0.5F);
                break;
            case R.id.bt_setCurrentPlayTime:
                if (objAni.isRunning())
                    objAni.cancel();
                objAni.setCurrentPlayTime(objAni.getDuration() / 2);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void information() {
        log("information:--->getCurrentPlayTime:" + objAni.getCurrentPlayTime()
                + "\t getAnimatedValue:" + objAni.getAnimatedValue()
                + "\t getAnimatedFraction:" + objAni.getAnimatedFraction()
                + "\t isPaused:" + objAni.isPaused()
                + "\t isStart:" + objAni.isStarted()
                + "\t isRunning:" + objAni.isRunning()
        );
    }
}
