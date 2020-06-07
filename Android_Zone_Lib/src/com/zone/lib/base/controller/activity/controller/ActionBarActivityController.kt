package com.zone.lib.base.controller.activity.controller

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import com.zone.lib.base.controller.activity.base.ActivityController
import com.zone.lib.base.controller.activity.base.FeatureActivity

/**
 * 隐藏titlebar
 * https://stackoverflow.com/questions/35697402/android-removing-the-title-bar-itself-in-appcompatactivity
 */
class ActionBarActivityController(activity: FeatureActivity) :
        ActivityController<FeatureActivity>(activity),
        ActionBarContract.Controller {
    private var showStatesArrays: Array<out ShowState>? = null

    fun initFirst(vararg showStates: ShowState) {
        showStatesArrays = showStates
    }


    override fun onCreateBefore(bundle: Bundle?) {
        super.onCreateBefore(bundle)
        showStatesArrays?.forEach {
            dealStateOnCreateBefore(it)
        }
    }

    private fun dealStateOnCreateBefore(it: ShowState) {
        when (it) {
            ShowState.FullScreen -> {
                //设置全屏
                val flagFullscreen = WindowManager.LayoutParams.FLAG_FULLSCREEN
                getActivity()?.window?.setFlags(flagFullscreen, flagFullscreen)
            }
            ShowState.HideTitle -> {
                hideTitle()
            }
            ShowState.ShowTitle -> {
                showTitle()
            }
            else -> { //包括null
            }
        }
    }

    override fun onCreateAfter(bundle: Bundle?) {
        super.onCreateAfter(bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.run {
            when (this.itemId) {
                //返回键
                android.R.id.home -> {
                    getActivity()?.supportFinishAfterTransition()
                    true
                }
                else -> false
            }
        } ?: super.onOptionsItemSelected(item)
    }

    override fun hideTitle() {
        getActivity()?.supportActionBar?.hide()
    }

    override fun showTitle() {
        //设置无标题
        getActivity()?.supportActionBar?.show()
    }
}
