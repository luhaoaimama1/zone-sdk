package com.zone;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

public abstract class NotificationFactory {

    /**
     * @return true:处理后隐藏消息，false则处理后不隐藏消息
     */
    public abstract boolean click(Intent intent);

    /**
     * @param context
     * @param clickIntent
     * @param callback 可以进行网络请求 延迟显示消息
     */
    public abstract void getNotification(Context context, Intent clickIntent, Callback callback);

    public interface Callback {
        @UiThread
        void onNotification(@Nullable Notification notification);
    }
}
