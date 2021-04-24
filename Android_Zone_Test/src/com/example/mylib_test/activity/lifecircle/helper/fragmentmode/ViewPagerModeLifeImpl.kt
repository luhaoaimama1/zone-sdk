package com.example.mylib_test.activity.lifecircle.helper.fragmentmode

import com.example.mylib_test.activity.lifecircle.helper.visichange.NowFragmentVisibleFragmentLifeController

class ViewPagerModeLifeImpl(helper: NowFragmentVisibleFragmentLifeController) : IFragmentModeLife(helper) {

    override fun onHiddenChanged(hidden: Boolean) {
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    }
}