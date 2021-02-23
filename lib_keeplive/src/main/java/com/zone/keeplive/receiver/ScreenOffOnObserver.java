package com.zone.keeplive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zone.KeepLives;
import com.zone.keeplive.activity.utils.NoVolumePlayer;

public class ScreenOffOnObserver {

    public static final String ACTION_SCREEN_ON = "_screen_on";
    public static final String ACTION_SCREEN_OFF = "_screen_off";

    //zone todo: 2021/2/23  包名统一 提取
    public void sendBroad(Context context, boolean screenOn) {
        Intent broadcastIntent = new Intent();
        if (screenOn) {
            broadcastIntent.setAction(ACTION_SCREEN_ON);
        } else {
            broadcastIntent.setAction(ACTION_SCREEN_OFF);
        }
        context.sendBroadcast(broadcastIntent);
    }

    private Handler mHander = new Handler();
    private boolean mScreenOn = true;

    private NoVolumePlayer noVolumePlayer = new NoVolumePlayer();

    public ScreenOffOnObserver() {
    }

    public void onReceive(final Context context, boolean screenOn) {
        KeepLives.log("ScreenOffOnReceiver \t action:" + screenOn);
        if (mScreenOn == screenOn) return;
        mScreenOn = screenOn;
        if (!mScreenOn) {
            //防止频繁启动一像素保护
            mHander.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mScreenOn) {
                        noVolumePlayer.playCircle(context);
                    }
                }
            }, 2500);
        } else {
            noVolumePlayer.destory();
        }
    }


}
