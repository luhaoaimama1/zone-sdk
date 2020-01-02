package com.example.mylib_test.activity.animal

import android.view.View

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.nineoldandroids.view.ViewHelper

import kotlinx.android.synthetic.main.a_viewhelper.*

/**
 * Created by Administrator on 2016/1/28.
 */
class ViewHelperTestActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_viewhelper)
    }

    override fun initData() {

    }

    override fun setListener() {
        ll_main.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_main -> startAni()
            R.id.bt2 -> startAni2()
            R.id.bt3 -> startAni3()
            else -> {
            }
        }
        super.onClick(v)
    }

    private fun startAni() {
        ViewHelper.setY(bt1, 100f)
    }

    private fun startAni2() {
        //        ViewHelper.setY(bt1,100);
        ViewHelper.setTranslationY(bt1, 100f)
    }

    private fun startAni3() {
        ViewHelper.setScrollX(ll_main, 100)
        ViewHelper.setScrollX(bt3, -100)
    }
}
