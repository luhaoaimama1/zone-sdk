package view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.zone.lib.utils.view.DrawUtils

class MaskFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val btPaint = DrawUtils.getBtPaint()
    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var bt: Bitmap? = null
    private var btCanvas: Canvas? = null

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val isNotSafe = w == 0 && h == 0
        if (!isNotSafe) {
            bt = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).apply {
                btCanvas = Canvas(this)
            }
        }
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        canvas ?: return false
        val indexOfChild = indexOfChild(child)
        val btTemp = bt
        if (indexOfChild == 1 && btTemp != null) {
            canvas.save()
            val drawChild = super.drawChild(btCanvas, child, drawingTime)
            btPaint.xfermode = porterDuffXfermode
            canvas.drawBitmap(btTemp, 0F, 0F, btPaint)
            btPaint.xfermode = null
            canvas.restore()
            return drawChild
        } else {
            return super.drawChild(canvas, child, drawingTime)
        }
    }
}