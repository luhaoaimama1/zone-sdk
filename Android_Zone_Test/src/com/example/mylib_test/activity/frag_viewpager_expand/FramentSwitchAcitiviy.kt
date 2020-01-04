package com.example.mylib_test.activity.frag_viewpager_expand

import com.example.mylib_test.R

import com.zone.lib.utils.activity_fragment_ui.FragmentSwitcher

import android.os.Bundle

import android.view.View
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class FramentSwitchAcitiviy : BaseFeatureActivity() {
    private var fragmentSwitcher: FragmentSwitcher? = null

    override fun setContentView() {
        setContentView(R.layout.a_frament_switch_test)
    }

    override fun initData() {
        fragmentSwitcher = FragmentSwitcher(this, R.id.framelayout)
        fragmentSwitcher!!.setPriDefaultAnimal(android.R.anim.fade_in, android.R.anim.fade_out)
        //		fragmentSwitcher.initFragment(Tab1.class, Tab2.class, Tab3.class);
        val tab2 = Tab2()
        val bundle = Bundle()
        bundle.putString("mode", "normal")
        tab2.arguments = bundle
        fragmentSwitcher!!.initFragment(Tab1(), tab2, Tab3())
        fragmentSwitcher!!.switchToNull()
        //		fragmentSwitcher.switchPage(0);
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.open1 -> fragmentSwitcher!!.switchPage(0)
            R.id.open2 -> fragmentSwitcher!!.switchPage(1)
            R.id.open3 -> fragmentSwitcher!!.switchPage(2)
            R.id.open4 -> fragmentSwitcher!!.switchToNull()
            else -> {
            }
        }
    }
}
