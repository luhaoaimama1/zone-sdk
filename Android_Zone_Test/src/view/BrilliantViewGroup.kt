
package view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.zone.lib.utils.data.convert.DensityUtils

/**
 *[2018/8/24] by Zone
 */
object MathUtils {
    //    t, tMin, tMax, value1, value2
    fun <T : Number, R : Number> linearMap(srcNow: T, src1: T, src2: T, dst1: R, dst2: R): R {

        val radio: Float = when (srcNow) {
            is Float -> (srcNow.toFloat() - src1.toFloat()) / (src2.toFloat() - src1.toFloat())
            is Double -> ((srcNow.toDouble() - src1.toDouble()) / (src2.toDouble() - src1.toDouble())).toFloat()
            is Int -> ((srcNow.toInt() - src1.toInt()) / (src2.toInt() - src1.toInt())).toFloat()
            is Long -> ((srcNow.toLong() - src1.toLong()) / (src2.toLong() - src1.toLong())).toFloat()
            else -> throw IllegalStateException("状态异常")
        }
        val result: Float = when (dst1) {
            is Double -> (radio * (dst2.toDouble() - dst1.toDouble()) + dst1).toFloat()
            is Float -> radio * (dst2.toFloat() - dst1.toFloat()) + dst1
            is Int -> radio * (dst2.toInt() - dst1.toInt()).toFloat() + dst1.toFloat()
            is Long -> radio * (dst2.toLong() - dst1.toLong()) + dst1
            else -> throw IllegalStateException("状态异常")
        }
        return when (dst1) {
            is Double -> result.toDouble()
            is Float -> result
            is Int -> result.toInt()
            is Long -> result.toLong()
            else -> throw IllegalStateException("状态异常")
        } as R
    }
}

enum class State { REST, PULLING, LOADING, RELEASE, ANIMATE_LEAVE, ANIMATE_BACK }

interface CenterProgress{
    fun progress(progress:Float)
}

class BrilliantViewGroup : FrameLayout, NestedScrollingParent {

    var closeAbleHeight = 300
    var scaleMiniMax = 0.4F
    var scaleLeave = 0.3F
    var leaveProcess = 0F
    var backProcess = 0F
    var state = State.REST
    var centerProgress:CenterProgress?=null


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var textPaint: Paint

    init {
        mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.textSize = DensityUtils.dp2px(context, 12F).toFloat()
        textPaint.color = Color.WHITE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val childAt = getChildAt(0)
            childAt.post{
                /* Sets the Outline of the View. */
                val mOutlineProvider = object : ViewOutlineProvider() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun getOutline(view: View, outline: Outline) {
                        outline.setRoundRect(Rect(0,0,width,height), 100F)
                    }
                }
                childAt.setOutlineProvider(mOutlineProvider);
                /* Enables clipping on the View. */
                childAt.setClipToOutline(true);
            }
        }
    }

    val martrix = Matrix()
    override fun dispatchDraw(canvas: Canvas) {
        val restoreCount = canvas.save()
        martrix.reset()
        when (state) {
            State.REST -> {
            }
            State.ANIMATE_LEAVE -> {
                val offsetTotalY = height * (.5f + scaleLeave / 2)
                val nowOffsetY = offsetTotalY * leaveProcess
                val nowScaleRadio = MathUtils.linearMap(leaveProcess, 0F, 1F, scaleMiniMax, scaleLeave)

                background!!.alpha=MathUtils.linearMap(leaveProcess, 0F, 1F, 127, 0)
                martrix.postScale(nowScaleRadio, nowScaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
                martrix.postTranslate(0F, -nowOffsetY)
            }
            State.ANIMATE_BACK -> {
                val mTotalUnconsumedTemp = mTotalUnconsumed * (1 - backProcess)
                val scaleRadio = MathUtils.linearMap(mTotalUnconsumedTemp, 300F, 0F, scaleMiniMax, 1F)

                centerProgress?.progress(MathUtils.linearMap(mTotalUnconsumedTemp, 300F, 0F, 1F, 0F))
                textPaint.alpha = MathUtils.linearMap(mTotalUnconsumedTemp, 300F, 0F, 255, 100)
                background!!.alpha=MathUtils.linearMap(mTotalUnconsumedTemp, 300F, 0F, 127, 255)
                martrix.postScale(scaleRadio, scaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
            }
            else -> {
                val scaleRadio = if (mTotalUnconsumed > 300) scaleMiniMax else MathUtils.linearMap(mTotalUnconsumed, 300F, 0F, scaleMiniMax, 1F)

                textPaint.alpha = MathUtils.linearMap(scaleRadio, scaleMiniMax, 1F, 255, 100)
                background!!.alpha=MathUtils.linearMap(scaleRadio, scaleMiniMax, 1F, 127, 255)
                centerProgress?.progress(MathUtils.linearMap(scaleRadio, scaleMiniMax, 1F, 1F, 0F))
                martrix.postScale(scaleRadio, scaleRadio, (width / 2).toFloat(), (height / 2).toFloat())
            }
        }

        //把原来的背景清除掉  才能用alpha
        canvas.clipRect(0F,0F,width.toFloat(), height.toFloat())
        canvas.drawColor(Color.TRANSPARENT)
        background.draw(canvas)

        canvas.concat(martrix)
        super.dispatchDraw(canvas)//绘制内容

        if (state != State.ANIMATE_LEAVE)
            canvas.drawText("关闭", (width - textPaint.measureText("关闭")) / 2,
                height.toFloat() + DensityUtils.dp2px(context, 21F), textPaint)

        canvas.restoreToCount(restoreCount)
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

    //总结：就是自身控件存储了一定的滑动偏移值。在内部空间滑动前问此控件 需要消耗一定的滑动值不
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
        }else{
            resetState(false)
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
            state = State.ANIMATE_LEAVE
            leaveAnimator.start()
        } else {
            //回来
            state = State.ANIMATE_BACK
            println("Zone:onStopNestedScroll 回来:")
            backAnimator.start()
        }
    }

    val leaveAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                resetState(true)
            }
        })
        addUpdateListener { animation ->
            leaveProcess = animation.animatedValue as Float
            postInvalidate()
        }
    }
    val backAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                resetState(true)
            }
        })
        addUpdateListener {
            backProcess = it.animatedValue as Float
            postInvalidate()
        }
    }

    fun resetState(isInvalidate: Boolean) {
        mTotalUnconsumed = 0f
        state = State.REST
        leaveProcess = 0F
        backProcess = 0F
        if (isInvalidate) postInvalidate()
    }
}