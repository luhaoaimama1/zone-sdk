package com.example.mylib_test.activity.controller

import android.view.View
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.ActionBarActivityController
import com.zone.lib.base.controller.activity.controller.ShowState

class ActionBarControllerActivity : BaseFeatureActivity() {

    override fun initBaseControllers() {
        super.initBaseControllers()
        val values = intent.getStringArrayListExtra("key")
        val showStateList = ArrayList<ShowState>()
        values.forEach {
            showStateList.add(ShowState.valueOf(it))
        }
        val list = showStateList.toTypedArray()
        getController(ActionBarActivityController::class.java)?.initFirst(*list)
    }

    override fun setContentView() {
        setContentView(R.layout.a_actionbar_controller)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.bt_hideTitle -> hideTitle()
            R.id.bt_showTitle -> showTitle()
            else -> {
            }
        }
    }
}