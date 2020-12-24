package view.corn

import android.content.res.TypedArray
import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mylib_test.R
import com.zone.lib.utils.view.DrawUtils

interface CornerImageViewI {
    fun readTypedArray(typedArray: TypedArray)
    fun initPath()
    fun onDraw(canvas: Canvas)
    fun onBorderDraw(canvas: Canvas)
}

class DefaultCornerImageViewImpl(val view: CornerImageView) : CornerImageViewI {
    override fun readTypedArray(typedArray: TypedArray) {
    }

    override fun initPath() {
    }

    override fun onDraw(canvas: Canvas) {
        view.superOnDraw(canvas)
    }

    override fun onBorderDraw(canvas: Canvas) {
    }
}

class CirclerCornerImageViewImpl(val view: CornerImageView) : CornerImageViewI {

    private class CirclePathInner : Path() {
        var x = 0f
        var y = 0f
        var radius = 0f
        var realRadius = 0f
        override fun addCircle(x: Float, y: Float, radius: Float, dir: Direction) {
            this.x = x
            this.y = y
            this.radius = radius
            super.addCircle(x, y, radius, dir)
        }
    }

    private val circlePath = CirclePathInner()
    private val opPath = Path()
    private val DST_OUT = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    private val paint = DrawUtils.getStrokePaint(Paint.Style.FILL)

    override fun readTypedArray(typedArray: TypedArray) {
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun initPath() {
        val paddingW: Int = view.paddingLeft + view.paddingRight
        val paddingH: Int = view.paddingTop + view.paddingBottom
        val min: Int = Math.min(view.width - paddingW, view.height - paddingH)
        circlePath.reset()
        circlePath.realRadius = min * 1f / 2
        when (view.borderType) {
            CornerImageView.BORDER_TYPE_HALF_CONTENT_EDGE -> {
                circlePath.addCircle(
                        view.width * 1f / 2,
                        view.height * 1f / 2,
                        circlePath.realRadius - view.borderWith / 2,
                        Path.Direction.CW
                )
            }
            CornerImageView.BORDER_TYPE_EXCLUDE_CONTENT -> {
                circlePath.addCircle(
                        view.width * 1f / 2,
                        view.height * 1f / 2,
                        circlePath.realRadius - view.borderWith,
                        Path.Direction.CW
                )
            }
            CornerImageView.BORDER_TYPE_INCLUDE_CONTENT -> {
                circlePath.addCircle(
                        view.width * 1f / 2,
                        view.height * 1f / 2,
                        circlePath.realRadius,
                        Path.Direction.CW
                )
            }
        }

        opPath.reset()
        opPath.addRect(0f, 0f, view.getWidth().toFloat(), view.getHeight().toFloat(), Path.Direction.CW)
        opPath.op(circlePath, Path.Op.DIFFERENCE)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.saveLayer(0f, 0f, view.getWidth().toFloat(), view.getHeight().toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        view.superOnDraw(canvas)
        paint.xfermode = DST_OUT
        canvas.drawPath(opPath, paint)
        paint.xfermode = null
        canvas.restore()
    }

    override fun onBorderDraw(canvas: Canvas) {
        //绘制border
        if (view.borderWith != 0f) {
            canvas.drawCircle(circlePath.x, circlePath.y, circlePath.realRadius - view.borderWith / 2, view.paintBorder)
        }
    }
}

class RounderCornerImageViewImpl(val view: CornerImageView) : CornerImageViewI {

    private var roundCorner = 0f

    private var roundCornerTopLeft = 0f
    private var roundCornerTopRight = 0f
    private var roundCornerBottomLeft = 0f
    private var roundCornerBottomRight = 0f

    private var roundCornerTopLeftX = 0f
    private var roundCornerTopLeftY = 0f
    private var roundCornerTopRightX = 0f
    private var roundCornerTopRightY = 0f
    private var roundCornerBottomLeftX = 0f
    private var roundCornerBottomLeftY = 0f
    private var roundCornerBottomRightX = 0f
    private var roundCornerBottomRightY = 0f

    private val rounderPath = Path()
    private val bouderRounderPath: Path by lazy {
        Path()
    }
    private val opPath = Path()
    private val DST_OUT = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    private val paint = DrawUtils.getStrokePaint(Paint.Style.FILL)
    private val radii = floatArrayOf(
            0F, 0F,
            0F, 0F,
            0F, 0F,
            0F, 0F
    )

    override fun readTypedArray(typedArray: TypedArray) {
        //最低优先级
        roundCorner = typedArray.getDimension(R.styleable.CornerImageView_round_corner, 0f)
        if (roundCorner != 0F) {
            radii.forEachIndexed { index, _ ->
                radii[index] = roundCorner
            }
        }

        //第二优先级
        roundCornerTopLeft = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_left, 0f)
        if (roundCornerTopLeft != 0F) {
            radii[0] = roundCornerTopLeft
            radii[1] = roundCornerTopLeft
        }
        roundCornerTopRight = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_right, 0f)
        if (roundCornerTopRight != 0F) {
            radii[2] = roundCornerTopRight
            radii[3] = roundCornerTopRight
        }

        roundCornerBottomRight = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_right, 0f)
        if (roundCornerBottomRight != 0F) {
            radii[4] = roundCornerBottomRight
            radii[5] = roundCornerBottomRight
        }

