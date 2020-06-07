package com.zone.lib.base.controller.activity

import com.zone.lib.base.controller.common.picture.PicktureHelper
import com.zone.lib.base.controller.common.picture.PictureActivityController
import com.zone.lib.base.controller.common.picture.PictureContract

//为什么不用get的方式？因为是匿名内部类 所以找不到~
abstract class BasePictureFeatureActivity : BaseFeatureActivity(), PictureContract.Callback {

    var pictureActivityController: PictureActivityController? = null
    override fun initBaseControllers() {
        super.initBaseControllers()
        pictureActivityController = object : PictureActivityController(this@BasePictureFeatureActivity) {
            override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
                this@BasePictureFeatureActivity.getReturnedPicPath(path, type)
            }
        }
        pictureActivityController?.let {
            registerPrestener(it)
        }

    }

    override fun openCamera() {
        pictureActivityController?.openCamera()
    }

    override fun openPhotos() {
       pictureActivityController?.openPhotos()
    }

    override fun pickPicture() {
       pictureActivityController?.pickPicture()
    }
}