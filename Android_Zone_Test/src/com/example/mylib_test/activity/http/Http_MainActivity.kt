package com.example.mylib_test.activity.http

import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.View.OnClickListener
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.activity.http.aidl.AIDLActivity
import com.example.mylib_test.activity.http.zeventbus.ZEventBus
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_http_test.*


class Http_MainActivity : BaseFeatureActivity(), OnClickListener {
    companion object {
        private val zb: ZEventBus? = null
    }

    override fun setContentView() {
        setContentView(R.layout.a_http_test)
    }

    override fun initData() {
    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.handle -> handler()
            R.id.bt_aidl -> startActivity(Intent(this, AIDLActivity::class.java))
            R.id.eventBus -> startActivity(Intent(this, EventBusActivity::class.java))
            R.id.zeventBus -> startActivity(Intent(this, ZBusActivity::class.java))
            R.id.handlerThread -> handlerThread()
            else -> {
            }
        }
    }

    private fun handler() {
//        val broadcastIntent = Intent("com.zone.keeplive")
//        broadcastIntent.component = ComponentName("com.zone.sample", "com.zone.recevier.PullLiveReceiver")
//        broadcastIntent.setClassName("com.zone.sample", "com.zone.recevier.PullLiveReceiver")
//        broadcastIntent.setPackage("com.zone.sample")
//        application.sendBroadcast(broadcastIntent)

        mainHandler.post {
            handlerTv.text = "更改成功了!"
        }
    }


    lateinit var t2Handler: Handler
    val handlerThread = object : HandlerThread("abc") {}.apply {
        start()
        t2Handler = Handler(looper) {
            //子线程中处理问题
            threadLog("HandlerThread 的handler处理问题：")

            //子线程 与主线程通信
            mainHandler.post {
                threadLog("HandlerThread子线程中 使用 主线程的handler处理问题")
            }
            true
        }
    }

    open class ThreadCustom : Thread() {
        var tHandler: Handler? = null //子线程的handler
        var myLooper: Looper? = null

        fun quit() {
            myLooper?.quit()
        }
    }

    val thread2 = object : ThreadCustom() {
        override fun run() {
            Looper.prepare()
            myLooper = Looper.myLooper()
            tHandler = object : Handler(myLooper) {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                    //子线程中处理问题
                    threadLog("ThreadCustom 的handler处理问题：")

                    //子线程 与主线程通信
                    mainHandler.post {
                        threadLog("ThreadCustom子线程中 使用主线程的handler处理问题")
                    }
                    true
                }
            }
            Looper.loop()//这里下面的代码执行不了了已经
        }
    }.apply {
        start()
    }

    //这段　只是学习下handler～　
    private fun handlerThread() {
        //主线程与子线程通信
        thread2.tHandler?.sendEmptyMessage(1)

        //主线程与子线程通信
        t2Handler.post {
            threadLog("主线程使用HandlerThread的handler")
        }
        t2Handler.sendEmptyMessage(1)

    }

    fun threadLog(str: String) {
        LogApp.d("${str} : ${if (Looper.getMainLooper() == Looper.myLooper()) "在" else "不在"} 主线程中执行任务")
    }

    override fun onDestroy() {
        super.onDestroy()
        thread2.quit()
        handlerThread.quit()
    }

}
