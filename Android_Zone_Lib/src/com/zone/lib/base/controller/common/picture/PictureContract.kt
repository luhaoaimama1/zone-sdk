package com.zone.lib.base.controller.common.picture

import com.zone.lib.base.controller.BaseFeatureView

interface PictureContract {

    interface Callback : Controller, BaseFeatureView {
        fun getReturnedPicPath(path: String?, type: PicktureHelper.Type)
    }

    interface Controller {
        fun openCamera()
        fun openPhotos()
        fun pickPicture()
    }
}