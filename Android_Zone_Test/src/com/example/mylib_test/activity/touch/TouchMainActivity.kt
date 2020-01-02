package com.example.mylib_test.activity.touch

import com.example.mylib_test.R

import android.content.Intent
import android.view.View
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class TouchMainActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_touch_main)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.veclocityTracker -> startActivity(Intent(this, VeclocityTrackerActivity::class.java))
            R.id.gestureDetectorActivity -> startActivity(Intent(this, GestureDetectorActivity::class.java))
            R.id.scaleGestureDetector -> startActivity(Intent(this, MyScaleActivity::class.java))
            R.id.viewDrag -> startActivity(Intent(this, ViewDragStudyActivity::class.java))
            R.id.viewDrag2 -> startActivity(Intent(this, ViewDragStudyActivity2::class.java))
            R.id.scrollerView -> startActivity(Intent(this, ScrollerActivity::class.java))
            R.id.outConflict1 -> startActivity(Intent(this, Conflict1Activity::class.java).putExtra("type", "out"))
            R.id.innerConflict1 -> startActivity(Intent(this, Conflict1Activity::class.java).putExtra("type", "inner"))
            R.id.onTouchConflict1 -> startActivity(Intent(this, Conflict1Activity::class.java).putExtra("type", "innerOntouch"))
            R.id.frameConflict1 -> startActivity(Intent(this, Conflict1Activity::class.java).putExtra("type", "frame"))
            R.id.NestedScrolling -> startActivity(Intent(this, NestedScrollingActivity_hongParent::class.java))
            R.id.NestedScrollingChild -> startActivity(Intent(this, NestedScrollingActivity_Child::class.java))
            R.id.eventPass -> startActivity(Intent(this, EventPassLogActivity::class.java))
            else -> {
            }
        }

    }

}
