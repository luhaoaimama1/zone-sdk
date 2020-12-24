package view

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.AttributeSet
import android.widget.FrameLayout
import com.zone.lib.utils.view.DrawUtils

//// TODO zone:   所有view 变灰色  
class GrayFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val btPaint = DrawUtils.getBtPaint().apply {
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0F)
        colorFilter = ColorMatrixColorFilter(colorMatrix)
    }


    override fun onDraw(canvas: Canvas?) {
        canvas?.saveLayer(null, btPaint, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.saveLayer(null, btPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
    }
}
