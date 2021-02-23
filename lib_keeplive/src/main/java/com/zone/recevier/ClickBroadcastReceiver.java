package com.zone.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zone.KeepLives;
public class ClickBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        KeepLives.log("ClickBroadcastReceiver:  点击消息");
        if(KeepLives.notifitionFactory.click(intent)){
            KeepLives.stopKeepLiveService(context);
        }
    }
}
