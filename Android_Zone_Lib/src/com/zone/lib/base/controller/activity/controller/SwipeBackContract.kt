package com.zone.lib.base.controller.activity.controller

import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import com.zone.lib.base.controller.BaseFeatureView

interface SwipeBackContract {

    interface Callback : Controller, BaseFeatureView

    interface Controller: SwipeBackActivityBase {
        fun setSwipeBackFlag(swipeBack: SwipeBackActivityController.SwipeBack)
    }
}