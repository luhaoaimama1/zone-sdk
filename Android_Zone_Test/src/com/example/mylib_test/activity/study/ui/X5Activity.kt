package com.example.mylib_test.activity.study.ui

import com.zone.lib.base.activity.BaseActivity
import android.content.Intent
import java.io.IOException
import com.example.mylib_test.R
import com.example.mylib_test.activity.study.Uri2PathUtil
import kotlinx.android.synthetic.main.a_study_x5.*


/**
 *[2018/11/14] by Zone
 */
class X5Activity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_study_x5)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == 1) {
            data?.data?.let {
                try {
                    documentReaderView.show(Uri2PathUtil.getRealPathFromUri(this, it))
//                    openRender(Uri2PathUtil.getRealPathFromUri(this, it))
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onDestroy() {
        documentReaderView.stop()
        super.onDestroy()
    }



}