package com.example.mylib_test.activity.touch

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mylib_test.MainMenu
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.example.mylib_test.delegates.MenuEntityDeletates
import com.example.mylib_test.delegates.TextDelegates
import com.zone.adapter3.QuickRcvAdapter
import com.zone.lib.base.activity.BaseActivity
import java.util.*

/**
 * Copyright (c) 2018 BiliBili Inc.
 * [2018/8/24] by Zone
 */
class NestedScrollBActivity : BaseActivity() {
    companion object {
        val data = LinkedList<String>()

        init {
            for (i in 0..29)
                data.add("一直很桑心")
        }
    }

    var rv: RecyclerView? = null
    override fun setContentView() {
        setContentView(R.layout.a_nestscrollb)
    }

    override fun findIDs() {
        rv = findViewById(R.id.rv) as RecyclerView?
    }

    override fun initData() {
        rv!!.layoutManager = LinearLayoutManager(this)
        val adapter2 = QuickRcvAdapter<String>(this, data)
            .addViewHolder(TextDelegates())
            .relatedList(rv)
    }

    override fun setListener() {

    }
}