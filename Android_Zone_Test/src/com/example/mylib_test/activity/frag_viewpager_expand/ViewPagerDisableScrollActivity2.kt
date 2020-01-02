package com.example.mylib_test.activity.frag_viewpager_expand

import android.annotation.SuppressLint
import android.graphics.Color

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.View.OnClickListener
import android.widget.TextView

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_frag_scoll2.*

import java.util.ArrayList

class ViewPagerDisableScrollActivity2 : BaseFeatureActivity() {

    companion object {
        private val TAB_COUNT = 4
    }

    internal var tvlist: MutableList<TextView> = ArrayList()

    override fun setContentView() {
        setContentView(R.layout.a_frag_scoll2)
    }

    override fun initData() {
        tvlist.add(tv1)
        tvlist.add(tv2)
        tvlist.add(tv3)
        tvlist.add(tv4)
        tv1!!.setTextColor(Color.RED)
        tv1!!.setOnClickListener(jt)
        tv2!!.setOnClickListener(jt)
        tv3!!.setOnClickListener(jt)
        tv4!!.setOnClickListener(jt)

        viewpager!!.setDisableScroll(false)
        viewpager!!.adapter = MyPagerAdapter(supportFragmentManager)
        viewpager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            //这个监听是  那个包固有的监听android.support.v4.view
            override fun onPageSelected(arg0: Int) {
                //划到哪页   那个文本高亮
                for (i in tvlist.indices) {
                    tvlist[i].setTextColor(Color.BLACK)
                }
                tvlist[arg0].setTextColor(Color.RED)

            }

            @SuppressLint("NewApi")
            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        image_1!!.setViewPagerWithItemView(viewpager, tv1, tv2, tv3, tv4)
        image_1!!.setDrawRes(R.drawable.search_line_back)
    }

    override fun setListener() {
    }

    // 点击 文本跳到哪页
    var jt: OnClickListener = OnClickListener { v ->
        for (i in tvlist.indices) {
            tvlist[i].setTextColor(Color.BLACK)
        }
        (v as TextView).setTextColor(Color.RED)

        when (v.getId()) {
            R.id.tv1 -> viewpager!!.currentItem = 0
            R.id.tv2 -> viewpager!!.currentItem = 1
            R.id.tv3 -> viewpager!!.currentItem = 2
            R.id.tv4 -> viewpager!!.currentItem = 3
            else -> {
            }
        }
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return Tab1()
                1 -> return Tab2()
                2 -> return Tab3()
                3 -> return Tab1()
            }
            return null
        }

        override fun getCount(): Int {
            return TAB_COUNT
        }
    }


}
