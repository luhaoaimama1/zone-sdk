package com.zone.lib.utils.activity_fragment_ui.handler

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

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

    fun postSafeExist(runnable: Runnable, obj: Any?) {
        postDelayedExist(runnable, 0L, obj)
    }

    fun postDelayedExist(runnable: Runnable, delayMillis: Long, objIsExist: Any?) {
        val weakObj = WeakReference(objIsExist)
        val runableProxy = Runnable {
            weakObj.get()?.let {
                runnable.run()
            }
        }
        if (delayMillis == 0L) HANDLER.post(runableProxy)
        else HANDLER.postDelayed(runableProxy, delayMillis)

    }

    fun postSafe(runnable: Runnable, isSafe: () -> Boolean) {
        postSafeDelayed(runnable, 0L, isSafe)
    }

    fun postSafeDelayed(runnable: Runnable, delayMillis: Long, isSafe: () -> Boolean) {
        val runableProxy = Runnable {
            if (isSafe()) {
                runnable.run()
            }
        }
        if (delayMillis == 0L) HANDLER.post(runableProxy)
        else HANDLER.postDelayed(runableProxy, delayMillis)
    }
}
