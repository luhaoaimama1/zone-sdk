package com.example.mylib_test.activity.lifecircle.helper

import androidx.fragment.app.Fragment
import com.example.mylib_test.activity.lifecircle.helper.helper.FragmentLifeManagers
import com.example.mylib_test.activity.lifecircle.helper.visichange.NowFragmentVisibleFragmentLifeController

/**
 * 使用方式：参考FragmentStudyTagFragment
 *
 *  fragment.userVisibleHint是fragment真正的是展示ing 但是生命周期不会对其有有影响
 *  isRealVisibility表示可见性的改变 影响点是Mode与userVisibleHint
 */
class FragmentLifeController(fragment: Fragment, iFragmentLife: IFragmentLife) : NowFragmentVisibleFragmentLifeController(fragment, iFragmentLife) {
    init {
        FragmentLifeManagers.getInstance().add(fragment, this)
    }
}