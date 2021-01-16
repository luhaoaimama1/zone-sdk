package com.zone.base_keeplive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;

public class KeepLiveBroadcasts {
    public static final String TAG = "KeepLiveBroadcasts";
    public static final String KEY_FROM = "from";
    public static final String KEY_ACTION = "action";

    public static boolean enableLog = true;

    public static void sendBroadcast(@NonNull Context context, String from, String action) {
        KeepLiveBroadcasts.log("from:" + from + "\t action:" + action);
        Intent broadcastIntent = new Intent("com.zone.keeplive");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName("com.example.mylib_test","com.zone.keeplives.MyBroadcastReceiver"));
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FROM, from);
        bundle.putString(KEY_ACTION, action);
        broadcastIntent.putExtras(bundle);
        context.sendBroadcast(broadcastIntent);
    }

    public static void log(String log) {
        if (enableLog) {
            Log.d(TAG, log);
        }
    }

    public static void logE(String tag1,String log) {
        if (enableLog) {
            Log.e(TAG, tag1+"===>"+log);
        }
    }
}
