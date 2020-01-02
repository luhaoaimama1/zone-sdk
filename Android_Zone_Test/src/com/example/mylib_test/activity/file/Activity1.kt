package com.example.mylib_test.activity.file

import android.content.Intent
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class Activity1 : BaseFeatureActivity() {

    override fun setContentView() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    private var condition = true
    override fun onStart() {
        super.onStart()
        println("Activity1====onStart")
    }

    override fun onResume() {
        super.onResume()
        println("Activity1====onResume")
        if (condition) {
            startActivity(Intent(this, Activity2::class.java))
            condition = false
        }
    }

    override fun onStop() {
        super.onStop()
        println("Activity1====onStop")
    }

    override fun onPause() {
        super.onPause()
        println("Activity1====onPause")
    }

    override fun onRestart() {
        super.onRestart()
        println("Activity1====onRestart")
    }

}
