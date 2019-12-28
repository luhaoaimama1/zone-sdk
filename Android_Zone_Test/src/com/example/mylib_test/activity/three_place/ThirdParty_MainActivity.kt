package com.example.mylib_test.activity.three_place

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mylib_test.R

/**
 *[2018/7/10] by Zone
 */
class ThirdParty_MainActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_thirdparty)
    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.google_pull -> startActivity(Intent(this, GooglePullActvity::class.java))
            R.id.bt_glide -> startActivity(Intent(this, GildeActivity::class.java))
            R.id.bt_fresco -> startActivity(Intent(this, FrescoActivity::class.java))
//            R.id.bt_swtichButton -> startActivity(Intent(this, RippleViewActivity::class.java))
            R.id.bt_blur -> startActivity(Intent(this, BlurActivity::class.java))
            R.id.textBug -> startActivity(Intent(this, TextBugActivity::class.java))
            R.id.lifeCycle -> startActivity(Intent(this, LifecycleActivity::class.java))
        }
    }

}