package com.example.mylib_test.activity.pop_dialog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.mylib_test.MainActivity2;
import com.example.mylib_test.R;
import com.zone.lib.utils.activity_fragment_ui.handler.HandlerUiUtil;

//浮动通知实现：https://stackoverflow.com/questions/33510861/how-to-show-heads-up-notifications-android
public class NoticationService extends Service {
    private FloatWindowPop floatWindowPop;

    @Override
    public void onCreate() {
        super.onCreate();
        notifyABC();


    }

    private void notifyABC() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String mylib_channel = "Mylib_channel";

            NotificationChannel chan1 = new NotificationChannel(mylib_channel, "Mylib", NotificationManager.IMPORTANCE_HIGH);

            chan1.setLightColor(Color.CYAN);
            chan1.setLockscreenVisibility(NotificationCompat.VISIBILITY_PRIVATE);
//            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(chan1);
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(chan1);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), mylib_channel);
            builder.setContentTitle("title")
                    .setContentText("content")
                    .setSmallIcon(R.drawable.icon)
                    .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .setAutoCancel(true)

                    //浮动通知 设置
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            //加上跳转
            Intent intent2 = new Intent(this, MainActivity2.class);
            intent2.setAction("android.intent2.action.MAIN");
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.addCategory("android.intent2.category.LAUNCHER");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
            builder.setContentIntent(pendingIntent);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            HandlerUiUtil.INSTANCE.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //不加也是好用的
                    notificationManager.cancel(0);
                }
            }, 2000);

            //浮动通知 设置
            notificationManager.notify(0, builder.build());

//            this.startForeground(244, builder.build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notifyABC();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
