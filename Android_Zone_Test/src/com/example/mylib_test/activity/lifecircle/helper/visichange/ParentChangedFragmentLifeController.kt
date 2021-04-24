package com.example.mylib_test.activity.lifecircle.helper.visichange

import androidx.fragment.app.Fragment
import com.example.mylib_test.LogApp
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife
import com.example.mylib_test.activity.lifecircle.helper.Mode
import com.example.mylib_test.activity.lifecircle.helper.helper.FragmentLifeManagers
/**
 *  第一次可见的触发时机一定是onResume之后
 *
 *  原因就是 如果fragment有parent那么 会在onattch之后才能取到，而可见的定义其实对用户来说是onresume之后。
 *  所以在onresume的时候初始化一下 parent的可见性。之后parent每次改变都会找到对应的childFragment进行更换
 */
open class ParentChangedFragmentLifeController(fragment: Fragment, iFragmentLife: IFragmentLife) : ActivityVisibleFragmentLifeController(fragment, iFragmentLife) {

    override fun initParentVisibleHint(fragment: Fragment) {
        if (!parentFragmentVisibleHintHasInit) {
            parentFragmentVisibleHintHasInit = true

            //修复viewpager 嵌套viewpager范例中 最后那个viewpager1 可显示的问题 因为parent其实是false
            val parentFragment = fragment.parentFragment
            if (parentFragment != null) {
                FragmentLifeManagers.getInstance().get(parentFragment)?.let {
                    parentFragmentVisibleHint = it.isVisibleToUserInner()
                    LogApp.d("${debugTag}_onResume init parentFragmentVisible:${parentFragmentVisibleHint}}")
                }
            }
        }
    }

    override fun onFragmentVisibilityChanged(isVisibleToUser: Boolean, mode: Mode) {
        super.onFragmentVisibilityChanged(isVisibleToUser, mode)
        notifyChildFragmentsChanged()
    }

    private fun notifyChildFragmentsChanged() {
        if (fragment.host == null) {
            return //防止fragment.childFragmentManager报错Fragment has not been attached yet.
        }

        //通过itemFragment 找到helper类 然后调用onFragmentVisibilityChangedNoCheck
        for (itemFragment in fragment.childFragmentManager.fragments) {
            val itemFragmentLifeHelper = FragmentLifeManagers.getInstance().get(itemFragment)
            itemFragmentLifeHelper?.let {
                val realVisible = isVisibleToUser()
                val parentChildVisibleIsDiff = it.isVisibleToUser() != realVisible
                if (parentChildVisibleIsDiff) {
                    it.parentFragmentVisibleHint = realVisible
                    it.notifyVisibilityChanged(Mode.ParentFragmentChanged)
                }
            }
        }
    }
}