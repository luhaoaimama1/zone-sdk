package view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import com.example.mylib_test.LogApp
import com.zone.lib.utils.view.graphics.MathUtils

/**
 * [2017] by Zone
 */
open class SlideViewGroupV2 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var downLeft = 0
    private var downTop = 0
    private var downX = 0
    private var downY = 0
    private var offX = 0
    private var offY = 0
    private var leaveStartValue = 0
    private var leaveEndValue = 0
    private val animatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            leaveAnimator.removeListener(this)
            leaveStartValue = 0
            leaveEndValue = 0
        }
    }
    private val leaveAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener { animation ->
            val leaveProcess = animation.animatedValue as Float
            val clampX = MathUtils.linear(leaveProcess, 0f, 1f, leaveStartValue, leaveEndValue)//
            ViewCompat.offsetLeftAndRight(this@SlideViewGroupV2, clampX - left)
        }
    }

    var clickIntercept = false
    var downTime = 0L

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clickIntercept = false
                downTime = System.currentTimeMillis()
                downLeft = left
                downTop = top
                downX = event.rawX.toInt()
                downY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                offX = event.rawX.toInt() - downX
                offY = event.rawY.toInt() - downY
                update(offX, offY)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> upCancel(offX, offY)
        }
        //刷新
        return super.dispatchTouchEvent(event)
    }

    val rectLimit = Rect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            val view = parent as? View
            rectLimit.set(view?.left ?: 0, view?.top ?: 0, view?.right ?: 0, view?.bottom ?: 0)
            LogApp.d("限定：rectlimit ${rectLimit.toShortString()}")
        }
    }

    open fun update(dx: Int, dy: Int) {
        LogApp.d("update dx: $dx \tdy: $dy")
        var clampedX = downLeft + dx //滑动后应该left
        var clampedY = downTop + dy //滑动后应该top
        if (dx != 0) {
            clampedX = clampViewPositionHorizontal(clampedX, dx)
            LogApp.d("update  offsetLeftAndRight-->${clampedX - left}")
            ViewCompat.offsetLeftAndRight(this, clampedX - left)
        }
        if (dy != 0) {
            clampedY = clampViewPositionVertical(clampedY, dy)
            LogApp.d("update  offsetTopAndBottom-->${clampedY - top}")
            ViewCompat.offsetTopAndBottom(this, clampedY - top)
        }
    }

    //模仿ViewDragHelper#Callback
    fun clampViewPositionHorizontal(left: Int, dx: Int): Int {
        val widthLimit = rectLimit.width() - width
        val clampW: Int = MathUtils.clamp(left, 0, widthLimit)
        LogApp.d("clampViewPositionHorizontal:$clampW \tleft:$left")
        return clampW
    }

    //模仿ViewDragHelper#Callback
    fun clampViewPositionVertical(top: Int, dy: Int): Int {
        val bottomLimit = rectLimit.height() - height
        val clampH: Int = MathUtils.clamp(top, 0, bottomLimit)
        LogApp.d("clampViewPositionVertical:$top \ttop:$top")
        return clampH
    }


    open fun upCancel(x: Int, y: Int) {
        if (System.currentTimeMillis() - downTime > ViewConfiguration.getTapTimeout()) {
            clickIntercept = true
        }
        reset()
        leaveStartValue = left
        if (left + width / 2 < rectLimit.width() / 2) { //贴左
            leaveEndValue = 0
        } else {//贴右
            leaveEndValue = rectLimit.width() - width
        }
        leaveAnimator.apply {
            addListener(animatorListener)
            start()
        }
    }

    private fun reset() {
        downLeft = 0
        downTop = 0
        downX = 0
        downY = 0
        offX = 0
        offY = 0
    }
}
