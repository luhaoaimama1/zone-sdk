package com.example.mylib_test.activity.touch

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.view.utils.SheetBehavior
import com.example.mylib_test.activity.touch.view.utils.ShowModeTop
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import kotlinx.android.synthetic.main.a_custom_top_sheet.*

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

    lateinit var showModeTop: ShowModeTop
    override fun initData() {
        mainView.setOnClickListener {
            showModeTop.open()
        }
//        rv.layoutManager = LinearLayoutManager(this)
//        rv.adapter = QuickAdapter<String>(this).apply {
//            registerDelegate(TextDelegates())
//            add(NestedScrollBActivity.data)
//        }
        val from = SheetBehavior.from(nsv)
        showModeTop = ShowModeTop(from, object : ShowModeTop.Callback {
            override fun onOpen() {
            }

            override fun onClose() {
            }

            override fun onLayout() {
                showModeTop.open()
            }

            override fun onDrag() {
            }

            override fun onDragReleased() {
            }

            override fun onAutoScroll() {
            }

            override fun onAutoScrollStop() {
            }
        })
        showModeTop.peekLength = 0
        from.showStyle = showModeTop

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
