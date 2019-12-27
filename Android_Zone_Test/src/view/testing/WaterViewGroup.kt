package view.testing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.mylib_test.LogApp

public class WaterViewGroup :FrameLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        LogApp.d("onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        LogApp.d("onLayout")
    }

    var rounder = 150F
    private val rectRoundPath = Path()

    override fun dispatchDraw(canvas: Canvas?) {

        rectRoundPath.reset()

        rectRoundPath.moveTo(rounder, 0F)

        rectRoundPath.lineTo(width - rounder, 0F)
        rectRoundPath.quadTo(width.toFloat(), 0F, width.toFloat(), rounder)

        rectRoundPath.lineTo(width.toFloat(), height.toFloat() - rounder)
        rectRoundPath.quadTo(width.toFloat(), height.toFloat(), width.toFloat() - rounder, height.toFloat())

        rectRoundPath.lineTo(rounder, height.toFloat())
        rectRoundPath.quadTo(0F, height.toFloat(), 0F, height.toFloat() - rounder)

        rectRoundPath.lineTo(0F, rounder)
        rectRoundPath.quadTo(0F, 0F, rounder, 0F)
        rectRoundPath.close()

        canvas?.save()
        canvas?.clipPath(rectRoundPath)
        super.dispatchDraw(canvas)
        canvas?.restore()
    }

}