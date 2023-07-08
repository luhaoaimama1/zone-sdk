package com.example.mylib_test.activity.study

import android.view.View
import android.content.Intent
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib_test.LogApp
import com.example.mylib_test.MainMenu
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.example.mylib_test.activity.study.ui.*
import com.example.mylib_test.adapter.delegates.MenuEntityDeletates
import com.example.mylib_test.adapter.delegates.TextDelegatesHori
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.ViewStyleDefault
import com.zone.adapter3kt.ViewStyleOBJ
import com.zone.adapter3kt.adapter.OnItemClickListener
import com.zone.adapter3kt.data.HFMode
import com.zone.adapter3kt.loadmore.OnScrollRcvListener
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.info.PrintLog
import kotlinx.android.synthetic.main.a_menu.*
import java.io.File
import java.lang.IllegalStateException
import java.util.ArrayList


/**
 *[2018/11/14] by Zone
 */
class RvHoriActivity : BaseFeatureActivity() {

    override fun setContentView() {
        val arrayList = ArrayList<String>()
        for (i in 0..30) {
            arrayList.add("哈哈：" + i)
        }
        val recyclerView = RecyclerView(this)
        setContentView(recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter2 = QuickAdapter<String>(this).apply {
            registerDelegate(TextDelegatesHori())
        }
        recyclerView.adapter = adapter2
        adapter2.add(arrayList)
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                println("dx===>"+dx)
            }
        })
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}