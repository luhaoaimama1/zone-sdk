package com.zone.keeplive.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.zone.KeepLives;

/**
 * 守护进程
 */
public class DemonService extends Service {
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            KeepLives.log("DemonService: \t onServiceDisconnected");
            bindKeepLiveServiceSuccess = false;
            bindDemonService();
        }
    };

    private boolean bindKeepLiveServiceSuccess;

    private boolean isStopKeepLiveService;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KeepLives.log("DemonService: \t onStartCommand");
        if (intent != null && intent.getIntExtra("INTENT_SERVICE_STATE", 0) == Contants.INTENT_SERVICE_STATE_STOP) {
            isStopKeepLiveService = true;
        }
        if (isStopKeepLiveService) {
            unbindDemonService();
            stopSelf();
        } else {
            bindDemonService();
        }
        runing();
        return START_STICKY;
    }

    private void runing() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runing();
            }
        },1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepLives.log("DemonService: \t onDestroy");
    }

    //绑定守护进程
    private void bindDemonService() {
        if (isStopKeepLiveService) return;
        try {
            KeepLives.log("DemonService: \t bindService->KeepLiveService before bindKeepLiveServiceSuccess:" + bindKeepLiveServiceSuccess);
            if(!bindKeepLiveServiceSuccess){
                Intent intent = new Intent(DemonService.this, KeepLiveService.class);
                startService(intent);
                bindKeepLiveServiceSuccess = this.bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
                KeepLives.log("DemonService: \t bindService->KeepLiveService");
            }else{
                KeepLives.log("DemonService: \t 无需 bind");
            }
        } catch (Exception e) {
            KeepLives.logE("KeepLiveService", "bindDemonService error:" + e.getMessage());
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KeepLives.log("DemonService: \t onUnbind intent:"+intent.getComponent());
        bindKeepLiveServiceSuccess = false;
        bindDemonService();
        return super.onUnbind(intent);
    }

    private void unbindDemonService() {
        if (connection != null && bindKeepLiveServiceSuccess) {
            try {
                unbindService(connection);
                bindKeepLiveServiceSuccess = false;
                KeepLives.log("DemonService: \t unbindService");
            } catch (Exception e) {
                KeepLives.logE("DemonService", "unbindDemonService error:" + e.getMessage());
            }
        }
    }
}
