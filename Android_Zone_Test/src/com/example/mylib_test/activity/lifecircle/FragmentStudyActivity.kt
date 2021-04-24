package com.example.mylib_test.activity.lifecircle

import com.example.mylib_test.R
import android.view.View
import androidx.fragment.app.Fragment
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class FragmentStudyActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_lifecircle_fragment_study)
        val beginTransaction = supportFragmentManager.beginTransaction()

        var addFragment: Fragment? = null
        val type = intent.getStringExtra("type")
        when (type) {
            "isViewPagerAV", "isViewPagerNFV" -> {
                addFragment = FragmentStudyViewPagerFragment.newInstance(type) {
                    FragmentStudyNullFragment.newInstance("${type}_f(poi:${it})", true)
                }
            }
            "normalAV", "normalNFV" -> {
                addFragment = FragmentStudyTABFragment.newInstance(type) {
                    FragmentStudyNullFragment.newInstance("${type}_f(poi:${it})", false)
                }

            }
            "isViewPagerPFV_N" -> {
                addFragment = FragmentStudyTABFragment.newInstance(type) {
                    val tag = "${type}_Ftab(posi:${it})"
                    FragmentStudyViewPagerFragment.newInstance(tag) {
                        FragmentStudyNullFragment.newInstance("${tag}(VP_poi:${it})", false)
                    }
                }
            }
            "normalPFV_N" -> {
                addFragment = FragmentStudyTABFragment.newInstance(type) {
                    val tag = "${type}_Ftab(posi:${it})"
                    FragmentStudyTABFragment.newInstance(tag) {
                        FragmentStudyNullFragment.newInstance("${tag}(N_poi:${it})", false)
                    }
                }
            }
            "isViewPagerPFV_VP" -> {
                addFragment = FragmentStudyViewPagerFragment.newInstance(type) {
                    val tag = "${type}_FVP(posi:${it})"
                    FragmentStudyViewPagerFragment.newInstance(tag) {
                        FragmentStudyNullFragment.newInstance("${tag}(VP_poi:${it})", false)
                    }
                }
            }
            "normalPFV_VP" -> {
                addFragment = FragmentStudyViewPagerFragment.newInstance(type) {
                    val tag = "${type}_FVP(posi:${it})"
                    FragmentStudyTABFragment.newInstance(tag) {
                        FragmentStudyNullFragment.newInstance("${tag}(N_poi:${it})", false)
                    }
                }
            }
            else -> {
            }
        }
        addFragment?.let {
            beginTransaction.add(R.id.fl1, it, "addFragment")
            beginTransaction.commitNowAllowingStateLoss()
        }
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.fragmentLifeCircle -> startActivityForResult(Intent(this, ResultActivity2::class.java), LifeCircleActivity.RequestCode)
            else -> {
            }
        }
    }

}
