package com.example.mylib_test.activity.touch

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.activity_nested_child.*


class NestedScrollingActivity_Child : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.activity_nested_child)
    }

    override fun initData() {
        for (i in 0..6) {
            val view = LayoutInflater.from(this).inflate(R.layout.item_rc_textview, null)
            //            view.setLayoutParams(lp);
            (view.findViewById<View>(R.id.tv) as TextView).text = "Child Demo -> $i"
            //            nestedScrollingChildView.addView(view,i,new LinearLayout.LayoutParams(
            //                    LinearLayout.LayoutParams.MATCH_PARENT,200
            //            ));
            recyclerView.addView(view, i)
        }
    }

    override fun setListener() {

    }


}
