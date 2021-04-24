package com.example.mylib_test.activity.lifecircle.helper.visichange

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.mylib_test.LogApp
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife
import com.example.mylib_test.activity.lifecircle.helper.Mode
import com.example.mylib_test.activity.lifecircle.helper.helper.FragmentLifeManagers

open class ActivityVisibleFragmentLifeController(fragment: Fragment, iFragmentLife: IFragmentLife) : AbstractFragmentLifeController(fragment, iFragmentLife) {
    init {
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume(owner: LifecycleOwner) {
                initParentVisibleHint(fragment)

                activityVisibleHint = true
                LogApp.d("${debugTag}_activityVisibleHint onResume:${activityVisibleHint}")
                notifyVisibilityChanged(Mode.ActivityVisibilityChanged)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause(owner: LifecycleOwner) {
                activityVisibleHint = false
                LogApp.d("${debugTag}_activityVisibleHint onPause:${activityVisibleHint}")
                notifyVisibilityChanged(Mode.ActivityVisibilityChanged)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                FragmentLifeManagers.getInstance().remove(fragment)
            }
        })
    }

    open fun initParentVisibleHint(fragment: Fragment) {
    }
}