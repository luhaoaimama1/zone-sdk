package com.example.mylib_test.activity.touch

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.view.ViewDragStudyFrame
import com.example.mylib_test.adapter.delegates.TextDelegates
import com.zone.adapter3kt.QuickAdapter
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import kotlinx.android.synthetic.main.a_nestscrollb.*

/**
 * Created by Zone on 2016/1/29.
 */
class SheetViewRecycelrViewActivity : BaseFeatureActivity() {
    override fun initBaseControllers() {
        super.initBaseControllers()
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_custom_top_sheet)
    }

    override fun initData() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = QuickAdapter<String>(this).apply {
            registerDelegate(TextDelegates())
            add(NestedScrollBActivity.data)
        }
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
