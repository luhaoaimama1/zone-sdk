package com.zone.keeplive.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zone.KeepLives;

public class ScreenOffOnObserver {
    private Handler mHander = new Handler();
    private boolean mScreenOn = true;

    private NoVolumePlayer noVolumePlayer = new NoVolumePlayer();

    public ScreenOffOnObserver() {
    }

    public void onReceive(final Context context, boolean screenOn) {
        KeepLives.log("ScreenOffOnReceiver \t action:" + screenOn);
        if (mScreenOn == screenOn) return;
        mScreenOn = screenOn;

        if (!mScreenOn) { //防止频繁启动
            mHander.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mScreenOn) {
                        noVolumePlayer.playCircle(context);
                    }
                }
            }, 2500);
        } else { //屏幕亮了 不播放音乐
            noVolumePlayer.destory();
        }
    }


}
