package com.example.mylib_test.activity.three_place.utils

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 *[2018/7/10] by Zone
 */
class SuperscriptDrawable(var drawable: Drawable) : Drawable() {
    val mPaint: Paint
    val superscriptRadio = 12 * 1f / 40

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG);
    }


    override fun draw(canvas: Canvas?) {
        val showWidth = (getBounds().width() * superscriptRadio).toInt()
        val showHeight = (getBounds().height() * superscriptRadio).toInt();
        canvas!!.save()
        drawable.setBounds(0, 0, showWidth, showHeight)
        canvas.translate((bounds.width() - showWidth).toFloat(), (bounds.height() - showHeight).toFloat())
        // bgcolor
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(new Rect(0, 0, showWidth, showHeight),mPaint);
        drawable.draw(canvas)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
        invalidateSelf()
    }

}