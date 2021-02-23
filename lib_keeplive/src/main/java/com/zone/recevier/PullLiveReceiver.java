package com.zone.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zone.KeepLives;

/**
 * 进程是主进程 所以 主进程的application会被拉活 那么会走应用的application的onCreate
 */
public class PullLiveReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String from = intent.getStringExtra(KeepLiveBroadcasts.KEY_FROM);
            String action = intent.getStringExtra(KeepLiveBroadcasts.KEY_ACTION);
            KeepLives.log("receive=====>from:" + from + "\t action:" + action);
        }else{
            KeepLives.logE("MyBroadcastReceiver", "intent is null");
        }
    }
}