        roundCornerBottomLeft = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_left, 0f)
        if (roundCornerBottomLeft != 0F) {
            radii[6] = roundCornerBottomLeft
            radii[7] = roundCornerBottomLeft
        }

        //最高优先级
        roundCornerTopLeftX = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_left_x, 0f)
        if (roundCornerTopLeftX != 0F) {
            radii[0] = roundCornerTopLeftX
        }
        roundCornerTopLeftY = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_left_y, 0f)
        if (roundCornerTopLeftY != 0F) {
            radii[1] = roundCornerTopLeftY
        }
        roundCornerTopRightX = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_right_x, 0f)
        if (roundCornerTopRightX != 0F) {
            radii[2] = roundCornerTopRightX
        }
        roundCornerTopRightY = typedArray.getDimension(R.styleable.CornerImageView_round_corner_top_right_y, 0f)
        if (roundCornerTopRightY != 0F) {
            radii[3] = roundCornerTopRightY
        }

        roundCornerBottomRightX = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_right_x, 0f)
        if (roundCornerBottomRightX != 0F) {
            radii[4] = roundCornerBottomRightX
        }
        roundCornerBottomRightY = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_right_y, 0f)
        if (roundCornerBottomRightY != 0F) {
            radii[5] = roundCornerBottomRightY
        }

        roundCornerBottomLeftX = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_left_x, 0f)
        if (roundCornerBottomLeftX != 0F) {
            radii[6] = roundCornerBottomLeftX
        }
        roundCornerBottomLeftY = typedArray.getDimension(R.styleable.CornerImageView_round_corner_bottom_left_y, 0f)
        if (roundCornerBottomLeftY != 0F) {
            radii[7] = roundCornerBottomLeftY
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun initPath() {
        if (view.borderType != CornerImageView.BORDER_TYPE_HALF_CONTENT_EDGE) {
            throw IllegalStateException("不支持其他类型，原因path偏移的话 圆角不变会导致漏底！")
        }
        val paddingW: Int = view.paddingLeft + view.paddingRight
        val paddingH: Int = view.paddingTop + view.paddingBottom

        val halfBorder = view.borderWith / 2
        val bounderRectF = RectF(
                view.paddingLeft.toFloat() + halfBorder,
                view.paddingTop.toFloat() + halfBorder,
                view.width.toFloat() - paddingW - halfBorder,
                view.height.toFloat() - paddingH - halfBorder
        )

        //计算bouder Path
        bouderRounderPath.reset()
        bouderRounderPath.addRoundRect(bounderRectF, radii, Path.Direction.CW)

        //计算rounderPath
        rounderPath.reset()
        rounderPath.addRoundRect(bounderRectF, radii, Path.Direction.CW)

        //计算op Path
        opPath.reset()
        opPath.addRect(0f, 0f, view.width.toFloat(), view.height.toFloat(), Path.Direction.CW)
        opPath.op(rounderPath, Path.Op.DIFFERENCE)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.saveLayer(0f, 0f, view.getWidth().toFloat(), view.getHeight().toFloat(), paint, Canvas.ALL_SAVE_FLAG)
        view.superOnDraw(canvas)
        paint.xfermode = DST_OUT
        canvas.drawPath(opPath, paint)
        paint.xfermode = null
        canvas.restore()
    }

    override fun onBorderDraw(canvas: Canvas) {
        //绘制border
        if (view.borderWith != 0f) {
            canvas.drawPath(bouderRounderPath, view.paintBorder)
        }
    }
}