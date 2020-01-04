package com.example.mylib_test.activity.system

import android.widget.TextView
import com.example.mylib_test.Page

import com.zone.lib.base.controller.activity.BaseFeatureActivity

class PageActivity : BaseFeatureActivity() {

    override fun setContentView() {
        val page = intent.getSerializableExtra("page") as Page
        val textView = TextView(this)
        textView.text = page.name
        setContentView(textView)
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}

