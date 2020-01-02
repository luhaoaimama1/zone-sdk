package com.example.mylib_test.activity.animal


import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.animal.viewa.PathMeasureView
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_pathmeasure.*

/**
 * Created by fuzhipeng on 2016/11/23.
 */

class PathMeasureActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_pathmeasure)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt_Constructor -> pathMeasureView.setState(PathMeasureView.State.Constructor)
            R.id.bt_NextContour -> pathMeasureView.setState(PathMeasureView.State.NextContour)
            R.id.bt_GetSegmentTrue -> pathMeasureView.setState(PathMeasureView.State.GetSegmentTrue)
            R.id.bt_GetSegmentFalse -> pathMeasureView.setState(PathMeasureView.State.GetSegmentFalse)
            R.id.bt_GetMatrix -> pathMeasureView.setState(PathMeasureView.State.GetMatrix)
            else -> {
            }
        }
    }
}
