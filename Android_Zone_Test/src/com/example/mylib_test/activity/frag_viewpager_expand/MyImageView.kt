package com.example.mylib_test.activity.frag_viewpager_expand

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.ImageView

class MyImageView : ImageView {
    private var startpoint = 0f
    private var kuand = 0f

    private var paint: Paint? = null

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context) : super(context) {
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        paint = Paint()
        paint!!.color = Color.BLUE
        paint!!.style = Style.FILL
        //canvas.drawRect(0, 0, 10, 10, paint);
        canvas.drawRect(startpoint, 0f, startpoint + kuand / 4, 20f, paint!!)

    }

    fun onScrolled(a: Float, b: Float) {
        startpoint = a
        kuand = b
        invalidate()
    }
}
