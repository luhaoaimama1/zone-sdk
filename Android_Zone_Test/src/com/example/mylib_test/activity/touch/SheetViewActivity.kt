package com.example.mylib_test.activity.touch

import android.view.View

import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.view.ViewDragStudyFrame
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import com.zone.lib.utils.activity_fragment_ui.ToastUtils

/**
 * Created by Zone on 2016/1/29.
 */
class SheetViewActivity : BaseFeatureActivity() {
    override fun initBaseControllers() {
        super.initBaseControllers()
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_sheetview)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
//            R.id.tv_back -> {}
//            R.id.tv_mMenuView ->{}
            else -> {
            }
        }
    }
}
