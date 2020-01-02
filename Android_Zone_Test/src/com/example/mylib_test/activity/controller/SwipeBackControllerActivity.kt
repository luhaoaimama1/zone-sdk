package com.example.mylib_test.activity.controller

import android.view.View
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController

class SwipeBackControllerActivity : BaseFeatureActivity() {

    override fun initBaseControllers() {
        super.initBaseControllers()
        getController(SwipeBackActivityController::class.java)?.defaultSwipeBack = SwipeBackActivityController.SwipeBack.RIGHT
    }

    override fun setContentView() {
        setContentView(R.layout.a_swipeback_controller)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.bt_swipeback -> scrollToFinishActivity()
            else -> {
            }
        }
    }
}