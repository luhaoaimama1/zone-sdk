package com.zone.lib

import android.content.Context
import java.lang.IllegalStateException

object Configure {

    private var context: Context? = null

    @JvmStatic
    fun getApplicationContext(): Context =
        if (context == null) throw IllegalStateException("Please use ZSDKConfig.init(context)")
        else context as Context

    @JvmStatic
    fun init(context: Context) {
        if (this.context != null) return
        this.context = context.applicationContext
    }
}