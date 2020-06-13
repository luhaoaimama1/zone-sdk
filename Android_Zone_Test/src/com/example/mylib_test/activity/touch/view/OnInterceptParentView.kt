package com.example.mylib_test.activity.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.mylib_test.LogApp


class OnInterceptParentView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    var allowIntercept=false
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        LogApp.d(" Inc_View :dispatchTouchEvent=${dispatchTouchEvent}")
        if(ev.action==MotionEvent.ACTION_DOWN){
            parent.requestDisallowInterceptTouchEvent(true)
            allowIntercept=true
            LogApp.d(" Inc_View :requestDisallowInterceptTouchEvent=${true}")
        }
        return dispatchTouchEvent
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val onTouchEvent = super.onTouchEvent(event)
        if(allowIntercept){
            allowIntercept=false
            postDelayed({
                parent.requestDisallowInterceptTouchEvent(false)
                LogApp.d(" Inc_View :requestDisallowInterceptTouchEvent=${false}!!!")
            },2000)
        }
        LogApp.d(" Inc_View :onTouchEvent=${true}");
        return true
    }
}