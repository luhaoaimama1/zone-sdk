package com.example.mylib_test.activity.three_place

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.arch.lifecycle.LifecycleRegistry


/**
 *[2019/1/3] by Zone
 * 实现原理  我们可以在 不是activity fragment中去自己是实现
 * https://blog.csdn.net/xiatiandefeiyu/article/details/78643482
 */
class LifecycleActivityCore : Activity(), LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun getLifecycle(): Lifecycle = mLifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLifecycleRegistry = LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
//        lifecycle.addObserver()
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }
}