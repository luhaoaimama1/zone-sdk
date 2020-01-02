package com.example.mylib_test.activity.touch


import com.example.mylib_test.R

import java.util.ArrayList
import com.example.mylib_test.delegates.TextType2Delegates
import com.zone.adapter3.QuickRcvAdapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.activity_nested_parent_hong.*


class NestedScrollingActivity_hongParent : BaseFeatureActivity() {
    private val mDatas = ArrayList<String>()

    override fun setContentView() {
        setContentView(R.layout.activity_nested_parent_hong)
    }

    override fun initData() {
        for (i in 0..49) {
            mDatas.add("Parent Demo -> $i")
        }
        recyclerView.layoutManager = LinearLayoutManager(this)

        QuickRcvAdapter(this, mDatas)
                .addViewHolder(TextType2Delegates())
                .relatedList(recyclerView)
    }

    override fun setListener() {
    }

}
