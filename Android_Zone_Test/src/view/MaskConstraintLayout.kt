package view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.zone.lib.utils.view.DrawUtils

class MaskConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val btPaint = DrawUtils.getBtPaint()
//    var porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    var porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//    var porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        btPaint.xfermode = null
        canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), btPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas?.restore()
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        canvas ?: return false
        val childReal=child!!
        val indexOfChild = indexOfChild(child)
        if (indexOfChild == 1) {
            btPaint.xfermode = porterDuffXfermode
            canvas.saveLayer(0f,0f, width.toFloat(), height.toFloat(), btPaint, Canvas.ALL_SAVE_FLAG)
            val drawChild = super.drawChild(canvas, child, drawingTime)
            canvas.restore()
            return drawChild
        } else {
            return super.drawChild(canvas, child, drawingTime)
        }
    }
}