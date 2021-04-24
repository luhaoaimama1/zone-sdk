package com.example.mylib_test.activity.lifecircle

import android.content.Intent
import com.example.mylib_test.R
import android.view.View
import android.widget.Button
import com.zone.lib.base.controller.activity.BaseFeatureActivity

/**
 * zone todo: 2021/4/22
 * viewPager model 还是 normal
 *
 * parent
 *
 */
class LifeCircleActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_lifecircle)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btNowFragmentVisibleIsViewPager,
            R.id.btNowFragmentVisibleNormal,
            R.id.btParentFragmentVisibleNormal,
            R.id.btParentFragmentVisibleIsViewPager,
            R.id.btParentFragmentVisibleIsViewPager_N,
            R.id.btParentFragmentVisibleNormal_N,
            R.id.btActivityVisibleIsViewPager,
            R.id.btActivityVisibleNormal ->{
                if(v is Button){
                    startActivity(Intent(this, FragmentStudyActivity::class.java).apply {
                        putExtra("type",  v.text)
                    })
                }
            }
            else -> {
            }
        }
    }

}
