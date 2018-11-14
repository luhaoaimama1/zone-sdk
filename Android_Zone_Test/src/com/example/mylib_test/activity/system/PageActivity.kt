package com.example.mylib_test.activity.system

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.example.mylib_test.Page

import com.example.mylib_test.R

class PageActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val page = intent.getSerializableExtra("page") as Page
        val textView = TextView(this)
        textView.text = page.name
        setContentView(textView)
    }
}

