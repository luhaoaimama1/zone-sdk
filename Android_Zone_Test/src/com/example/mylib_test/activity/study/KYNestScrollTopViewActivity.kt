package com.example.mylib_test.activity.study

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.NestedScrollBActivity
import com.example.mylib_test.adapter.delegates.TextDelegates
import com.zone.adapter3kt.QuickAdapter
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_ky_nest_scroll_top.*
import kotlinx.android.synthetic.main.a_nestscrollb.rv

class KYNestScrollTopViewActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_ky_nest_scroll_top)
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
            R.id.tvClick -> {
                nsv.fullScroll(View.FOCUS_UP)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
