package com.example.mylib_test.activity.frag_viewpager_expand

import java.util.ArrayList

import com.example.mylib_test.R

import android.annotation.SuppressLint
import android.graphics.Color

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import android.util.DisplayMetrics
import android.view.View.OnClickListener
import android.widget.TextView
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_frag_scoll.*

class ViewPagerDisableScrollActivity : BaseFeatureActivity() {
    internal var tvlist: MutableList<TextView> = ArrayList()

    companion object {
        private val TAB_COUNT = 4
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

    override fun setContentView() {
        setContentView(R.layout.a_frag_scoll)
    }

    override fun initData() {
        tvlist.add(tv1)
        tvlist.add(tv2)
        tvlist.add(tv3)
        tvlist.add(tv4)
        tv1.setTextColor(Color.RED)
        tv1.setOnClickListener(jt)
        tv2.setOnClickListener(jt)
        tv3.setOnClickListener(jt)
        tv4.setOnClickListener(jt)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val kuan = dm.widthPixels// 宽度height = dm.heightPixels ;//高度
        image_1!!.onScrolled(0f, kuan.toFloat())

        cb!!.setOnCheckedChangeListener { buttonView, isChecked -> viewpager!!.setDisableScroll(isChecked) }
        cb!!.performClick()

        viewpager!!.adapter = MyPagerAdapter(supportFragmentManager)
        // viewpager.setOnTouchListener(new OnTouchListener() {
        //
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        // //viewpager.getChildAt(viewpager.getCurrentItem()).getLeft();
        // //String str="Touch1:";
        // //tv4.setText(str);
        // return false;
        // }
        // });
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
                // int kuan = image_1.getWidth();
                val ko = image_1!!.y
                val dm = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(dm)
                val kuan = dm.widthPixels// 宽度height = dm.heightPixels ;//高度

                val a = (arg0.toFloat() + arg1) * kuan
                val b = (arg0.toFloat() + arg1) / 4 * kuan
                val str = ("  1:" + arg0.toString() + "   2:"
                        + arg1.toString() + "   3:" + arg2.toString()
                        + "  宽:" + kuan.toString() + "  当前:"
                        + b.toString() + "  x:" + ko.toString())
                // tv4.setText(str);

                image_1!!.onScrolled(b, kuan.toFloat())

            }

            override fun onPageScrollStateChanged(arg0: Int) {

            }
        })
    }

    override fun setListener() {
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return Tab2()
                1 -> return Tab1()
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
