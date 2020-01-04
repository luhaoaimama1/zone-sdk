package com.example.mylib_test.activity.three_place

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.zone.lib.base.controller.activity.BaseFeatureActivity


/**
 *[2019/1/3] by Zone
 * 实现原理  我们可以在 不是activity fragment中去自己是实现
 * https://blog.csdn.net/xiatiandefeiyu/article/details/78643482
 */
class LifecycleActivityCore : BaseFeatureActivity(), LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle = mLifecycleRegistry


    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    override fun setContentView() {
        mLifecycleRegistry = LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        //        lifecycle.addObserver()
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}