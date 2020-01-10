package com.zone.lib.utils.data.info

import android.os.SystemClock
import com.zone.lib.Configure
import com.zone.lib.LogZSDK
import com.zone.lib.utils.data.file2io2data.IOUtils
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

object PrintLog {

    val E_STACK = 3
    val QUIT = "quit"
    val CLEAR = "clear"

    val logFile = Configure.getApplicationContext().filesDir.absolutePath + File.separator + "Logcat.txt"
    val logFileTemp = Configure.getApplicationContext().filesDir.absolutePath + File.separator + "Logcat-Temp.txt"

    interface StopListener {
        fun onStop()
    }

    interface ReStartListener {
        fun onReStart()
    }

    val logDumper = LogDumper()

    @JvmStatic
    fun stopLogs(listener: StopListener) {
        logDumper.stopLogs(listener)
    }

    @JvmStatic
    fun stopLogs() {
        logDumper.stopLogs()
    }

    @JvmStatic
    fun restart(listener: ReStartListener) {
        logDumper.restart(listener)
    }

    @JvmStatic
    fun restart() {
        logDumper.restart()
    }

    @JvmStatic
    fun e(msg: String) {
        getStackLogger().e(msg)
        logDumper.pw.println(getWarpContent(msg))
        logDumper.pw.flush()
    }

    @JvmStatic
    fun e(e: Exception) {
        getStackLogger().e("",e)
        eInner("", e)
    }

    @JvmStatic
    fun e(msg:String,e: Exception) {
        getStackLogger().e(msg,e)
        eInner(msg, e)
    }

    private fun eInner(msg: String, e: Exception) {
        logDumper.pw.print("${getDataStr()}   ${msg}")
        e.printStackTrace(logDumper.pw)
        logDumper.pw.flush()
    }

    @JvmStatic
    fun e(e: Throwable) {
        getStackLogger().e("",e)
        logDumper.pw.print("${getDataStr()}   ")
        e.printStackTrace(logDumper.pw)
        logDumper.pw.flush()
    }

    @JvmStatic
    fun crash(e: Throwable) {
        logDumper.pw.print("${getDataStr()}   Caused by:  ")
        e.printStackTrace(logDumper.pw)
        logDumper.pw.flush()
    }

    @JvmStatic
    internal fun heart(){
        logDumper.pw.println("")
        logDumper.pw.flush()
    }


    private fun getStackLogger() = LogZSDK.stack(E_STACK)

    //https://blog.csdn.net/qq_34603736/article/details/73497360
    //这个位置只有写成固定格式 "("+fileName+":"+lineNumber+")"，才能实现定位功能
    private fun getWarpContent(content: String): String {
        val caller = Throwable().stackTrace[2]
        return "${getDataStr()}      [ (${caller.fileName}:${caller.lineNumber})#${caller.methodName} ] $content"
    }

    private fun getDataStr(): String? {
        val date = Date() //获得当前日期
        val sdf = SimpleDateFormat("yyyy/MM/dd  HH:mm:ss")
        val dataStr = sdf.format(date)
        return dataStr
    }

    @JvmStatic
    fun clear() {
        logDumper.pw.println(CLEAR)
        logDumper.pw.flush()
    }

    fun clear(file: File) {
        val fw = PrintWriter(file)
        fw.print("")
        fw.flush()
        fw.close()
    }

    /**
     * stop 写入完毕监听
     * start
     */
    class LogDumper() : Runnable {


        private var isQuit = AtomicBoolean(true)
        private var isRestart = AtomicBoolean(false)
        lateinit var pis: PipedInputStream
        lateinit var pos: PipedOutputStream
        lateinit var pw: PrintWriter

        init {
            mustInit()
        }

        private fun mustInit() {
            pis = PipedInputStream()
            pos = PipedOutputStream().apply {
                connect(pis)
            }
            pw = PrintWriter(pos)
        }

        private var mReader: BufferedReader? = null
        private var out: FileOutputStream? = null
        private var listener: StopListener? = null
        private var restartListener: ReStartListener? = null
        private var callback: () -> Unit = {
            if (isRestart.get()) {
                LogZSDK.d("PrintLog isRestart！")
                realStart()
            } else {
                LogZSDK.d("PrintLog  stop 回调是 退出执行的！")
                listener?.onStop()
                listener = null
            }
        }

        fun stopLogs(listener: StopListener) {
            this.listener = listener
            stopLogs()
        }

        fun stopLogs() {
            if (!isQuit.get()) {
                LogZSDK.d("stopLogs!")
                pw.println(QUIT)
                pw.flush()
            } else {
                callback()
            }
        }

        fun restart(listener: ReStartListener) {
            this.restartListener = listener
            startInner()
        }

        fun restart() {
            startInner()
        }

        private fun startInner() {
            LogZSDK.d("PrintLog startInner!")
            isRestart.set(true)
            stopLogs()
        }

        fun isRuning() = !isQuit.get()

        private fun realStart() {
            if (isQuit.get()) {
                LogZSDK.d("PrintLog  realStart !")
                isQuit.set(false)
                //心跳保证 pip not broken 如果时间不写入就会 dead
                Thread(KeepLiveThread()).start()
                Thread(this).start()
            } else LogZSDK.d("PrintLog  logcat is colllectioning!")
        }

        override fun run() {
            if (isRestart.get()) {
                restartListener?.onReStart()
                listener = null
            }
            isRestart.set(false)
            try {

                out = FileOutputStream(logFile, true)
                val ins = InputStreamReader(pis)
                mReader = BufferedReader(ins, 1024)
                var line = ""
                while (mReader!!.readLine()?.apply {
                            line = this
                        } != null) {
                    if (line == QUIT) {
                        LogZSDK.d("PrintLog  quit!")
                        break
                    }
                    if (line == CLEAR) {
                        clear(File(logFile))
                        LogZSDK.d("PrintLog  clear!")
                        continue
                    }
                    if (line.isEmpty()) {
//                        PrintLopAppstackTrace.d("PrintLog  heart!")
                        continue
                    }
                    if (out != null) {
                        LogZSDK.d("PrintLog  write===>$line")
                        out?.write(("$line\n").toByteArray())
                        out?.flush();
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                IOUtils.closeQuietly(mReader)
                IOUtils.closeQuietly(out)
                IOUtils.closeQuietly(pis)
                IOUtils.closeQuietly(pos)
                IOUtils.closeQuietly(pw)
                mustInit()
            }
            isQuit.set(true)
            LogZSDK.d("PrintLog quit logs  final!")
            callback()
        }
    }
    //心跳保证 pip not broken 如果时间不写入就会 dead
    class KeepLiveThread:Runnable{
        override fun run() {
            while(true){
                try {
                    heart()
                    SystemClock.sleep(500)
                } catch (e: Exception) {
                }
            }
        }
    }
}
