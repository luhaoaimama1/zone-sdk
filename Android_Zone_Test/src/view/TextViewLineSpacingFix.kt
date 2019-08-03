package view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView

/**
 *[2019/1/10] by Zone
 *  参考：https://blog.csdn.net/etwge/article/details/72818859
 *  解决用了 lineSpacingMultiplier 最后一行 有空行的问题
 */
class TextViewLineSpacingFix : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    val mRect = Rect()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (lineCount > 0) {
            val baseline = getLineBounds(lineCount - 1, mRect)
            var result = 0
            if (measuredHeight == layout.height + paddingBottom + paddingTop) {
                result = mRect.bottom - (baseline + layout.paint.fontMetricsInt.descent);
            }
            if (result > 0) setMeasuredDimension(measuredWidth, measuredHeight - result)
        }
    }
}