package com.zone.lib.base.controller.fragment

import com.zone.lib.base.controller.common.picture.PicktureHelper
import com.zone.lib.base.controller.common.picture.PictureContract
import com.zone.lib.base.controller.fragment.base.FeatureFragment
import com.zone.lib.base.controller.common.picture.PictureFragmentController

open class BaseFeatureFragment : FeatureFragment(), PictureContract.Callback {

    var pictureActivityController: PictureFragmentController? = null
    override fun initBaseControllers() {
        pictureActivityController = object : PictureFragmentController(this@BaseFeatureFragment) {
            override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
                this@BaseFeatureFragment.getReturnedPicPath(path, type)
            }
        }
        pictureActivityController?.let {
            registerPrestener(it)
        }
        initDefaultConifg()
    }

    open fun initDefaultConifg() {
    }

    override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
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