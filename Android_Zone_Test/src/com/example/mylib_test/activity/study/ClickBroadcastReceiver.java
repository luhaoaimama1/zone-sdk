package com.example.mylib_test.activity.study;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mylib_test.LogApp;

/**
 * DO NOT do anything in this Receiver!<br/>
 * <p>
 * Created by Mars on 12/24/15.
 */
public class ClickBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogApp.INSTANCE.d("receive=====>click!");
    }
}
