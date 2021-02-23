package com.zone.recevier;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.zone.KeepLives;

/**
 * 进程是主进程 所以 主进程的application会被拉活 那么会走应用的application的onCreate
 */
public class PullLiveReceiver extends BroadcastReceiver {

    public static final String KEY_FROM = "from";
    public static final String KEY_ACTION = "action";

    public static void sendBroadcast(@NonNull Context context, String from, String action) {
        KeepLives.log("from:" + from + "\t action:" + action);
        Intent broadcastIntent = new Intent("com.zone.keeplive");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName(context.getPackageName(), "com.zone.recevier.PullLiveReceiver"));
//        broadcastIntent.setComponent(new ComponentName("com.example.mylib_test","com.zone.recevier.PullLiveReceiver"));
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FROM, from);
        bundle.putString(KEY_ACTION, action);
        broadcastIntent.putExtras(bundle);
        context.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String from = intent.getStringExtra(KEY_FROM);
            String action = intent.getStringExtra(KEY_ACTION);
            KeepLives.log("PullLiveReceiver receive=====>from:" + from + "\t action:" + action);
        } else {
            KeepLives.logE("PullLiveReceiver", "intent is null");
        }
    }
}
