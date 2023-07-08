package view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.zone.lib.utils.view.DrawUtils

class AdjustmentConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val btPaint = DrawUtils.getBtPaint()
    private val linePaint = DrawUtils.getStrokePaint(Paint.Style.STROKE, 5f).apply {
        color = Color.RED
    }
    private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    //    private var bt: Bitmap? = null
//    private var btCanvas: Canvas? = null
    init {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setWillNotDraw(false)
        setLayerPaint(btPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
//        val isNotSafe = w == 0 && h == 0
//        if (!isNotSafe) {
//            bt = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
//            btCanvas = Canvas(bt!!)
//        }
    }

    override fun draw(canvas: Canvas?) {
        canvas ?: return
//        bt?.let {
//            canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), btPaint, Canvas.ALL_SAVE_FLAG)
        val saveCount = canvas.save()
//            canvas.setBitmap()

        btPaint.xfermode = porterDuffXfermode
        super.draw(canvas)
//            (parent as ViewGroup).setLayerPaint()
//            canvas.drawBitmap(bt,left,right,btPaint)
        btPaint.xfermode = null
//            canvas.restore(saveCount)
        canvas.restoreToCount(saveCount)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), linePaint)
//        } ?: super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }
}