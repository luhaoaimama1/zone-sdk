package com.example.mylib_test.activity.lifecircle.helper.fragmentmode

import com.example.mylib_test.activity.lifecircle.helper.visichange.NowFragmentVisibleFragmentLifeController

abstract class IFragmentModeLife(val helper: NowFragmentVisibleFragmentLifeController ) {
    abstract fun onHiddenChanged(hidden: Boolean)
    abstract fun setUserVisibleHint(isVisibleToUser: Boolean)
}
