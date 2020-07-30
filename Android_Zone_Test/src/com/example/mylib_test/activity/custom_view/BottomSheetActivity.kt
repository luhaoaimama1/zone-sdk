package com.example.mylib_test.activity.custom_view

import android.view.View
import com.example.mylib_test.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_custom_bottom_sheet.*


/**
 * [2017] by Zone
 */

class BottomSheetActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_custom_bottom_sheet)
    }

    override fun initData() {
    }

    override fun setListener() {
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //这里是bottomSheet状态的改变
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        })
    }
}
