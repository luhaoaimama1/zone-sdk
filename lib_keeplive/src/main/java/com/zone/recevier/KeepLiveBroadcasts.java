package com.zone.recevier;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zone.KeepLives;

public class KeepLiveBroadcasts {

    public static final String KEY_FROM = "from";
    public static final String KEY_ACTION = "action";
    public static void sendBroadcast(@NonNull Context context, String from, String action) {
        KeepLives.log("from:" + from + "\t action:" + action);
        Intent broadcastIntent = new Intent("com.zone.keeplive");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName("com.example.mylib_test","com.zone.recevier.PullLiveReceiver"));
//        broadcastIntent.setComponent(new ComponentName("com.zone","com.zone.recevier.PullLiveReceiver"));
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FROM, from);
        bundle.putString(KEY_ACTION, action);
        broadcastIntent.putExtras(bundle);
        context.sendBroadcast(broadcastIntent);
    }

}
