package com.example.mylib_test.activity.animal.viewa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

import com.example.mylib_test.LogApp
import com.example.mylib_test.R

import com.zone.lib.utils.image.compress2sample.SampleUtils
import com.zone.lib.utils.view.DrawUtils

/**
 * Created by fuzhipeng on 2016/12/19.
 */

class LightingColorFilterView(context: Context) : View(context) {
    private val mBmp: Bitmap
    private val mPaint: Paint
    init {
        mPaint = DrawUtils.getBtPaint()
        mBmp = SampleUtils.load(context, R.drawable.blog2).bitmap()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        LogApp.d("View.isHardwareAccelerated:$isHardwareAccelerated")
        val width = 300
        //这里是为了等比缩放
        val height = width * mBmp.height / mBmp.width
        canvas.drawBitmap(mBmp, null, Rect(0, 0, width, height), mPaint)

        //为什么是ff而不能是01呢 因为A默认值是ff 所以 矩阵相乘的时候  会在mod255
        canvas.translate(0f, height.toFloat())
        mPaint.colorFilter = LightingColorFilter(0xffffff, 0x0000f0)
        canvas.drawBitmap(mBmp, null, Rect(0, 0, width, height), mPaint)

        canvas.translate(0f, height.toFloat())
        mPaint.colorFilter = LightingColorFilter(0x00ff00, 0x000000)
        canvas.drawBitmap(mBmp, null, Rect(0, 0, width, height), mPaint)
    }
}
