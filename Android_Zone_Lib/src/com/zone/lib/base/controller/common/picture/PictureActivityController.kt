package com.zone.lib.base.controller.common.picture

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.format.DateFormat
import com.zone.lib.LogZSDK
import com.zone.lib.base.controller.RequestCodeConfig
import com.zone.lib.utils.data.convert.Uri2PathUtil
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.base.controller.activity.base.ActivityController
import com.zone.lib.base.controller.activity.base.FeatureActivity
import java.io.File
import java.util.*

abstract class PictureActivityController(activity: FeatureActivity) : ActivityController<FeatureActivity>(activity),
        PictureContract.Controller {

    private val helper = object : PicktureHelper() {
        override fun startActivityForResult(intent: Intent?, requestCode: Int) {
            getActivity()?.startActivityForResult(intent, requestCode)
        }

        override fun getContext(): Context? = getActivity()

        override fun getReturnedPicPath(path: String?, type: Type) {
            this@PictureActivityController.getReturnedPicPath(path, type)
        }
    }

    override fun openCamera() {
        helper.openCamera()
    }

    override fun openPhotos() {
        helper.openPhotos()
    }

    override fun pickPicture() {
        helper.pickPicture()
    }

    /**
     *
     * @param path 照片的路径  已经返回了 你调用就行
     */
    protected abstract fun getReturnedPicPath(path: String?, type: PicktureHelper.Type)

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        helper.onActivityResult(requestCode, resultCode, intent)
    }
}
