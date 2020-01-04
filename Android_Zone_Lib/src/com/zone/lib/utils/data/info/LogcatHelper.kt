package com.zone.lib.utils.data.info


import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicBoolean


/**
 *  日志等级：
 *  V —— Verbose(最低，输出得最多)  (v,d,i,w,e)
 *  D —— Debug                   (d,i,w,e)
 *  I —— Info                    (i,w,e)
 *  W —— Warning                 (w,e)
 *  E —— Error                   (e)
 *  F —— Fatal
 *  S —— Silent（最高，啥也不输出）
 */
enum class LogcatHelperLevel {
    i, v, d, w, e, f, s
}

/**
 * log日志统计保存
 * 每次开启应用，就会把上次的log信息覆盖
 * @author way
 */

class LogcatHelper private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: LogcatHelper? = null
        @JvmField
        var PATH_LOGCAT: String = ""
        @JvmStatic
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: LogcatHelper(context).also { INSTANCE = it }
        }

        // 2012年10月03日 23:41:31
        private val fileName: String = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))

        // 2012-10-03 23:41:31
        private val dateEN: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
    }

    private var mLogDumper: LogDumper
    private val mPId: Int


    init {
        init(context)
        mPId = android.os.Process.myPid()
        mLogDumper = LogDumper(mPId.toString(), PATH_LOGCAT)
    }

    /**
     * 初始化目录
     */
    fun init(context: Context) {
        if (TextUtils.isEmpty(PATH_LOGCAT)) {
            PATH_LOGCAT = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                // 优先保存到SD卡中
                Environment.getExternalStorageDirectory().absolutePath + File.separator + "logcat"
            } else {
                // 如果SD卡不存在，就保存到本应用的目录下 storage/sdcard0/logcat
                context.applicationContext.filesDir.absolutePath + File.separator + "logcat"
            }
        }
        val file = File(PATH_LOGCAT)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun start() {
        mLogDumper.start()
    }

    fun start(vararg logLevels: LogcatHelperLevel) {
        mLogDumper.start(*logLevels)
    }

    fun stop() {
        mLogDumper.stopLogs()
    }

    fun isRuning() = mLogDumper.isRuning()

    fun getLogFile() = getLogFile(PATH_LOGCAT)

    private fun getLogFile(dir: String) = File(dir, "Logcat-" + fileName + ".txt").path

    private inner class LogDumper(private val mPID: String, val dir: String) : Runnable {
        private var logcatProc: Process? = null
        private var mReader: BufferedReader? = null
        private var mQuitFlag = AtomicBoolean(false)
        private var mIsQuit = AtomicBoolean(true)
        private var mIsStart = AtomicBoolean(false)
        internal var cmds: String? = null
        private var out: FileOutputStream? = null
        private var callback: () -> Unit = {
            if (mIsStart.get()) {
                realStart()
            }
        }

        private fun initCmds(list: Array<out LogcatHelperLevel>?) {
            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             */

            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            // cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
            // cmds = "logcat -s way";//打印标签过滤信息
            val sb = StringBuilder()
            list?.forEach {
                sb.append(" *:${it.name}")
            }
//          cmds = "logcat *:e *:i *:d *:w *:s *:f *:v | grep \"($mPID)\""
            cmds = "logcat$sb | grep \"($mPID)\""
        }

        fun stopLogs() {
            if (!mIsQuit.get()) {
                mQuitFlag.set(false)
                //因为 when里的 readLine 堵塞ing 需要得到内容才能继续执行！
                Log.e("LogcatHelper", "quit logs")
            } else {
                callback()
            }
        }

        /**
         * 调用了start 一定要run
         */
        fun start(vararg logLevels: LogcatHelperLevel) {
            initCmds(logLevels)
            startInner()
        }

        fun start() {
            initCmds(null)
            startInner()
        }

        private fun startInner() {
            mIsStart.set(true)
            stopLogs()
        }

        fun isRuning() = !mIsQuit.get()

        private fun realStart() {
            if (mIsQuit.get()) {
                mQuitFlag.set(true)
                mIsQuit.set(false)
                AsyncTask.SERIAL_EXECUTOR.execute {
                    run()
                }
            } else Log.e("LogcatHelper", " logcat is colllectioning!")
        }

        override fun run() {
            mIsStart.set(false)
            try {
                val file =getLogFile(dir)
                out = FileOutputStream(file)
                logcatProc = Runtime.getRuntime().exec(cmds)
                val ins = InputStreamReader(logcatProc!!.inputStream)
                mReader = BufferedReader(ins, 1024)
                var line = ""
                while (mQuitFlag.get() && mReader!!.readLine().apply {
                            line = this
                        } != null) {
                    if (!mQuitFlag.get()) {
                        break
                    }
                    if (line.isEmpty()) {
                        continue
                    }
                    if (out != null && line.contains(mPID)) {
                        val lineContent = ("$dateEN  $line\n").toByteArray()
                        out?.write(lineContent)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {

                if (logcatProc != null) {
                    logcatProc?.destroy()
                    logcatProc = null
                }
                if (mReader != null) {
                    try {
                        mReader?.close()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    mReader = null
                }
                if (out != null) {
                    try {
                        out?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    out = null
                }
            }
            mIsQuit.set(true)
            callback()
        }
    }
}