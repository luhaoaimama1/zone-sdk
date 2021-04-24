package com.example.mylib_test.activity.lifecircle.helper.fragmentmode

import com.example.mylib_test.activity.lifecircle.helper.visichange.NowFragmentVisibleFragmentLifeController

class NormalModeLifeImpl(helper: NowFragmentVisibleFragmentLifeController) : IFragmentModeLife(helper) {
    //非viewPager模式 只有    beginTransaction.hide/replace(it)。才能触发 onHiddenChanged
    override fun onHiddenChanged(hidden: Boolean) {
        helper.fragment.userVisibleHint = !hidden
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    }
}