package interview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.sqrt

class InterView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     *  |______
     *  40  62
     */
    private val checkPath = Path()
    private val checkDstPath = Path()
    private val checkPathMeasure = PathMeasure()
    var checkFirstLength = Utils.dp2px(context, 40F)
    var checkSecondLength = Utils.dp2px(context, 62F)
    var checkCircleRadius = Utils.dp2px(context, 80F)

    /*
     *  | 30
     */
    private val edgePath = Path()
    private val edgePathMeasure = PathMeasure()
    private val edgeLineDstPath = Path()
    private val edgeLineStartEnd = FloatArray(2) { 0F }
    private var edgeDrawRorate = 0F
    var edgeLineLength = Utils.dp2px(context, 30F)
    var edgeLineCount = 8

    val paint = Utils.getPaint(Paint.Style.STROKE).apply {
        color = Color.WHITE
        strokeWidth = Utils.dp2px(context, 14F)
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    init {
        checkPath.moveTo(0F, -checkFirstLength)
        checkPath.lineTo(0F, 0F)
        checkPath.lineTo(checkSecondLength, 0F)
        checkPathMeasure.setPath(checkPath, false)

        edgePath.moveTo(checkCircleRadius, 0F)
        edgePath.lineTo(checkCircleRadius + edgeLineLength, 0F)
        edgePathMeasure.setPath(edgePath, false)
    }

    fun startCustomAnimation() {
        animator.start()
    }

    fun stopCustomAnimation() {
        animator.end()
    }

    interface MotionInterpolator {
        /**
         * @param progress 时间比 0F-1F,
         * @return 对应生长的进度 范围0F-1F
         */
        fun checkPath(progress: Float): Float

        /**
         * @param progress 时间比 0F-1F
         * @param edgeLineStartEnd 二维数组,【0】代表start 【1】代表end
         */
        fun edgeLinePath(progress: Float, edgeLineStartEnd: FloatArray)

        /**
         * @param progress 时间比 0F-1F
         * @return 旋转的角度 范围0F-360F
         */
        fun edgeLineRoate(progress: Float): Float
    }

    var motionInterpolator: MotionInterpolator = object : MotionInterpolator {
        //勾勾速度曲线，动画从0s开始，1s结束
        override fun checkPath(progress: Float): Float {
            return progress
        }

        //0.14开始 1结束，中间没说 我猜测0.86/2 0.43一个过程 那么第二个过程0.14+0.43=0.57
        override fun edgeLinePath(progress: Float, edgeLineStartEnd: FloatArray) {
            if (progress <= 0.14F) {
                edgeLineStartEnd[0] = 0F
                edgeLineStartEnd[1] = 0F
            } else if (progress <= 0.57F) { //(0.14,0.57] map (0,1]
                edgeLineStartEnd[0] = 0F
                edgeLineStartEnd[1] = MathUtils.linear(progress, 0.14F, 0.57F, 0F, 1F, MathUtils.Linear.OverMax)
            } else { //[0.58,1.100] map [0,1]
                edgeLineStartEnd[0] = MathUtils.linear(progress, 0.58F, 1F, 0F, 1F, MathUtils.Linear.OverMax)
                edgeLineStartEnd[1] = 1F
            }
        }

        //0-1 旋转94度
        override fun edgeLineRoate(progress: Float): Float {
            return MathUtils.linear(progress, 0F, 1F, 0F, 94F, MathUtils.Linear.OverMax)
        }
    }

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatCount = -1
        interpolator = LinearInterpolator()

        val checkPathMeasureLength: Float by lazy {
            checkPathMeasure.length
        }
        val edgePathMeasureLength: Float by lazy {
            edgePathMeasure.length
        }

        addUpdateListener {
            val progress = it.animatedValue as Float
            //根据进度计算checkPath的片段
            checkDstPath.reset()
            val fl = checkPathMeasureLength * motionInterpolator.checkPath(progress)
            checkPathMeasure.getSegment(0F, fl, checkDstPath, true)

            //根据进度计算edgeline的片段
            edgeLineDstPath.reset()
            motionInterpolator.edgeLinePath(progress, edgeLineStartEnd)
            val start = edgePathMeasureLength * edgeLineStartEnd[0]
            val end = edgePathMeasureLength * edgeLineStartEnd[1]
            edgePathMeasure.getSegment(start, end, edgeLineDstPath, true)

            //根据进度计算edgeline 整体旋转的角度
            edgeDrawRorate = motionInterpolator.edgeLineRoate(progress)
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //居中
        canvas.translate(measuredWidth * 1F / 2, measuredHeight * 1F / 2)

        drawCheckPath(canvas)
        drawRorateWithEdgeLine(canvas)
    }

    private fun drawRorateWithEdgeLine(canvas: Canvas) {
        canvas.save()
        canvas.rotate(edgeDrawRorate)
        drawEdgeLine(canvas)
        canvas.restore()
    }

    private fun drawEdgeLine(canvas: Canvas) {
        val degreesInteval = 360F / edgeLineCount
        for (i in 0 until edgeLineCount) {
            canvas.save()
            canvas.rotate(degreesInteval * i)
            canvas.drawPath(edgeLineDstPath, paint)
            canvas.restore()
        }
    }

    private fun drawCheckPath(canvas: Canvas) {
        canvas.save()
        canvas.translate(0F, checkFirstLength * 1F / sqrt(2.0).toFloat())
        canvas.rotate(-45F)
        canvas.drawPath(checkDstPath, paint)
        canvas.restore()
    }
}