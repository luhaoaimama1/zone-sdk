package view


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.utils.view.DrawUtils

class ImageTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        const val LEFT = 0
        const val TOP = 1
        const val RIGHT = 2
        const val Bottom = 3

    }

    @IntDef(LEFT, TOP, RIGHT, Bottom)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class DrawableIndex

    class InsetDrawableInner : InsetDrawable {
        private val mTmpRect = Rect()
        var insetLeft = 0
        var insetTop = 0
        var insetRight = 0
        var insetBottom = 0
        val mPaint = DrawUtils.getBtPaint()
        var drawableBackgroudColor = 0
            set(value) {
                mPaint.color = value
                field = value
            }

        var mDrawableInner: Drawable

        constructor(drawable: Drawable, inset: Int) : this(drawable, inset, inset, inset, inset)

        constructor(
                drawable: Drawable,
                insetLeft: Int,
                insetTop: Int,
                insetRight: Int,
                insetBottom: Int
        ) : super(drawable, 0) {
            mDrawableInner = drawable
            this.insetLeft = insetLeft
            this.insetTop = insetTop
            this.insetRight = insetRight
            this.insetBottom = insetBottom
        }

        override fun getPadding(padding: Rect): Boolean {
            val superPadding = super.getPadding(padding)
            return superPadding || (insetLeft or insetTop or insetRight or insetBottom) != 0
        }

        override fun getIntrinsicWidth(): Int {
            val childWidth = mDrawableInner.intrinsicWidth
            return if (childWidth < 0) {
                -1
            } else {
                childWidth + insetLeft + insetRight
            }
        }

        override fun getIntrinsicHeight(): Int {
            val childHeight = mDrawableInner.intrinsicHeight
            return if (childHeight < 0) {
                -1
            } else {
                childHeight + insetTop + insetBottom
            }
        }

        override fun getOpacity(): Int {
            val opacity = mDrawableInner.opacity
            return if (opacity == PixelFormat.OPAQUE && (insetLeft or insetTop or insetRight or insetBottom) > 0) {
                PixelFormat.TRANSLUCENT
            } else opacity
        }

        override fun onBoundsChange(bounds: Rect) {
            mTmpRect.set(bounds)
            mTmpRect.right = bounds.width() - insetLeft - insetRight
            mTmpRect.bottom = bounds.height() - insetTop - insetBottom
            mTmpRect.offset(insetLeft, insetTop)
            LogApp.d("bounds before:${bounds.toString()} \tmTmpRect:${mTmpRect}" +
                    "\t [insetLeft:$insetLeft,insetTop:$insetTop,insetRight:$insetRight,insetBottom:$insetBottom]")
            super.onBoundsChange(mTmpRect)
            LogApp.d("bounds after:${mDrawableInner.bounds.toString()}" +
                    "\t [insetLeft:$insetLeft,insetTop:$insetTop,insetRight:$insetRight,insetBottom:$insetBottom]")
        }


        override fun draw(canvas: Canvas) {
            if (mPaint.color != Color.TRANSPARENT) {
                canvas.drawRect(bounds, mPaint)
            }
            super.draw(canvas)
        }

        override fun setDrawable(dr: Drawable) {
            mDrawableInner = dr
            super.setDrawable(dr)
        }

        override fun getDrawable(): Drawable {
            return mDrawableInner
        }
    }

    var drawableMargins = arrayOf(0f, 0f, 0f, 0f)
    var drawableHeight = arrayOf(0f, 0f, 0f, 0f)
    var drawableWidth = arrayOf(0f, 0f, 0f, 0f)
    var arrays = arrayOf(0, 0, 0, 0)

    //如果生成了就存在缓存中 下次就不会生成了
    var insetDrawableInnerArrays = arrayOfNulls<InsetDrawableInner>(4)
    var drawableBackgroudColor: Int = Color.TRANSPARENT

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView, 0, 0)
            if (typedArray != null) {
                try {
                    drawableMargins[0] = typedArray.getDimension(R.styleable.ImageTextView_leftDrawableMargin, 0f)
                    drawableHeight[0] = typedArray.getDimension(R.styleable.ImageTextView_leftDrawableHeight, 0f)
                    drawableWidth[0] = typedArray.getDimension(R.styleable.ImageTextView_leftDrawableWidth, 0f)

                    drawableMargins[1] = typedArray.getDimension(R.styleable.ImageTextView_topDrawableMargin, 0f)
                    drawableHeight[1] = typedArray.getDimension(R.styleable.ImageTextView_topDrawableHeight, 0f)
                    drawableWidth[1] = typedArray.getDimension(R.styleable.ImageTextView_topDrawableWidth, 0f)


                    drawableMargins[2] = typedArray.getDimension(R.styleable.ImageTextView_rightDrawableMargin, 0f)
                    drawableHeight[2] = typedArray.getDimension(R.styleable.ImageTextView_rightDrawableHeight, 0f)
                    drawableWidth[2] = typedArray.getDimension(R.styleable.ImageTextView_rightDrawableWidth, 0f)


                    drawableMargins[3] = typedArray.getDimension(R.styleable.ImageTextView_bottomDrawableMargin, 0f)
                    drawableHeight[3] = typedArray.getDimension(R.styleable.ImageTextView_bottomDrawableHeight, 0f)
                    drawableWidth[3] = typedArray.getDimension(R.styleable.ImageTextView_bottomDrawableWidth, 0f)

                    drawableBackgroudColor = typedArray.getColor(R.styleable.ImageTextView_drawableBackgroudColor, Color.TRANSPARENT)
                } finally {
                    typedArray.recycle()
                }
            }
        }

        updateCompoundDrawables(compoundDrawables)
    }

    /**
     * @param drawable 等于-1 的时候代表置空
     */
    fun setDrawable(
            @DrawableIndex index: Int,
            @DrawableRes drawable: Int,
            width: Float,
            height: Float,
            margin: Float
    ) {
        drawableMargins[index] = margin
        drawableHeight[index] = height
        drawableWidth[index] = width

        val drawables = compoundDrawables
        if (drawable == -1) {
            drawables[index] = null
        } else {
            drawables[index] = resources.getDrawable(drawable).mutate()
        }
        //tips:这里需要全部更新一遍不能单独更新,单独更新会导致另一边宽度不准确
        updateCompoundDrawables(drawables)
    }

    override fun getCompoundDrawables(): Array<Drawable?> {
        val compoundDrawables = super.getCompoundDrawables()
        for (index in compoundDrawables.indices) {
            val drawable = compoundDrawables[index]
            //确保不是自己生成的壳子 因为没有set去更改inset所以需要重新生成
            if (drawable is InsetDrawableInner) {
                compoundDrawables[index] = drawable.mDrawableInner
            }
        }
        return compoundDrawables
    }

    private fun updateCompoundDrawables(drawables: Array<Drawable?>) {
        for (index in drawables.indices) {
            updateCompoundDrawableIndex(drawables, index)
        }
        //tips:如果drawable不设置setBounds setCompoundDrawables无效
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3])
    }

    private fun updateCompoundDrawableIndex(drawables: Array<Drawable?>, index: Int) {
        val drawable = drawables[index]
        if (drawable != null) {
            val drawableMarginTemp = drawableMargins[index]
            val drawableWidthTemp = if (drawableWidth[index] == 0f) drawable.intrinsicWidth else drawableWidth[index].toInt()
            val drawableHeightTemp = if (drawableHeight[index] == 0f) drawable.intrinsicHeight else drawableHeight[index].toInt()

            for (i in 0 until 4) {
                arrays[i] = 0
            }

            val oppositeIndex = (index + 2) % 4
            arrays[oppositeIndex] = drawableMarginTemp.toInt()

            val insetDrawableInnerTemp = insetDrawableInnerArrays[index]
            val insetDrawableInner = if (insetDrawableInnerTemp != null &&
                    insetDrawableInnerTemp.drawable == drawable) {
                LogApp.d("复用index：${index}")
                //如果drawable 重复的话 可以复用 不然就重新创建
                insetDrawableInnerTemp.insetLeft = arrays[0]
                insetDrawableInnerTemp.insetTop = arrays[1]
                insetDrawableInnerTemp.insetRight = arrays[2]
                insetDrawableInnerTemp.insetBottom = arrays[3]
                insetDrawableInnerTemp
            } else {
                val insetDrawableInner = InsetDrawableInner(drawable, arrays[0], arrays[1], arrays[2], arrays[3])
                insetDrawableInner.drawableBackgroudColor = drawableBackgroudColor
                LogApp.d("创建index：${index}")
                insetDrawableInnerArrays[index] = insetDrawableInner
                insetDrawableInner
            }

            var widthReal = drawableWidthTemp
            var heightReal = drawableHeightTemp
            when (index) {
                LEFT, RIGHT -> {
                    widthReal += drawableMarginTemp.toInt()
                }
                TOP, Bottom -> {
                    heightReal += drawableMarginTemp.toInt()
                }
                else -> {
                }
            }
            insetDrawableInner.setBounds(0, 0, widthReal, heightReal)
            drawables[index] = insetDrawableInner
        }
    }
}