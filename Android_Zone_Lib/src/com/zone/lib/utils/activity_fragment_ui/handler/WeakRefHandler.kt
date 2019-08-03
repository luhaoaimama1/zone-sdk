package com.zone.lib.utils.activity_fragment_ui.handler

import android.app.Activity
import android.os.Handler
import android.os.Message

import java.lang.ref.WeakReference

/**
 * 主要是防止空缓存
 */
class WeakRefHandler(activity: Activity) : Handler() {
    private val mCallback: WeakReference<Activity> = WeakReference(activity)

    override fun handleMessage(msg: Message) {
        if (mCallback.get() == null) return
        handleMessageSafe(msg)
    }

    fun handleMessageSafe(msg: Message) {
    }

    //Tips：在生命周期结束的时候调用
    fun removeAllMessage() {
        removeCallbacksAndMessages(null)//remove全部任务！
    }
}