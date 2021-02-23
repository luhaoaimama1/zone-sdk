package com.zone.keeplive.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zone.KeepLives;
import com.zone.R;
import com.zone.keeplive.receiver.ScreenOffOnBroadcastReceiver;
import com.zone.utils.NotificationUtils;
import com.zone.utils.PowerManagers;

import java.util.List;

public class KeepLiveService extends Service {
    //zone todo: 2021/2/22  绑定 失联后重复绑定并启动
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
    private ScreenOffOnBroadcastReceiver mScreenOffOnBroadcastReceiver = new ScreenOffOnBroadcastReceiver();
    private boolean bindDemonServiceSuccess;

    @Override
    public void onCreate() {
        super.onCreate();
        registerScreenOffOnReceiver();
        screenOffOnCheck();
        //zone todo: 2021/2/22  前台消息制造工厂
    }

    private void screenOffOnCheck() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean screenOn = PowerManagers.isScreenOn(KeepLiveService.this);
                KeepLives.log("KeepLiveService: \t 定时 检查屏幕："+ screenOn);
//                mScreenOffOnObserver.onReceive(screenOn);
                ScreenOffOnBroadcastReceiver.sendBroad(KeepLiveService.this, screenOn);
                screenOffOnCheck();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepLives.log("KeepLiveService: \t onDestroy");
        unregisterScreenOffOnReceiver();
    }

    private boolean isStopKeepLiveService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //zone todo: 2021/2/22  前台服务
        createForegroudNotification(this);
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
    private void createForegroudNotification(@NonNull Context context) {
        String title = "title";
        String content = "content_test";
        Intent broadcastIntent = new Intent("CLICK_NOTIFICATION");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName("com.example.mylib_test", "com.zone.utils.ClickBroadcastReceiver"));


        Notification notification = NotificationUtils.createNotification(context, title, content, R.mipmap.account_launcher, broadcastIntent);
        startForeground(13691, notification);

//        stopForeground(13691);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerScreenOffOnReceiver() {
        try {
            KeepLives.log("KeepLiveService \t registerScreenOffOnReceiver 进程名字：" + returnName());
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ScreenOffOnBroadcastReceiver.ACTION_SCREEN_OFF);
            intentFilter.addAction(ScreenOffOnBroadcastReceiver.ACTION_SCREEN_ON);
            registerReceiver(mScreenOffOnBroadcastReceiver, intentFilter);
        } catch (Exception e) {
            KeepLives.logE("KeepLiveService", "registerScreenOffOnReceiver error:" + e.getMessage());
        }
    }

    private void unregisterScreenOffOnReceiver() {
        if (mScreenOffOnBroadcastReceiver == null) return;
        try {
            unregisterReceiver(mScreenOffOnBroadcastReceiver);
            mScreenOffOnBroadcastReceiver = null;
            KeepLives.log("KeepLiveService \t unregisterScreenOffOnReceiver");
        } catch (Exception e) {
            KeepLives.logE("KeepLiveService", "unregisterReceiver error:" + e.getMessage());
        }
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
            //zone todo: 2021/2/22  应该会触发 防止二者无限循环调用
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
