package com.example.mylib_test.activity.touch

import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.view.FrameLogLayout
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_eventpasslog.*

/**
 * Created by fuzhipeng on 2016/11/22.
 */

class EventPassLogActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_eventpasslog)
    }

    override fun initData() {
    }

    override fun setListener() {
        view.setOnClickListener(this)
        red.setActiviy(this)
        green.setActiviy(this)
        view.setActiviy(this)
    }

    fun log(view: View?, str: String) {
        var pre = ""
        if (view != null) {
            if (view is FrameLogLayout) {
                pre = "FrameLogLayout"
                if (view === red)
                    pre += "1"
                if (view === green)
                    pre += "2"

            } else
                pre = "TextLogView"
        } else {
            pre = "Activity"
        }

        Log.e("EventPassLogActivity", "$pre   $str")
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        log(null, "dispatchTouchEvent")
        val result = super.dispatchTouchEvent(ev)
        log(null, "dispatchTouchEvent 结果->:$result")
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        log(null, "onTouchEvent")
        val result = super.onTouchEvent(event)
        log(null, "onTouchEvent 结果->:$result")
        return result
    }

}
