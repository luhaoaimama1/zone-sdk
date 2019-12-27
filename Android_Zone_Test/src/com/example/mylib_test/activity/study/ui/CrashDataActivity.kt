package com.example.mylib_test.activity.study.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zone.lib.base.activity.BaseActivity
import com.example.mylib_test.R
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.data.convert.DensityUtils
import kotlinx.android.synthetic.main.a_tips.*
import view.TipConstraintLayout


/**
 *[2018/11/14] by Zone
 */
class CrashDataActivity : BaseActivity() {

    private val textView = TextView(this)

    override fun setContentView() {
        setContentView(textView)
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }


}