package com.example.mylib_test.activity.touch

import com.example.mylib_test.R

import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import androidx.core.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class GestureDetectorActivity : BaseFeatureActivity() {
    private lateinit var mGestureDetector: GestureDetectorCompat

    companion object {
        protected val DEBUG_TAG = "GestureDetectorActivity"
    }

    override fun setContentView() {
        setContentView(R.layout.a_gesturedetector)
    }

    override fun initData() {
        mGestureDetector = GestureDetectorCompat(this,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        // 单击事件
                        Log.d(DEBUG_TAG, "onSingleTapUp: $e")
                        showMessage("单击事件")
                        return super.onSingleTapUp(e)
                    }

                    override fun onFling(event1: MotionEvent,
                                         event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                        // 快速滑动
                        Log.d(DEBUG_TAG, "onFling: " + event1.toString()
                                + event2.toString())
                        showMessage("快速滑动")
                        return true
                    }

                    override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                                          distanceX: Float, distanceY: Float): Boolean {
                        // 滑动
                        showMessage("滑动")
                        return super.onScroll(e1, e2, distanceX, distanceY)
                    }

                    override fun onLongPress(e: MotionEvent) {
                        // 长嗯
                        showMessage("onLongPress")
                        Log.d(DEBUG_TAG, "onLongPress: ")
                        super.onLongPress(e)
                    }

                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        // 双击
                        showMessage("onDoubleTap")
                        Log.d(DEBUG_TAG, "onDoubleTap: ")
                        return super.onDoubleTap(e)
                    }

                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        //这才是click 点击
                        Log.d(DEBUG_TAG, "onSingleTapConfirmed: ")
                        return super.onSingleTapConfirmed(e)
                    }

                    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                        Log.d(DEBUG_TAG, "onDoubleTapEvent: ")
                        return super.onDoubleTapEvent(e)
                    }

                    override fun onShowPress(e: MotionEvent) {
                        Log.d(DEBUG_TAG, "onShowPress: ")
                        super.onShowPress(e)
                    }

                    override fun onDown(e: MotionEvent): Boolean {
                        Log.d(DEBUG_TAG, "onDown: ")
                        return super.onDown(e)
                    }
                })
    }

    override fun setListener() {
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.mGestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun showMessage(str: String) {
        ToastUtils.showLong(this, str)
    }

}
