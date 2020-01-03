package com.example.mylib_test.activity.touch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib_test.R
import com.example.mylib_test.activity.wifi.entity.WifiItem
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.example.mylib_test.adapter.delegates.TextDelegates
import com.example.mylib_test.adapter.delegates.WifiDelegate
import com.zone.adapter3kt.QuickAdapter
import com.zone.lib.utils.view.graphics.MathUtils
import kotlinx.android.synthetic.main.a_nestscrollb.*
import java.util.*
import view.CenterProgress


/**
 * [2018/8/24] by Zone
 */
class NestedScrollBActivity : BaseFeatureActivity() {
    companion object {
        val data = LinkedList<String>()

        init {
            for (i in 0..29)
                data.add("一直很桑心")
        }
    }
    override fun setContentView() {
        setContentView(R.layout.a_nestscrollb)
        briVp.centerProgress=object : CenterProgress{
            override fun progress(progress: Float) {
                cpv.progressInner= (progress*100).toInt()
            }

        }
        animal.postDelayed({
            ValueAnimator.ofFloat(-251F,-242F,-119F,-35F,23F,27F,23F,9.6F,0F).apply {
                duration=650
                addUpdateListener {
                    animal!!.translationX=MathUtils.linear(it.getAnimatedValue() as Float,-251F,0F,-1080F,0F,MathUtils.Linear.OverOver)
                }
                addListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        animal!!.visibility=View.VISIBLE
                    }
                })
            }.start()
        },1000)
    }

    override fun initData() {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy != 0 && cpv != null) {
                    val totalOffset = (recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent())
                    val s = "进度：${recyclerView.computeVerticalScrollOffset() * 1F / totalOffset}"
                    println(s)
//                    tv!!.setText(s)
                    val progress=(recyclerView.computeVerticalScrollOffset() * 1F / totalOffset*100).toInt()
                    cpv!!.progress= progress
//                    cpv!!.progressInner=progress
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}
        })
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = QuickAdapter<String>(this).apply {
            registerDelegate(TextDelegates())
            add(data)
        }
    }

    override fun setListener() {

    }
}