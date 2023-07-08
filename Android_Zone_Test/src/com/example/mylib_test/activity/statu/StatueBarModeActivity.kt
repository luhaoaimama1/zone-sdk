package com.example.mylib_test.activity.statu

import android.view.View
import com.example.mylib_test.R
import com.jaeger.library.StatusBarUtil
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class StatueBarModeActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_status_mode)
//        setContentView(R.layout.a_status_mode_test)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.darkMode -> {
                StatusBarUtil.setDarkMode(this)
            }
            R.id.lightMode -> {
                StatusBarUtil.setLightMode(this)
            }
            R.id.darkModePop -> {
                DarkModePop_Bottom(this, R.id.darkModePop).show()
            }
            else -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
