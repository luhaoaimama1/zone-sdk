package com.zone.lib.utils.activity_fragment_ui.handler

import android.os.Handler
import android.os.Looper

/**
 * @author MaTianyu
 * @date 2015-03-12
 */
object HandlerUiUtil {
    val HANDLER = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        HANDLER.post(runnable)
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        HANDLER.postDelayed(runnable, delayMillis)
    }

    fun removeCallbacks(runnable: Runnable) {
        HANDLER.removeCallbacks(runnable)
    }
}
