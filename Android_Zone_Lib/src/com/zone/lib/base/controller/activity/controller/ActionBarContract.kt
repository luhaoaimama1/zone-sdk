package com.zone.lib.base.controller.activity.controller

import com.zone.lib.base.controller.BaseFeatureView

interface ActionBarContract {

    interface Callback : Controller, BaseFeatureView

    interface Controller {
        fun hideTitle()
        fun showTitle()
    }
}