package com.example.mylib_test.activity.system

import com.example.mylib_test.R

import android.content.Intent
import android.view.View
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class ResultActivity2 : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.activity_result2)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_toThird -> startActivityForResult(Intent(this, ResultActivity3::class.java), SystemMainActivity.RequestCode)
            R.id.bt_returnOne -> {
                setResult(SystemMainActivity.ResponseCode)
                finish()
            }

            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("second onActivityResult____ requestCode:$requestCode\t resultCode$resultCode")
    }
}
