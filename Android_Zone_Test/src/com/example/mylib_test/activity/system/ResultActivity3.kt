package com.example.mylib_test.activity.system

import com.example.mylib_test.R

import android.view.View
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class ResultActivity3 : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.activity_result3)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_returnOne -> {
                setResult(SystemMainActivity.ResponseCode)
                finish()
            }
            else -> {
            }
        }
    }

}
