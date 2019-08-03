package com.zone.lib

import java.util.HashMap

/**
 * Created by zone on 2016/10/24.
 */

object TimeDiffUtils {
    internal var timeMaps: MutableMap<String, Long> = HashMap()

    fun start(tag: String) {
        timeMaps[tag] = System.currentTimeMillis()
    }

    fun end(tag: String): Long {
        val startTime = timeMaps[tag] ?: 0L
        if (startTime == 0L) throw IllegalStateException("please first use Method:start()")
        val diff = System.currentTimeMillis() - startTime
        LogZSDK.i("TimeDiffUtils--->$tag:$diff")
        return diff
    }
}
