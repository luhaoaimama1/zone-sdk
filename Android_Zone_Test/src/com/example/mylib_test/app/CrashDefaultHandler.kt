package com.example.mylib_test.app

import com.zone.lib.utils.data.info.PrintLog
import java.lang.IllegalStateException
import java.lang.Thread.UncaughtExceptionHandler

object CrashDefaultHandler : UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: UncaughtExceptionHandler? = null

    @JvmStatic
    fun init2() {
        val defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        if (defaultUncaughtExceptionHandler == this) throw IllegalStateException("只能使用一次！")
        mDefaultHandler = defaultUncaughtExceptionHandler// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this)// 设置该CrashHandler2为程序的默认处理器
    }

    /**
     * 当UncaughtException发生时会转入该重写的方法来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        //do nothing

        PrintLog.crash(ex)
        PrintLog.stopLogs(object : PrintLog.StopListener {
            override fun onStop() {
                lastDeal(thread, ex)
            }
        })

        //如果超过一秒还没有上面还没有结束 则需要退出
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        lastDeal(thread, ex)
    }

    private fun lastDeal(thread: Thread, ex: Throwable) {
        //call original handler
        if (mDefaultHandler != null)
            mDefaultHandler?.uncaughtException(thread, ex)
        else {
            System.exit(0)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }

}