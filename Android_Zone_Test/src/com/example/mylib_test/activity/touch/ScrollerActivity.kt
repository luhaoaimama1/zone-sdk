package com.example.mylib_test.activity.touch

import com.example.mylib_test.R
import com.nineoldandroids.view.ViewHelper

import android.view.View
import android.view.ViewGroup
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class ScrollerActivity : BaseFeatureActivity() {
    private var vp: ViewGroup? = null
    private var tv: View? = null

    override fun setContentView() {
        setContentView(R.layout.a_touch_scroller)
        vp = window.decorView.findViewById<View>(android.R.id.content) as ViewGroup
        vp!!.getChildAt(0)
        tv = vp!!.getChildAt(0).findViewById(R.id.tv)
        findViewById<View>(R.id.ll_main).setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.offleftandRight -> vp!!.getChildAt(0).findViewById<View>(R.id.tv).offsetLeftAndRight(100)
            R.id.offtopandBottom -> vp!!.getChildAt(0).findViewById<View>(R.id.tv).offsetTopAndBottom(100)
            R.id.setMarginToP -> {
                val params = vp!!.getChildAt(0).findViewById<View>(R.id.tv).layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(0, 100, 0, 0)
                tv!!.layoutParams = params
            }
            R.id.setViewHelperX -> ViewHelper.setY(tv!!, 200f)
            R.id.requestLayout -> {
                (tv!!.layoutParams as ViewGroup.MarginLayoutParams).height = 100
                tv!!.requestLayout()
                tv!!.scrollTo(100, 100)
                tv!!.scrollBy(100, 50)
            }
        }
        val p = Property(tv!!)
    }

    internal inner class Property(view: View) {
        var y: Float = 0.toFloat()
        var tY: Float = 0.toFloat()
        var top: Int = 0
        var topMargin: Int = 0

        init {
            y = ViewHelper.getY(view)
            tY = ViewHelper.getTranslationY(view)
            top = view.top
            topMargin = (view.layoutParams as ViewGroup.MarginLayoutParams).topMargin
        }
    }

}
