package com.example.mylib_test.activity.study

import android.app.Activity
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import android.widget.Button

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mylib_test.R
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.data.DataWarp
import com.zone.lib.base.BasePopWindow
import com.zone.lib.utils.data.info.LogcatHelperLevel
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

import java.util.ArrayList

/**
 * Created by Administrator on 2016/3/21.
 *
 * 仅仅调用show()即可
 * <br></br>默认颜色　　是浅黑色
 *
 * @param activity             在那个activity 弹出pop
 */


class LogsPop(activity: Activity, val callback: Callback) : BasePopWindow(activity) {
    interface Callback {
        fun click(name: String)
    }

    private var lv: RecyclerView? = null
    internal var listData: MutableList<String> = ArrayList()

    init {
        setPopContentView(R.layout.pop_list, -1)
        for (mode in LogcatHelperLevel.values()) {
            listData.add(mode.name)
            println("mode.name():" + mode.name)
        }

    }


    override fun findView(mMenuView: View) {
        lv = mMenuView.findViewById<View>(R.id.lv) as RecyclerView
        lv!!.layoutManager = LinearLayoutManager(activity)
    }

    override fun initData() {
        val delegates = object : ViewDelegatesDemo<String>() {
            override val layoutId: Int = R.layout.item_textview_only

            override fun onBindViewHolder(position: Int, item: DataWarp<String>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
                item.data?.let {
                    baseHolder.setText(R.id.tv, it).setOnClickListener(View.OnClickListener { view ->
                        callback.click(it)
                        dismiss()
                    }, R.id.rl_main)
                }
            }
        }

        lv!!.adapter = QuickAdapter<String>(activity).apply {
            registerDelegate(delegates)
        }
    }

    override fun setListener() {

    }

    override fun setLocation(view: View) {
        showAtLocation(view, Gravity.NO_GRAVITY, 0, 0)
    }
}
