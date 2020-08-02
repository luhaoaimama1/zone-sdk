package interview

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue

object Utils {
    fun getPaint(style: Paint.Style): Paint {
        return Paint().apply {
            isAntiAlias = true
            setStyle(style)
        }
    }

    fun dp2px(context: Context, dpVal: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics)
    }
}