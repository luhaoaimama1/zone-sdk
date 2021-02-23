package com.zone.keeplive.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.zone.KeepLives;
import com.zone.NotificationFactory;
import com.zone.keeplive.receiver.ScreenOffOnObserver;
import com.zone.utils.PowerManagers;

import java.util.List;

public class KeepLiveService extends Service {
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            KeepLives.log("KeepLiveService: \t onServiceDisconnected");
            bindDemonServiceSuccess = false;
            bindDemonService();
        }
    };

    private Handler handler = new Handler();
    private ScreenOffOnObserver mScreenOffOnBroadcastReceiver = new ScreenOffOnObserver();
    private boolean bindDemonServiceSuccess;

    @Override
    public void onCreate() {
        super.onCreate();
        bindForgroundNotification();
        screenOffOnCheck();
    }

    //zone todo: 2021/2/22  前台消息制造工厂
    private void bindForgroundNotification() {
        Intent broadcastIntent = new Intent("CLICK_NOTIFICATION");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName(getPackageName(), "com.zone.recevier.ClickBroadcastReceiver"));
        KeepLives.notifitionFactory.getNotification(this, broadcastIntent, new NotificationFactory.Callback() {
            @Override
            public void onNotification(@Nullable final Notification notification) {
                if (notification != null) {
                    startForeground(13691, notification);
                }
            }
        });
    }

    private void screenOffOnCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean screenOn = PowerManagers.isScreenOn(KeepLiveService.this);
                KeepLives.log("KeepLiveService: \t 定时 检查屏幕："+ screenOn);
                mScreenOffOnBroadcastReceiver.onReceive(KeepLiveService.this,screenOn);
                screenOffOnCheck();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepLives.log("KeepLiveService: \t onDestroy");
    }

    private boolean isStopKeepLiveService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KeepLives.log("KeepLiveService: \t onStartCommand");
        if (intent != null && intent.getIntExtra("INTENT_SERVICE_STATE", 0) == Contants.INTENT_SERVICE_STATE_STOP) {
            isStopKeepLiveService = true;
        }

        if (isStopKeepLiveService) {
            unbindDemonService();
            stopSelf();
        } else {
            bindDemonService();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //绑定守护进程
    private void bindDemonService() {
        if (isStopKeepLiveService) return;
        try {
            KeepLives.log("KeepLiveService: \t bindService->DemonService before bindKeepLiveServiceSuccess:" + bindDemonServiceSuccess);
            if (!bindDemonServiceSuccess) {
                Intent intent = new Intent(this, DemonService.class);
                startService(intent); //一个service可以多次启动 不过会走对应的onStart罢了
                bindDemonServiceSuccess = this.bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
                KeepLives.log("KeepLiveService: \t bindService->DemonService");
            }else{
                KeepLives.log("KeepLiveService: \t 无需 bind");
            }
        } catch (Exception e) {
            KeepLives.logE("KeepLiveService", "bindDemonService error:" + e.getMessage());
        }
    }

    private String returnName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        if (runningAppProcessInfos != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    processName = appProcess.processName;
                    break;
                }
            }
        }
        return processName;
    }

    private void unbindDemonService() {
        if (connection != null && bindDemonServiceSuccess) {
            try {
                unbindService(connection);
                bindDemonServiceSuccess = false;
                KeepLives.log("KeepLiveService: \t unbindService");
            } catch (Exception e) {
                KeepLives.logE("KeepLiveService", "unbindDemonService error:" + e.getMessage());
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KeepLives.log("KeepLiveService: \t onUnbind intent:" + intent.getComponent());
        bindDemonServiceSuccess = false;
        bindDemonService();
        return super.onUnbind(intent);
    }
}
