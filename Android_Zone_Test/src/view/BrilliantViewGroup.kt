/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.zone.lib.utils.image.BitmapUtils

/**
 * Copyright (c) 2018 BiliBili Inc.
 *[2018/8/24] by Zone
 */

enum class State { REST, PULLING, LOADING, RELEASE, ANIMATE_LEAVE, ANIMATE_BACK }

class BrilliantViewGroup : FrameLayout, NestedScrollingParent {

    var closeAbleHeight = 300
    var scaleMiniMax = 0.4F
    var leaveProcess = 0F
    var backProcess = 0F
    var state = State.REST

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    }

    override fun dispatchDraw(canvas: Canvas) {
        when (state) {
            State.REST -> super.dispatchDraw(canvas)
            State.ANIMATE_LEAVE -> {
                canvas.save()
                val scaleRadio = if (mTotalUnconsumed > 300) scaleMiniMax else mTotalUnconsumed * 0.6F / 300
                canvas.scale(scaleRadio, scaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
                super.dispatchDraw(canvas)
                canvas.restore()

            }
            State.ANIMATE_BACK -> {
                canvas.save()
                val mTotalUnconsumedTemp = mTotalUnconsumed * backProcess
                val scaleRadio = if (mTotalUnconsumedTemp > 300) scaleMiniMax else 1 - mTotalUnconsumedTemp * (1 - scaleMiniMax) / 300
                canvas.scale(scaleRadio, scaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
                super.dispatchDraw(canvas)
                canvas.restore()

            }
            else -> {
                canvas.save()
                val scaleRadio = if (mTotalUnconsumed > 300) scaleMiniMax else 1 - mTotalUnconsumed * (1 - scaleMiniMax) / 300
                canvas.scale(scaleRadio, scaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
                super.dispatchDraw(canvas)
                canvas.restore()
            }
        }
    }

    //参考 SwipeRefreshLayout_NestedScrollingParent

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return state == State.REST && nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    private var mTotalUnconsumed: Float = 0.toFloat()
    private val mNestedScrollingParentHelper: NestedScrollingParentHelper

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        // Reset the counter of how much leftover scroll needs to be consumed.
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes)
        mTotalUnconsumed = 0f
    }


    private lateinit var targetView: View

    //rv滚动过程中 到头了的时候, 让给了他的嵌套父亲 就是我了。 然后我处理剩下的滚动
    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        targetView = target
        println("ZoneInner:onNestedScroll 执行的 dyUnconsumed:$dyUnconsumed \t mTotalUnconsumed:$mTotalUnconsumed")
        //            判断是否可以下拉
        if (dyUnconsumed < 0 && !ViewCompat.canScrollVertically(target, -1)) {

            // 下拉 就是上面头部
            state = State.PULLING
            mTotalUnconsumed += Math.abs(dyUnconsumed).toFloat()
            println("Zone:onNestedScroll 消耗的:" + mTotalUnconsumed)
            moveSpinner(mTotalUnconsumed)
        }
//            判断是否可以上拉
        if (dyUnconsumed > 0 && !ViewCompat.canScrollVertically(target, 1)) {
            // 上啦 就是上面头部
            state = State.LOADING
            mTotalUnconsumed += dyUnconsumed.toFloat()
            println("Zone:onNestedScroll 消耗的:" + mTotalUnconsumed)
            moveSpinner(mTotalUnconsumed)
        }
    }

    //mTotalUnconsumed 这个就是 当前view乡下已经消耗的值。如果向上的时候。会把 mTotalUnconsumed这个值消耗掉 当消耗没了就 不管了。
    // 如果是向下 recycelrView处理不了 会自动调用走onNestedScroll 让他处理剩下的滑动的
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        println("ZoneInner:onNestedPreScroll 执行的 dy:$dy \t mTotalUnconsumed:$mTotalUnconsumed")
        if (state == State.PULLING) {
            if (dy > 0 && mTotalUnconsumed > 0) {
                if (dy > mTotalUnconsumed) {
                    consumed[1] = dy - mTotalUnconsumed.toInt()
                    resetState(false)
                } else {
                    mTotalUnconsumed -= dy.toFloat()
                    consumed[1] = dy
                }
                println("Zone:onNestedPreScroll 消耗的:" + mTotalUnconsumed)
                moveSpinner(mTotalUnconsumed)
            }
        }
        if (state == State.LOADING) {
            if (dy < 0 && mTotalUnconsumed > 0) {
                if (Math.abs(dy) > mTotalUnconsumed) {
                    consumed[1] = dy + mTotalUnconsumed.toInt()
                    resetState(false)
                } else {
                    mTotalUnconsumed += dy.toFloat()
                    consumed[1] = dy
                }
                println("Zone:onNestedPreScroll 消耗的:" + mTotalUnconsumed)
                moveSpinner(mTotalUnconsumed)
            }
        }
    }

    override fun getNestedScrollAxes(): Int = mNestedScrollingParentHelper.getNestedScrollAxes()

    override fun onStopNestedScroll(target: View) {
        mNestedScrollingParentHelper.onStopNestedScroll(target)
        // unconsumed nested scroll
        if (mTotalUnconsumed > 0) {
            println("Zone:onStopNestedScroll 消耗的:" + mTotalUnconsumed)
            finishSpinner(mTotalUnconsumed)
        }
    }

    private fun moveSpinner(mTotalUnconsumed: Float) {
//        targetView.translationY = if (state == State.PULLING) mTotalUnconsumed else if (state == State.LOADING) mTotalUnconsumed * -1 else 0F
        postInvalidate()
    }

    private fun finishSpinner(mTotalUnconsumed: Float) {
        if (mTotalUnconsumed > closeAbleHeight) {
            //离开
            println("Zone:onStopNestedScroll 离开:")
            goLeaveAnimate()
        } else {
            //回来
            println("Zone:onStopNestedScroll 回来:")
            goBackAnimate()
        }
    }

    val leaveAnimator = ValueAnimator.ofInt(0, 1000)
//    val backAnimator = ValueAnimator.ofFloat(0f, 100f).apply {
////        duration = 1000
//        addListener(listener)
//        addUpdateListener {
//            backProcess = it.animatedValue as Float
//        }
//    }
    val backAnimator = ValueAnimator.ofFloat(0f, 100f)
    val listener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            resetState(true)
        }
    }

    fun resetState(isInvalidate: Boolean) {
        mTotalUnconsumed = 0f
        state = State.REST
        leaveProcess = 0F
        backProcess = 0F
        if (isInvalidate) postInvalidate()
    }

    private fun goLeaveAnimate() {
        leaveAnimator.addUpdateListener { animation ->
            leaveProcess = animation.animatedValue as Int * 1f / 1000
            postInvalidate()
        }
        leaveAnimator.addListener(listener)
        leaveAnimator.start()
    }

    private fun goBackAnimate() {
        backAnimator.addUpdateListener { animation ->
            val temp = animation.animatedValue as Float
            backProcess=0F
            postInvalidate()
        }
        backAnimator.addListener(listener)
        backAnimator .interpolator = LinearInterpolator()
        backAnimator.duration =1000

        backAnimator.start()
    }

}