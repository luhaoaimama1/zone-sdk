package com.example.mylib_test.activity.touch

import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.view.FrameLogLayout
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.activity_fragment_ui.ActivityTopViewUtils
import com.zone.lib.utils.reflect.Reflect
import kotlinx.android.synthetic.main.a_eventpasslog.*
import kotlinx.android.synthetic.main.a_eventpasslog.red
import kotlinx.android.synthetic.main.a_onintercept.*

/**
 * Created by fuzhipeng on 2016/11/22.
 */

class OnInterceptActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_onintercept)
    }

    override fun initData() {
        Thread {
           var activity= this@OnInterceptActivity
            var a = 0
            while (true) {
                a++

            }
        }.start()
    }

    override fun setListener() {
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        LogApp.d("Inc_Acvitity dispatchTouchEvent")
        val result = super.dispatchTouchEvent(ev)
        LogApp.d("Inc_Acvitity dispatchTouchEvent 结果->:$result")
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        LogApp.d("Inc_Acvitity onTouchEvent")
        val result = super.onTouchEvent(event)
        LogApp.d("Inc_Acvitity  onTouchEvent 结果->:$result")
        return result
    }

}
