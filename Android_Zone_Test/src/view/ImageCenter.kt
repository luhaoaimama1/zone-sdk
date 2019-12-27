package view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View

//弹窗的时候加载这个Rl,而不是一直存在
class ImageCenter : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var bitmap: Bitmap? = null
        set(value) {
            field = value
            postInvalidate()
        }

    val mar = Matrix()
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null && bitmap != null) {
            val bitmapTemp = bitmap!!
            val scaleWidth = width * 1f / bitmapTemp.getWidth()
            val scaleHeight = height * 1f / bitmapTemp.getHeight()
            //用最小的缩放
            val realScale = if (scaleWidth <= scaleHeight) scaleWidth else scaleHeight
            canvas.save()
            //ok
//            mar.postScale(realScale, realScale)
//            mar.postTranslate((width - bitmapTemp.width * realScale) * 1f / 2, (height - bitmapTemp.height * realScale) * 1f / 2)
//            canvas.drawBitmap(bitmapTemp, mar, null)

            //ok2   上下都好使
            mar.postTranslate((width - bitmapTemp.width) * 1f / 2, (height - bitmapTemp.height) * 1f / 2)
            mar.postScale(realScale, realScale, width * 1f / 2, height * 1f / 2)
            canvas.drawBitmap(bitmapTemp, mar, null)
            canvas.restore()
        }
    }
}