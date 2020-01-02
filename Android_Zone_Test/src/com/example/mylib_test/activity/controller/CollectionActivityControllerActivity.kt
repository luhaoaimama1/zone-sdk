package com.example.mylib_test.activity.controller

import android.view.View
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class CollectionActivityControllerActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_collection_controller)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.bt_finishAll -> finishAll()
            else -> { }
        }
    }
}