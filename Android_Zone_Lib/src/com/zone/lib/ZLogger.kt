/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zone.lib

import android.text.TextUtils
import android.util.Log

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * Author: wyouflf(原始),Zone(扩展)
 * Date: 13-7-24
 * Time: 下午12:23
 */

enum class LogLevel {
    d, e, i, v, w, wtf
}

open class ZLogger protected constructor(var tagName: String) {

    //这个是在app中配置。因为一个项目可以有多个lib但是仅仅会有一个app,默认lib不会打印
    //优先级 等级>logger实体类的某个等级方法
    //Tips: 最好使用变量在一个地方 例如app而不是lib中。 如果 多个地方用这个就不好用了。
    companion object {
        //空 ：全部等级的都可以输出 。如果仅仅有e的话  仅仅e会打印
        var logLevelList: HashSet<LogLevel> = HashSet<LogLevel>()

        //空： 全部的都不会打印
        // 例如 我写一个B库用到A库 连个库都用到此log库。正常引用A库是A库不会打印的 。
        // 如果我想开启A库的打印。把A的打印实体类 填入即可
        var mayLoggerList: HashSet<ZLogger> = HashSet<ZLogger>()
    }

    //空： 全部的都不会打印
    //非空： 不包含不打印
    fun loggerOK(): Boolean {
        return if (mayLoggerList.isEmpty()) false
        else mayLoggerList.contains(this)
    }

    // 空 ：全部等级的都可以输出
    // 非空：不包含 不输出。
    fun levelOK(nowLevel: LogLevel): Boolean {
        return if (logLevelList.isEmpty()) true
        else logLevelList.contains(nowLevel)
    }

    private fun levelOrLoggerError(logLevel: LogLevel) = !levelOK(logLevel) || !loggerOK()
    fun levelOrLoggerOK(logLevel: LogLevel) = !levelOrLoggerError(logLevel)

    private fun generateTag(): String = tagName

    //https://blog.csdn.net/qq_34603736/article/details/73497360
    //这个位置只有写成固定格式 "("+fileName+":"+lineNumber+")"，才能实现定位功能
    private fun getWarpContent(content: String): String {
        val caller = Throwable().stackTrace[2]
        return "[ (${caller.fileName}:${caller.lineNumber})#${caller.methodName} ] $content"
    }

    fun d(content: String) {
        if (levelOrLoggerError(LogLevel.d)) return
        Log.d(generateTag(), getWarpContent(content))
    }

    fun d(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.d)) return
        Log.d(generateTag(), getWarpContent(content), tr)
    }

    fun e(content: String) {
        if (levelOrLoggerError(LogLevel.e)) return
        Log.e(generateTag(), getWarpContent(content))
    }

    fun e(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.e)) return
        Log.e(generateTag(), getWarpContent(content), tr)
    }

    fun i(content: String) {
        if (levelOrLoggerError(LogLevel.i)) return
        Log.i(generateTag(), getWarpContent(content))
    }

    fun i(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.i)) return
        Log.i(generateTag(), getWarpContent(content), tr)
    }

    fun v(content: String) {
        if (levelOrLoggerError(LogLevel.v)) return
        Log.v(generateTag(), getWarpContent(content))
    }

    fun v(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.v)) return
        Log.v(generateTag(), content, tr)
    }

    fun w(content: String) {
        if (levelOrLoggerError(LogLevel.w)) return
        Log.w(generateTag(), getWarpContent(content))
    }

    fun w(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.w)) return
        Log.w(generateTag(), getWarpContent(content), tr)
    }

    fun w(tr: Throwable) {
        if (levelOrLoggerError(LogLevel.w)) return
        Log.w(generateTag(), tr)
    }

    fun wtf(content: String) {
        if (levelOrLoggerError(LogLevel.wtf)) return
        Log.wtf(generateTag(), getWarpContent(content))
    }

    fun wtf(content: String, tr: Throwable) {
        if (levelOrLoggerError(LogLevel.wtf)) return
        Log.wtf(generateTag(), getWarpContent(content), tr)
    }

    fun wtf(tr: Throwable) {
        if (levelOrLoggerError(LogLevel.wtf)) return
        Log.wtf(generateTag(), tr)
    }

}
