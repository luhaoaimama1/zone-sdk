package view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.mylib_test.R
import com.zone.lib.utils.data.convert.DensityUtils

class CircleProgressView : RelativeLayout {

    private var mCirclePaint: Paint

    private var mArcPaint: Paint
    var progress = 50
        get() = field
        set(value) {
            field = value
            postInvalidate()
        }
    var progressStrokeWidth = 50F
        get() = field
        set(value) {
            field = value
            postInvalidate()
        }
    var progressInner = 0
        get() = field
        set(value) {
            field = value
            isMeasure=true
            requestLayout()
        }
    var radius = 50F
        get() = field
        set(value) {
            field = value
            isMeasure=true
            requestLayout()
        }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView, 0, 0)
            try {
                if (typedArray != null) {
                    radius = typedArray.getDimension(R.styleable.CircleProgressView_radius, 50F)
                    progressStrokeWidth = typedArray.getDimension(R.styleable.CircleProgressView_progressStrokeWidth, 10F)
                    progress = typedArray.getInteger(R.styleable.CircleProgressView_progress, 50)
                    progressInner = typedArray.getInteger(R.styleable.CircleProgressView_progressInner, 0)
                }
            } finally {
                typedArray.recycle()
            }
        }
        mCirclePaint = Paint()
        mCirclePaint.isAntiAlias = true
        mCirclePaint.color = resources.getColor(
            android.R.color.holo_blue_bright)

        mArcPaint = Paint()
        mArcPaint.isAntiAlias = true
        mArcPaint.color = resources.getColor(
            android.R.color.holo_blue_bright)
        mArcPaint.style = Style.STROKE
        setWillNotDraw(false)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setWidth2Height()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val width = getRadiusPx() * 2 + getProgressStrokeWidthPx()
//        setMeasuredDimension(width, width)
    }

    var isMeasure = true
    fun setWidth2Height() {
        if (isMeasure) {
            isMeasure = false
            layoutParams.width = getRadiusPx() * 2 + getProgressStrokeWidthPx()
            layoutParams.height = getRadiusPx() * 2 + getProgressStrokeWidthPx()
            requestLayout()
        }
    }

    fun getProgressStrokeWidthPx() = DensityUtils.dp2px(context, progressStrokeWidth)

    fun getRadiusPx() = DensityUtils.dp2px(context, radius)

    private var mArcRectF: RectF = RectF()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val strokePx = getProgressStrokeWidthPx()
        mArcPaint.strokeWidth = strokePx.toFloat()
        val storkeHalfPx = strokePx.toFloat() / 2
        mArcRectF.set(storkeHalfPx, storkeHalfPx, width - storkeHalfPx, height - storkeHalfPx)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCirclePaint.alpha = progressInner * 255 / 100
        canvas.drawCircle(height.toFloat() / 2, height.toFloat() / 2, getRadiusPx().toFloat(), mCirclePaint)
        canvas.drawArc(mArcRectF, 0F, progress.toFloat() * 360 / 100, false, mArcPaint)
    }

}
