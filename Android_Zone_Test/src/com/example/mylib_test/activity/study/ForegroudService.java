package com.example.mylib_test.activity.study;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.study.utils.NotificationUtils;

public class ForegroudService extends Service {

    public static final String NOTIFI = "notifi";
    public static final String NOTIFI_START = "start";
    private static final String NOTIFI_END = "end";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startService(@NonNull Context context) {
        Intent intentService = new Intent(context, ForegroudService.class);
        context.startService(intentService);
//        startServiceShowNotification(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void startServiceShowNotification(@NonNull Context context) {
        Intent intentService = new Intent(context, ForegroudService.class);
        intentService.putExtra(NOTIFI, NOTIFI_START);//0
//        context.startForegroundService(intentService);
        context.startService(intentService);
    }

    public static void startServiceHideNotification(@NonNull Context context) {
        Intent intentService = new Intent(context, ForegroudService.class);
        intentService.putExtra(NOTIFI, NOTIFI_END);//0
        context.startService(intentService);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Handler handler = new Handler();
    @Override
    public void onCreate() {
        log("onCreate！");
        super.onCreate();
        auto();
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            if(NOTIFI_START.equals(intent.getStringExtra(NOTIFI))){
                log("展示通知！");
                try {
                    createForegroudNotification(this);
                } catch (Exception e) {
                    log("展示通知Exception:"+e.getMessage());
                }
            }
            if(NOTIFI_END.equals(intent.getStringExtra(NOTIFI))){
                log("隐藏通知！");
                try {
                    stopForeground(13691);
                } catch (Exception e) {
                    log("隐藏通知Exception:"+e.getMessage());
                }
            }
        }
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    private void auto() {
        handler.postDelayed(()->{
            try {

                //zone todo: 2021/12/9  29有  降到了28 为了 编译debug
//                String s = ForegroudService.this.getForegroundServiceType() == 0 ? "后台" : "前台";
//                log("服务类型：" + s);
                auto();
            } catch (Exception e) {
                log("auto___Exception:"+e.getMessage());
            }
        },1000);
    }

    private void createForegroudNotification(@NonNull Context context) {
        String title = "title";
        String content = "content_test";
        Intent broadcastIntent = new Intent("CLICK_NOTIFICATION");
        //针对8.0以上发广播
        broadcastIntent.setComponent(new ComponentName("com.example.mylib_test", "com.example.mylib_test.activity.study.ClickBroadcastReceiver"));
        Notification notification = NotificationUtils.createNotification(context, title, content, R.drawable.aaaaaaaaaaaab, broadcastIntent);
        startForeground(13691, notification);
    }
    
    void log(String str){
        Log.d("ForegroudService",str);
    }
}
