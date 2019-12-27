package com.example.mylib_test.activity.study

import android.view.View
import com.zone.lib.base.activity.BaseActivity
import android.content.Intent
import com.example.mylib_test.R
import com.example.mylib_test.activity.study.ui.*


/**
 *[2018/11/14] by Zone
 */
class StudyMainActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_study_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.android_pdf -> startActivity(Intent(this,PDFActivity::class.java))
            R.id.x5_open_office -> startActivity(Intent(this, X5Activity::class.java))
            R.id.pdfbox -> startActivity(Intent(this, PDFBoxActivity::class.java))
            R.id.google_office -> startActivity(Intent(this, GoogleOfficeActivity::class.java))
            R.id.tips -> startActivity(Intent(this, TipsActivity::class.java))
            R.id.dynamicLogo -> startActivity(Intent(this, DynamicLogoActivity::class.java))
            R.id.bt_data_crash -> startActivity(Intent(this, DynamicLogoActivity::class.java))
            else -> {}
        }
    }
}