package com.zone.lib.utils.activity_fragment_ui.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 主要是防止空缓存
 */
public class ZHandler extends Handler {
    private final WeakReference<Activity> mCallback;

    public ZHandler(Activity activity) {
        mCallback = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mCallback.get() == null) {
            return;
        }
        handleMessageReplace(msg);
    }

    public void handleMessageReplace(Message msg) {

    }

    /**
     * Tips：在生命周期结束的时候调用
     */
    public void removeAllMessage() {
        removeCallbacksAndMessages(null);//remove全部任务！
    }
}