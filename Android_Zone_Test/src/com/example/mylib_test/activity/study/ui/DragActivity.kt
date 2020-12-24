package com.example.mylib_test.activity.study.ui

import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_drag.*

class DragActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_drag)
    }

    override fun initData() {
    }

    override fun setListener() {
        tv.setOnClickListener {
            if(!svg2.clickIntercept){
                LogApp.d("click 滑动tv")
            }else{
                LogApp.d("click 滑动tv 被拦截")
            }
        }

        tv2.setOnClickListener {
            LogApp.d("click 非滑动 tv2")
        }
    }
}