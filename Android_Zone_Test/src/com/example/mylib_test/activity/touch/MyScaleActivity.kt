package com.example.mylib_test.activity.touch

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Rect
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceHolder
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import kotlinx.android.synthetic.main.a_scalegesture.*

class MyScaleActivity : BaseFeatureActivity() {
    lateinit var mSurfaceHolder: SurfaceHolder
    lateinit var mScaleGestureDetector: ScaleGestureDetector
    lateinit var mBitmap: Bitmap
    private var mMatrix: Matrix? = null


    override fun initDefaultConifg() {
        super.initDefaultConifg()
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_scalegesture)
        mSurfaceHolder = surfaceview.holder
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleGestureListener())
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.aaaaaaaaaaaab)
        //按钮监听
        button.setOnClickListener {
            //锁定整个SurfaceView
            val mCanvas = mSurfaceHolder.lockCanvas()
            //画图
            mCanvas.drawBitmap(mBitmap, 0f, 0f, null)
            //绘制完成，提交修改
            mSurfaceHolder.unlockCanvasAndPost(mCanvas)
            //重新锁一次
            mSurfaceHolder.lockCanvas(Rect(0, 0, 0, 0))
            mSurfaceHolder.unlockCanvasAndPost(mCanvas)
        }
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //返回给ScaleGestureDetector来处理
        return mScaleGestureDetector.onTouchEvent(event)
    }


    inner class ScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {

        private var mScaleFactor: Float = 0.toFloat()

        override fun onScale(detector: ScaleGestureDetector): Boolean {

            //缩放比例
            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 3.0f))
            println("mScaleFactor$mScaleFactor")
            mMatrix = Matrix()
            mMatrix!!.postScale(mScaleFactor, mScaleFactor, detector.focusX, detector.focusY)

            //锁定整个SurfaceView
            val mCanvas = mSurfaceHolder.lockCanvas()
            //清屏
            mCanvas.drawColor(Color.BLACK)
            //画缩放后的图
            mCanvas.drawBitmap(mBitmap, mMatrix!!, null)
            //绘制完成，提交修改
            mSurfaceHolder.unlockCanvasAndPost(mCanvas)
            //重新锁一次
            mSurfaceHolder.lockCanvas(Rect(0, 0, 0, 0))
            mSurfaceHolder.unlockCanvasAndPost(mCanvas)

            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            //一定要返回true才会进入onScale()这个函数
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {}

    }
}