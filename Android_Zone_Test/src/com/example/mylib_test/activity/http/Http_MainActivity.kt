package com.example.mylib_test.activity.http

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
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
        handler.post {
            handle.text = "更改成功了!"
        }
    }

    //这段　只是学习下handler～　
    private fun handlerThread() {
        object : Thread() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun run() {
                Looper.prepare()
                //Handler.Callback 还必须这么写　
                val abc = Handler(Handler.Callback { false })
                Looper.loop()
                Looper.myLooper()!!.quitSafely()
            }
        }.start()
    }

}
