package com.zone.lib.utils.activity_fragment_ui.handler

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

/**
 * 主要是防止空缓存
 */
open class WeakRefHandler(callback: Callback) : Handler() {

    interface Callback {
        fun handleMessageSafe(msg: Message)
    }

    private val mCallback: WeakReference<Callback> = WeakReference(callback)

    override fun handleMessage(msg: Message) {
        mCallback.get()?.handleMessageSafe(msg)
    }

    //Tips：在生命周期结束的时候调用
    fun removeAllMessage() {
        removeCallbacksAndMessages(null)//remove全部任务！
    }
}
