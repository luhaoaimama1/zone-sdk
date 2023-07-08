package com.example.mylib_test.activity.custom_view

import android.view.View
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class AeMaskActivity: BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_ae_mask)
        val findViewById = findViewById<View>(R.id.view)
        val bt2 = findViewById<View>(R.id.bt2)
        bt2.setOnClickListener{
            if( findViewById.visibility==View.GONE){
                findViewById.visibility=View.VISIBLE
            }else{
                findViewById.visibility=View.GONE
            }
        }
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}