package com.zone.lib.base.controller.activity

import com.zone.lib.base.controller.activity.controller.*
import me.imid.swipebacklayout.lib.SwipeBackLayout
import java.lang.IllegalStateException

abstract class BaseFeatureActivity : BasePermissonsActivity(), CollectionContract.Callback
        , ActionBarContract.Callback, SwipeBackContract.Callback {

    override fun initBaseControllers() {
        super.initBaseControllers()
        registerPrestener(CollectionActivityController(this))
        registerPrestener(ActionBarActivityController(this))
        registerPrestener(SwipeBackActivityController(this))
        initDefaultConifg()
    }

    open fun initDefaultConifg() {
        getController(ActionBarActivityController::class.java)?.initFirst(ShowState.HideTitle)
    }

    override fun finishAll() {
        getController(CollectionActivityController::class.java)?.finishAll()
    }

    override fun hideTitle() {
        getController(ActionBarActivityController::class.java)?.hideTitle()
    }

    override fun showTitle() {
        getController(ActionBarActivityController::class.java)?.showTitle()
    }

    override fun setSwipeBackFlag(swipeBack: SwipeBackActivityController.SwipeBack) {
        getController(SwipeBackActivityController::class.java)?.setSwipeBackFlag(swipeBack)
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return getController(SwipeBackActivityController::class.java)?.swipeBackLayout
                ?: throw IllegalStateException("please set controller")
    }


    override fun scrollToFinishActivity() {
        getController(SwipeBackActivityController::class.java)?.scrollToFinishActivity()
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        getController(SwipeBackActivityController::class.java)?.setSwipeBackEnable(enable)
    }
}