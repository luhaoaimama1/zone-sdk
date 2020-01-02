package com.example.mylib_test.activity.animal

import com.example.mylib_test.R
import com.nineoldandroids.view.ViewHelper

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.view.View
import android.view.animation.LinearInterpolator
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_anipro.*

class AniPro : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_anipro)
    }

    override fun initData() {
    }

    override fun setListener() {
    }


    fun rotateyAnimRun(view: View) {
        //         ObjectAnimator//
        //         .ofFloat(view, "rotationX", 0.0F, 360.0F)//
        //         .setDuration(500)//
        //         .start();
        //        view.setCameraDistance(20000);
        //        ViewHelper.setPivotY(view,500);
        //        ViewHelper.setPivotX(view,500);
        ViewHelper.setRotationX(view, 40f)

    }

    fun togetherRun(view: View) {
        val anim1 = ObjectAnimator.ofFloat(button2, "scaleX",
                1.0f, 2f)
        val anim2 = ObjectAnimator.ofFloat(button2, "scaleY",
                1.0f, 2f)
        val animSet = AnimatorSet()
        animSet.duration = 2000
        animSet.interpolator = LinearInterpolator()
        //两个动画同时执行
        animSet.playTogether(anim1, anim2)
        //        animSet.playSequentially(anim1, anim2); 序列播放
        animSet.start()
    }

    /**
     * 抛物线
     * @param view
     */
    fun paowuxian(view: View) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = 3000
        valueAnimator.setObjectValues(PointF(0f, 0f))
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.setEvaluator { fraction, startValue, endValue ->
            // fraction = t / duration
            // x方向200px/s ，则y方向0.5 * 10 * t
            val point = PointF()
            point.x = 200f * fraction * 3f
            point.y = 0.5f * 200f * (fraction * 3) * (fraction * 3)
            point
        }

        valueAnimator.start()
        valueAnimator.addUpdateListener { animation ->
            val point = animation.animatedValue as PointF
            button2.x = point.x
            button2.y = point.y
        }
    }
}
