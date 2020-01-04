package com.example.mylib_test.activity.bbbb

import android.content.Intent
import android.view.View
import com.example.mylib_test.Page
import com.example.mylib_test.R
import com.example.mylib_test.activity.system.PageActivity
import com.example.mylib_test.activity.touch.NestedScrollBActivity
import com.zone.lib.base.controller.activity.BaseFeatureActivity


/**
 *[2018/11/14] by Zone
 */
class BBBBActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_bbbb_main)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_page -> {
                val intent = Intent(this, PageActivity::class.java)
                intent.putExtra("page", Page.HOME)
                startActivity(intent)
            }
            R.id.nestScrollB -> startActivity(Intent(this, NestedScrollBActivity::class.java))
            else -> {
            }
        }
        super.onClick(v)

    }
}