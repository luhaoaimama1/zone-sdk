package com.example.mylib_test.activity.frag_viewpager_expand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.mylib_test.R
import com.zone.lib.base.LazyFragment

class Tab2 : LazyFragment() {

    lateinit var tab2: TextView
    override fun setContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        val contentView = setContentView(R.layout.tab2)
        tab2= contentView.findViewById(R.id.tab2)

    }

    override fun findIDs() {

    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onFragmentShown() {
        tab2.text = "Perfect!"
    }

    override fun onFragmentDismiss() {

    }

}
