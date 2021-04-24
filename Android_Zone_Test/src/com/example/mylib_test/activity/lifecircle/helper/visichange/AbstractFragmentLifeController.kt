package com.example.mylib_test.activity.lifecircle.helper.visichange

import androidx.fragment.app.Fragment
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife
import com.example.mylib_test.activity.lifecircle.helper.Mode

/**
 *  定义可见的规则：
 *  onresume之后 onPause之前，并且如果有parentFragment的话 也要考虑父parentFrament的可见性 最后要考虑自己的可见性
 *  activityVisibleHint && parentFragmentVisibleHintHasInit && parentFragmentVisibleHint && nowFragmentVisibleHint()
 */
open class AbstractFragmentLifeController(val fragment: Fragment, val iFragmentLife: IFragmentLife) {
    var debugTag = ""
    protected var isFirstVisibility = false

    //父页面是否可见
    protected var parentFragmentVisibleHint = true
    //父页面是否可见 是否初始化
    protected var parentFragmentVisibleHintHasInit = false

    //activity是否可见  onResume 就是true //初始化的时候不可见
    protected var activityVisibleHint = false

    //当前fragment是否可见
    fun nowFragmentVisibleHint() = fragment.userVisibleHint

    //真正可见性的临时存储
    protected var realVisibleHint = false

    fun isVisibleToUser() = realVisibleHint

    //真正可见性
    protected fun isVisibleToUserInner() = activityVisibleHint && parentFragmentVisibleHintHasInit && parentFragmentVisibleHint && nowFragmentVisibleHint()

    @Synchronized
    internal fun notifyVisibilityChanged(mode: Mode) {
        val visibleToUser = isVisibleToUserInner()
        if (realVisibleHint == visibleToUser) return
        onFragmentVisibilityChanged(visibleToUser, mode)
    }

    protected open fun onFragmentVisibilityChanged(isVisibleToUser: Boolean, mode: Mode) {
        if (isVisibleToUser && !isFirstVisibility) {
            isFirstVisibility = true
            iFragmentLife.onFirstVisibility()
        }
        realVisibleHint = isVisibleToUser
        iFragmentLife.onFragmentVisibilityChanged(realVisibleHint, mode)
    }
}