package com.example.mylib_test.activity.touch

import java.util.Arrays

import com.example.mylib_test.R
import com.example.mylib_test.Images
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class Conflict1Activity : BaseFeatureActivity() {
    override fun setContentView() {
        val type = intent.getStringExtra("type")
        if ("out" == type) {
            setContentView(R.layout.a_touch_confict1)
        } else if ("frame" == type) {
            setContentView(R.layout.a_touch_confict1_frame)
        } else if ("innerOntouch" == type) {
            setContentView(R.layout.a_touch_confict1_ontouch)
        } else {
            setContentView(R.layout.a_touch_confict1_inner)
        }

        val lv = findViewById<View>(R.id.lv) as ListView
        val temp = Arrays.asList(*Images.imageThumbUrls)
        lv.adapter = Adapter2(this, temp)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    inner class Adapter2(val context: Context, private val stuList: List<String>) : BaseAdapter() {

        override fun getCount(): Int {
            return stuList.size
        }

        override fun getItem(position: Int): Any {
            return stuList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = LayoutInflater.from(context).inflate(R.layout.item_textview, null)
            val tv = view.findViewById<View>(R.id.tv) as TextView
            tv.text = stuList[position]
            return view
        }
    }
}
