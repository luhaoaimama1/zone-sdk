package com.example.mylib_test.activity.animal

import android.graphics.drawable.Animatable

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_svg.*

/**
 * 绘制SVG
 * Created by LGL on 2016/4/16.
 */
class SVGActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_svg)
    }

    override fun initData() {
    }

    override fun setListener() {
        iv.setOnClickListener { anim() }
    }

    private fun anim() {
        val drawable = iv.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
    }

}