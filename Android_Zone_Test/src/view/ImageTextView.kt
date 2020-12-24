package view


import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import com.example.mylib_test.R

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
        var mDrawableInner: Drawable? = null

        constructor(drawable: Drawable?, inset: Int) : super(drawable, inset) {
            mDrawableInner = drawable
        }

        @RequiresApi(Build.VERSION_CODES.O)
        constructor(drawable: Drawable?, inset: Float) : super(drawable, inset)
        constructor(
                drawable: Drawable?,
                insetLeft: Int,
                insetTop: Int,
                insetRight: Int,
                insetBottom: Int
        ) : super(drawable, insetLeft, insetTop, insetRight, insetBottom) {
            mDrawableInner = drawable
        }

        @RequiresApi(Build.VERSION_CODES.O)
        constructor(
                drawable: Drawable?,
                insetLeftFraction: Float,
                insetTopFraction: Float,
                insetRightFraction: Float,
                insetBottomFraction: Float
        ) : super(drawable, insetLeftFraction, insetTopFraction, insetRightFraction, insetBottomFraction) {
            mDrawableInner = drawable
        }
    }

    var drawableMargins = arrayOf(0f, 0f, 0f, 0f)
    var drawableHeight = arrayOf(0f, 0f, 0f, 0f)
    var drawableWidth = arrayOf(0f, 0f, 0f, 0f)
    var arrays = arrayOf(0, 0, 0, 0)

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
                } finally {
                    typedArray.recycle()
                }
            }
        }

        updateCompoundDrawables(compoundDrawables)
    }

    //用户？setCompoundDrawables

    //zone todo: 2020/12/24  从新set 怎么保证不嵌套inset？ 生成的存起来 判断是否是同一个？
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
        drawables[index] = resources.getDrawable(drawable)
        //这里需要全部更新一遍不能单独更新,单独更新会导致另一边宽度不准确
        updateCompoundDrawables(drawables)
    }

    override fun getCompoundDrawables(): Array<Drawable?> {
        val compoundDrawables = super.getCompoundDrawables()
        for (index in compoundDrawables.indices) {
            val drawable = compoundDrawables[index]
            //确保不是自己生成的壳子 因为没有set去更改inset所以需要重新生成
            if(drawable is InsetDrawableInner){
                compoundDrawables[index] = drawable.mDrawableInner
            }
        }
        return compoundDrawables
    }

    private fun updateCompoundDrawables(drawables:Array<Drawable?>) {
        for (index in drawables.indices) {
            updateCompoundDrawableIndex(drawables, index)
        }
        //tips:如果drawable不设置setBounds setCompoundDrawables无效
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3])
    }

    private fun updateCompoundDrawableIndex(drawables: Array<Drawable?>, index: Int) {
        val drawable =  drawables[index]
        if (drawable != null) {
            val rightDrawableMarginTemp = drawableMargins[index]
            val rightDrawableWidthTemp = if (drawableWidth[index] == 0f) drawable.intrinsicWidth else drawableWidth[index].toInt()
            val rightDrawableHeightTemp = if (drawableHeight[index] == 0f) drawable.intrinsicHeight else drawableHeight[index].toInt()

            for (i in 0 until 4) {
                arrays[i] = 0
            }

            val oppositeIndex = (index + 2) % 4
            arrays[oppositeIndex] = rightDrawableMarginTemp.toInt()

            val insetDrawableInner = InsetDrawableInner(drawable, arrays[0], arrays[1], arrays[2], arrays[3])
            var widthReal = rightDrawableWidthTemp
            var heightReal = rightDrawableHeightTemp
            when (index) {
                LEFT, RIGHT -> {
                    widthReal += rightDrawableMarginTemp.toInt()
                }
                TOP, Bottom -> {
                    heightReal += rightDrawableMarginTemp.toInt()
                }
                else -> {
                }
            }
            insetDrawableInner.setBounds(0, 0, widthReal, heightReal)
            drawables[index] = insetDrawableInner
        }
    }
}