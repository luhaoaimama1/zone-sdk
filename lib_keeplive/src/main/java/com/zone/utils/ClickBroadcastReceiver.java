package com.zone.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * DO NOT do anything in this Receiver!<br/>
 * <p>
 * Created by Mars on 12/24/15.
 */
public class ClickBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        LogApp.INSTANCE.d("receive=====>click!");
//        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent2.setComponent(new ComponentName("com.example.mylib_test","com.zone.keeplive.activity.OnePixelActivity"));
        Intent intent2 = new Intent("android.intent.action.MAIN");
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setComponent(new ComponentName("com.example.mylib_test","com.example.mylib_test.MainActivity2"));
        context.startActivity(intent2);
    }
}
