package com.zone.keeplives.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zone.base_keeplive.KeepLiveBroadcasts;

public class Service1 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO do some thing what you want..
        KeepLiveBroadcasts.sendBroadcast(this,"Marsdaemon","");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }
}
