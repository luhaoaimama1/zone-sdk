package view.testing

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.mylib_test.R
import com.zone.lib.utils.view.DrawUtils

class ShineFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var swipeBt: Bitmap? = null
    private val paint = DrawUtils.getBtPaint()
    private var xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    private val matrixTemp = Matrix()
    private var mValues = FloatArray(9).apply {
        matrixTemp.getValues(this)
    }

    private val backAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
                matrixTemp.setTranslate(0F * width, 0F)
                mValues[Matrix.MTRANS_X] = 0F
                matrixTemp.setValues(mValues)
                postInvalidate()
            }
        })
        duration = 2000
        repeatCount = -1
        addUpdateListener {
            val backProcess = it.animatedValue as Float
            mValues[Matrix.MTRANS_X] = backProcess * width
            matrixTemp.setValues(mValues)
            postInvalidate()
        }
    }

    fun startSwipe(id: Int, isScale: Boolean) {
        post {
            swipeBt = BitmapFactory.decodeResource(resources, id)
            if (isScale) {
                mValues[Matrix.MSCALE_Y] = height * 1f / swipeBt!!.height
                mValues[Matrix.MSCALE_X] = width * 1f / swipeBt!!.width
                matrixTemp.setValues(mValues)
            }
            backAnimator.start()
        }
    }

    fun cancelSwipe() {
        if (backAnimator.isRunning) backAnimator.cancel()
    }

    override fun onDetachedFromWindow() {
        cancelSwipe()
        super.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        swipeBt?.let {
            canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint, Canvas.ALL_SAVE_FLAG)
            super.dispatchDraw(canvas)
            paint.xfermode = xfermode
            canvas?.drawBitmap(it, matrixTemp, paint)
            paint.xfermode = null
            canvas?.restore()
        } ?: super.dispatchDraw(canvas)
    }
}