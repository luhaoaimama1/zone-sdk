package com.example.mylib_test.activity.three_place

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.jetbrains.annotations.NotNull

/**
 *[2019/1/3] by Zone
 */

interface IPresenter : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(@NotNull owner: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(@NotNull owner: LifecycleOwner, @NotNull event: Lifecycle.Event)
}

//链接：https://www.jianshu.com/p/b1208012b268

class LifecycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(object : IPresenter {
            override fun onCreate(owner: LifecycleOwner) {
                Log.e("LifecycleActivity", "onCreate")
            }

            override fun onDestroy() {
                Log.e("LifecycleActivity", "onDestroy")
            }

            override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
                Log.e("LifecycleActivity", " onLifecycleChanged event name:" + event.name)
            }
        })
    }
}