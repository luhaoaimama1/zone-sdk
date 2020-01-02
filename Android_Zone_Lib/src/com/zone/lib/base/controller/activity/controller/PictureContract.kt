package com.zone.lib.base.controller.activity.controller

import com.zone.lib.base.controller.BaseFeatureView

interface PictureContract {

    interface Callback : Controller, BaseFeatureView {
        fun getReturnedPicPath(path: String?, type: PictureActivityController.Type)
    }

    interface Controller {
        fun openCamera()
        fun openPhotos()
        fun pickPicture()
    }
}