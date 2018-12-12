package com.example.mylib_test.activity.touch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Outline
import android.graphics.Rect
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING
import android.view.View
import android.widget.TextView
import com.example.mylib_test.MainMenu
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.example.mylib_test.delegates.MenuEntityDeletates
import com.example.mylib_test.delegates.TextDelegates
import com.zone.adapter3.QuickRcvAdapter
import com.zone.lib.base.activity.BaseActivity
import com.zone.lib.utils.view.graphics.MathUtils
import view.BrilliantViewGroup
import view.CircleProgressView
import java.util.*
import view.CenterProgress


/**
 * [2018/8/24] by Zone
 */
class NestedScrollBActivity : BaseActivity() {
    companion object {
        val data = LinkedList<String>()

        init {
            for (i in 0..29)
                data.add("一直很桑心")
        }
    }

    var rv: RecyclerView? = null
    var tv: TextView? = null
    var cpv: CircleProgressView? = null
    private var animal: View?=null
    override fun setContentView() {
        setContentView(R.layout.a_nestscrollb)
    }

    private var briVp: BrilliantViewGroup?=null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun findIDs() {
        rv = findViewById(R.id.rv) as RecyclerView?
        briVp = findViewById(R.id.briVp) as BrilliantViewGroup?
        briVp!!.centerProgress=object : CenterProgress{
            override fun progress(progress: Float) {
                cpv!!.progressInner= (progress*100).toInt()
            }

        }
        tv = findViewById(R.id.tv) as TextView?
        cpv = findViewById(R.id.cpv) as CircleProgressView?
        animal = findViewById(R.id.animal)
        animal!!.postDelayed({
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
        rv!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        rv!!.layoutManager = LinearLayoutManager(this)
        val adapter2 = QuickRcvAdapter<String>(this, data)
            .addViewHolder(TextDelegates())
            .relatedList(rv)
    }

    override fun setListener() {

    }
}