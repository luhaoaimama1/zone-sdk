package com.example.mylib_test.activity.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.example.mylib_test.LogApp
import kotlin.math.abs


class OnInterceptParentVG : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        LogApp.d(" Inc_VG :dispatchTouchEvent=${dispatchTouchEvent}");
        return dispatchTouchEvent
    }

    var firstY = Float.MIN_VALUE
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (firstY == Float.MIN_VALUE) firstY = ev.y
        else {
            if (abs(ev.y - firstY) > 700) {
                LogApp.d(" Inc_VG :拦截=${true}???????????");
                return true
            }
        }
        val onInterceptTouchEvent = super.onInterceptTouchEvent(ev)
        LogApp.d(" Inc_VG :onInterceptTouchEvent=${onInterceptTouchEvent}");
        return onInterceptTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val onTouchEvent = super.onTouchEvent(event)
        LogApp.d(" Inc_VG :onTouchEvent=${true}");
        return true
    }
}