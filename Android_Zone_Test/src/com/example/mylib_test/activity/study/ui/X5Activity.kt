package com.example.mylib_test.activity.study.ui

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BasePictureFeatureActivity
import com.zone.lib.base.controller.common.picture.PicktureHelper
import com.zone.lib.base.controller.common.picture.PictureActivityController
import kotlinx.android.synthetic.main.a_study_x5.*

/**
 *[2018/11/14] by Zone
 */
class X5Activity : BasePictureFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_study_x5)
        pickPicture()
    }

    override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
        if (path != null) {
            documentReaderView.show(path)
        }
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