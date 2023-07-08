package com.example.mylib_test.activity.animal

import view.FlexibleBall
import view.QQBizierView

import com.zone.lib.base.controller.activity.BaseFeatureActivity


/**
 * Created by fuzhipeng on 16/7/29.
 */
class InvaliteStudyActivity : BaseFeatureActivity() {
    override fun setContentView() {
        val type = intent.getStringExtra("type")
        if ("QQBizierView" == type)
            setContentView(QQBizierView(this))
        if ("FlexibleBall" == type)
            setContentView(FlexibleBall(this))
    }

    override fun initData() {}

    override fun setListener() {

    }
}
