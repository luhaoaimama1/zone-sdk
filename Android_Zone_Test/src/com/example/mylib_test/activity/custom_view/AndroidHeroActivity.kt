package com.example.mylib_test.activity.custom_view


import com.example.mylib_test.R

import com.zone.lib.base.controller.activity.BaseFeatureActivity

class AndroidHeroActivity : BaseFeatureActivity() {
    override fun setContentView() {
        val temp = intent.getStringExtra("type")
        if ("circle" == temp) {
            setContentView(R.layout.a_hero_circle)
        } else if ("circle2" == temp) {
            setContentView(R.layout.a_hero_voice)
        } else {
            setContentView(R.layout.a_hero_scroll)
        }
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}
