package com.zone.lib.base.controller.activity.controller

import com.zone.lib.base.controller.BaseFeatureView

interface CollectionContract {

    interface Callback : Controller, BaseFeatureView

    interface Controller {
        fun finishAll()
    }
}