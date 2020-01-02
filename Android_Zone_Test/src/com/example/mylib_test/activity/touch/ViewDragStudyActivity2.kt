package com.example.mylib_test.activity.touch

import android.view.View
import com.example.mylib_test.R
import com.zone.lib.utils.activity_fragment_ui.ToastUtils

import androidx.customview.widget.ViewDragHelper
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import kotlinx.android.synthetic.main.a_viewdragstudy2.*

/**
 * Created by Zone on 2016/1/29.
 */
class ViewDragStudyActivity2 : BaseFeatureActivity() {

    override fun initBaseControllers() {
        super.initBaseControllers()
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_viewdragstudy2)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.tv_back -> viewDragStudyFrame.toggle()
            R.id.tv_moveEnable -> if (viewDragStudyFrame.isEnableMove) {
                viewDragStudyFrame.isEnableMove = false
                tv_moveEnable.text = "不!可以滑动"
            } else {
                viewDragStudyFrame.isEnableMove = true
                tv_moveEnable.text = "可以滑动"
            }
            R.id.tv_mMenuView -> ToastUtils.showLong(this, "没有tryCaptureView 可以点击")
            R.id.tv_leftOrRight -> if (viewDragStudyFrame.moveType == ViewDragHelper.EDGE_RIGHT) {
                viewDragStudyFrame!!.moveType = ViewDragHelper.EDGE_LEFT
                tv_leftOrRight.text = "左滑"
            } else {
                viewDragStudyFrame!!.moveType = ViewDragHelper.EDGE_RIGHT
                tv_leftOrRight.text = "右滑"
            }
            else -> {
            }
        }
    }
}
