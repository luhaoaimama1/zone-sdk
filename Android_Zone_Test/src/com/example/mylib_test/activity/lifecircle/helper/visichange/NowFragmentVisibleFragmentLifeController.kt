package com.example.mylib_test.activity.lifecircle.helper.visichange

import androidx.fragment.app.Fragment
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife
import com.example.mylib_test.activity.lifecircle.helper.Mode
import com.example.mylib_test.activity.lifecircle.helper.fragmentmode.IFragmentModeLife
import com.example.mylib_test.activity.lifecircle.helper.fragmentmode.NormalModeLifeImpl
import com.example.mylib_test.activity.lifecircle.helper.fragmentmode.ViewPagerModeLifeImpl

open class NowFragmentVisibleFragmentLifeController(fragment: Fragment, iFragmentLife: IFragmentLife) : ParentChangedFragmentLifeController(fragment, iFragmentLife) {
    protected var manager: IFragmentModeLife = NormalModeLifeImpl(this)
    protected var hasTouchSetUserVisibleHint = false
    var switchModeIsViewPager = false
        get() = field
        set(value) {
            field = value
            manager = if (switchModeIsViewPager) {
                ViewPagerModeLifeImpl(this)
            } else {
                NormalModeLifeImpl(this)
            }
        }

    fun onHiddenChanged(hidden: Boolean) {
        manager.onHiddenChanged(hidden)
    }

    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (!hasTouchSetUserVisibleHint) {
            hasTouchSetUserVisibleHint = true
        }
        manager.setUserVisibleHint(isVisibleToUser)
        notifyVisibilityChanged(Mode.UserVisibleChanged)
    }
}