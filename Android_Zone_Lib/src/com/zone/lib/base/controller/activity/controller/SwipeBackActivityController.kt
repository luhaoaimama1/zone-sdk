package com.zone.lib.base.controller.activity.controller

import android.content.Context
import android.os.Bundle
import com.zone.lib.base.controller.activity.ActivityController
import com.zone.lib.base.controller.activity.FeatureActivity
import android.os.Vibrator
import android.view.View
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

/**
 * Created by Administrator on 2016/3/26.
 * 直接把 SwipeBackActivity 拿过来 这样就导致保持继承一个基类
 * 因为我吧 震动也集成了所以  需要加上此权限
 * <uses-permission android:name="android.permission.VIBRATE"></uses-permission>
 * 配置参考:https://github.com/Jude95/SwipeBackHelper/blob/master/README_ch.md
 */
class SwipeBackActivityController(activity: FeatureActivity) : ActivityController<FeatureActivity>(activity),
        SwipeBackContract.Controller,
        SwipeBackLayout.SwipeListener {


    companion object {
        val VIBRATE_DURATION = 20
    }

    private lateinit var mHelper: SwipeBackActivityHelper
    protected lateinit var mSwipeBackLayout: SwipeBackLayout
    protected lateinit var swipeBack: SwipeBack
    var defaultSwipeBack: SwipeBack = SwipeBack.LEFT

    override fun onCreateAfter(bundle: Bundle?) {
        super.onCreateAfter(bundle)
        mHelper = SwipeBackActivityHelper(getActivity())
        mHelper.onActivityCreate()
        mSwipeBackLayout = swipeBackLayout
        setSwipeBackFlag(defaultSwipeBack)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        mHelper.onPostCreate()
    }


    fun findViewById(id: Int): View? {
        val v = getActivity()?.findViewById<View>(id)
        return if (v == null) mHelper.findViewById(id) else v
    }


    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.swipeBackLayout
    }


    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }


    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(getActivity())
        swipeBackLayout.scrollToFinishActivity()
    }

    enum class SwipeBack {
        NONE, LEFT, RIGHT, ALL, BOTTOM

    }

    override fun setSwipeBackFlag(swipeBack: SwipeBack) {
        this.swipeBack = swipeBack
        var edgeFlag = -1
        when (swipeBack) {
            SwipeBack.NONE -> {
            }
            //默认就是左边
            SwipeBack.LEFT -> edgeFlag = SwipeBackLayout.EDGE_LEFT
            SwipeBack.RIGHT -> edgeFlag = SwipeBackLayout.EDGE_RIGHT
            SwipeBack.ALL -> edgeFlag = SwipeBackLayout.EDGE_ALL
            SwipeBack.BOTTOM -> edgeFlag = SwipeBackLayout.EDGE_BOTTOM
        }//默认就是左边
        if (edgeFlag != -1)
            mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag)
    }

    override fun onScrollStateChange(state: Int, scrollPercent: Float) {

    }


    override fun onEdgeTouch(edgeFlag: Int) {
        vibrate(VIBRATE_DURATION.toLong())
    }


    override fun onScrollOverThreshold() {
        vibrate(VIBRATE_DURATION.toLong())
    }

    @SuppressWarnings("all")
    private fun vibrate(duration: Long) {
        val vibrator = getActivity()?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, duration)
        vibrator.vibrate(pattern, -1)
    }

}

