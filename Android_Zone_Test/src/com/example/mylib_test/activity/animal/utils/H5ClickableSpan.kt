package com.example.mylib_test.activity.animal.utils

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Parcel
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView

//把所有clickable条换成自己想要跳转到 clickable
class H5ClickableSpan : URLSpan {
    var content = ""

    constructor(url: String?) : super(url)
    constructor(src: Parcel) : super(src)

    override fun onClick(widget: View) {
//        super.onClick(widget)
        widget.context.startActivity(Intent().apply {
            action = "android.intent.action.VIEW"
            data = Uri.parse(url)
        })
    }

    //更换颜色 和是否添加下划线  等样式
    override fun updateDrawState(ds: TextPaint) {
//        super.updateDrawState(ds)
        ds.color = Color.RED
        ds.setFakeBoldText(true)
        ds.isUnderlineText = true
    }
    companion object {
        @JvmStatic
        fun replaceClickSpan(tv: TextView) {
            val spannableStringBuilder = SpannableStringBuilder(tv.text)
            val clickableSpans = spannableStringBuilder.getSpans(0, tv.text.length, ClickableSpan::class.java)

            //replace span
            clickableSpans.forEach {
                if (it is URLSpan) {
                    val spanStart = spannableStringBuilder.getSpanStart(it)
                    val spanEnd = spannableStringBuilder.getSpanEnd(it)
                    val spanFlags = spannableStringBuilder.getSpanFlags(it)
                    val span = H5ClickableSpan(it.url)
                    try {
                        span.content = spannableStringBuilder.subSequence(spanStart, spanEnd).toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    spannableStringBuilder.removeSpan(it)
                    spannableStringBuilder.setSpan(span, spanStart, spanEnd, spanFlags)
                }
            }
            tv.text = spannableStringBuilder
        }
    }
}