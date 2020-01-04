package com.example.mylib_test.activity.custom_view

import android.util.Log
import com.example.mylib_test.R

import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_hero_scroller.*

/**
 * [2017] by Zone
 */

class ScrollerViewActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_hero_scroller)
    }

    override fun initData() {
        sv.postDelayed({
            sv.scrollTo(1000, 0)
            Log.e("ScrollerViewActivity", sv.scrollX.toString() + "")
        }, 2000)
    }

    override fun setListener() {
    }
}
