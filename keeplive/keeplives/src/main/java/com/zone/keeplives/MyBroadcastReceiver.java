package com.zone.keeplives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zone.base_keeplive.KeepLiveBroadcasts;

/**
 * DO NOT do anything in this Receiver!<br/>
 * <p>
 * Created by Mars on 12/24/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String from = intent.getStringExtra(KeepLiveBroadcasts.KEY_FROM);
            String action = intent.getStringExtra(KeepLiveBroadcasts.KEY_ACTION);
            KeepLiveBroadcasts.log("receive=====>from:" + from + "\t action:" + action);
//            if(context.getApplicationContext() instanceof Application){
//                KeepLives.keepLive((Application)context.getApplicationContext());
//            }
        }
    }
}
