package com.example.mylib_test.activity.custom_view

import com.example.mylib_test.R
import com.example.mylib_test.activity.frag_viewpager_expand.FramentSwitchAcitiviy
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity2
import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class CustomView_MainActivity : BaseFeatureActivity(), OnClickListener {

    override fun setContentView() {
        setContentView(R.layout.a_custom_view)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.frg_disable_scroll -> startActivity(Intent(this, ViewPagerDisableScrollActivity::class.java))
            R.id.frg_scroll_ -> startActivity(Intent(this, ViewPagerDisableScrollActivity2::class.java))
            R.id.frammentSwitch -> startActivity(Intent(this, FramentSwitchAcitiviy::class.java))
            R.id.bt_viewPagerIndicator -> {
            }
            R.id.arcMenu -> startActivity(Intent(this, ArcMenuTestActivity::class.java))
            R.id.square_test -> startActivity(Intent(this, SquareTestActivity::class.java))
            R.id.bt_hero1 -> startActivity(Intent(this, AndroidHeroActivity::class.java).putExtra("type", "circle"))
            R.id.bt_hero2 -> startActivity(Intent(this, AndroidHeroActivity::class.java).putExtra("type", "circle2"))
            R.id.bt_scroll -> startActivity(Intent(this, AndroidHeroActivity::class.java).putExtra("type", "scroll"))
            R.id.chengcheng -> startActivity(Intent(this, ChengChengActivity::class.java))
            R.id.ges -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "ges"))
            R.id.ges2 -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "ges2"))
            R.id.par -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "par"))
            R.id.bt_fold -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "bt_fold"))
            R.id.bt_foldViewGroup -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "bt_foldViewGroup"))
            R.id.bt_wheel -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "bt_wheel"))
            R.id.bt_ScrollerView -> startActivity(Intent(this, ScrollerViewActivity::class.java))
            R.id.bt_FramePaddding -> startActivity(Intent(this, ChengChengActivity::class.java).putExtra("type", "FrameLayoutPadding"))
            R.id.bt_BottomSheet -> startActivity(Intent(this, BottomSheetActivity::class.java))
            //zone todo: 2020/11/10 全局至灰
            R.id.bt_all_gray -> startActivity(Intent(this, GrayViewActivity::class.java))
            R.id.bt_marquee -> startActivity(Intent(this, MaqueeViewActivity::class.java))
            else -> {
            }
        }//			startActivity(new Intent(this,ViewpagerIndicatorActivity.class));
    }

}
