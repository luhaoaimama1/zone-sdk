package com.example.mylib_test.activity.custom_view

import com.example.mylib_test.R

import com.zone.lib.base.controller.activity.BaseFeatureActivity

class SquareTestActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_customview_square)
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}
