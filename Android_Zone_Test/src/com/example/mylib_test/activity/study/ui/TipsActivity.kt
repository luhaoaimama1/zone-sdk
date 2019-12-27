package com.example.mylib_test.activity.study.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zone.lib.base.activity.BaseActivity
import com.example.mylib_test.R
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.data.convert.DensityUtils
import kotlinx.android.synthetic.main.a_tips.*
import view.TipConstraintLayout


/**
 *[2018/11/14] by Zone
 */
class TipsActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_tips)
        val dp2px = DensityUtils.dp2px(this, 6F).toFloat()

        tipsLayout.setGlobalLayout(findViewById(R.id.rl_main))
        tipsLayout.addTipItem(R.id.tv1, R.id.tv2, R.id.tv3, round = dp2px)

        val topView = LayoutInflater.from(this).inflate(R.layout.item_tv, tipsLayout, false)
        (topView as TextView).text="topView"
        val indicateItemTop = TipConstraintLayout.IndicateItem(topView, TipConstraintLayout.IndicateViewGravity.TopCenter)


        val bottomView = LayoutInflater.from(this).inflate(R.layout.item_tv, tipsLayout, false)
        (bottomView as TextView).text="bottomView"
        val indicateItemBottom = TipConstraintLayout.IndicateItem(bottomView, TipConstraintLayout.IndicateViewGravity.BottomCenter)


        val leftView = LayoutInflater.from(this).inflate(R.layout.item_tv, tipsLayout, false)
        (leftView as TextView).text="leftView"
        val indicateItemLeft = TipConstraintLayout.IndicateItem(leftView, TipConstraintLayout.IndicateViewGravity.LeftCenter)

        val rightView = LayoutInflater.from(this).inflate(R.layout.item_tv, tipsLayout, false)
        (rightView as TextView).text="rightView"
        val indicateItemRight = TipConstraintLayout.IndicateItem(rightView, TipConstraintLayout.IndicateViewGravity.RightCenter)

        tipsLayout.addTipItem(R.id.center, dp2px, indicateItemTop,indicateItemBottom,indicateItemLeft,indicateItemRight)

        tv2.setOnClickListener {
            ToastUtils.showShort(this, "haha")
        }
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }


}