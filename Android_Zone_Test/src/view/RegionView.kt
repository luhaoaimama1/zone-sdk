package view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.zone.lib.utils.view.DrawUtils

class RegionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var path = Path()
    var drawRegionPath = Path()
    var pathInnerCircle = Path()
    var pathArc = Path()
    var drawRegion = Region()
    var layoutRegion = Region()
    var bt = DrawUtils.getStrokePaint(Paint.Style.FILL_AND_STROKE).apply {
        color = Color.BLUE
    }

    var bt2 = DrawUtils.getStrokePaint(Paint.Style.FILL_AND_STROKE).apply {
        color = Color.BLACK
    }

    var bt3 = DrawUtils.getStrokePaint(Paint.Style.FILL_AND_STROKE).apply {
        color = Color.YELLOW
    }

    init {
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            layoutRegion.set(0,0,width,height)
            path.reset()
            pathInnerCircle.reset()
            pathArc.reset()

            val centerX = width * 1f / 2
            val centerY = height* 1f / 2
            val r = width * 1f / 2
            val rHalf = r / 2
            path.addCircle(
                centerX,
                centerY,
                r,
                Path.Direction.CW
            )

            pathInnerCircle.addCircle(
                centerX,
                centerY,
                rHalf,
                Path.Direction.CW
            )


            pathArc.arcTo(centerX - r, centerY - r,
                centerX + r, centerY + r,
                180 + 45f, 90 * 1f,false)
            pathArc.lineTo(centerX,centerY)
            pathArc.close()

            path.op(pathInnerCircle,Path.Op.DIFFERENCE)
            path.op(pathArc,Path.Op.INTERSECT)
            drawRegion.setPath(path, layoutRegion)

            //调试drawRegion的path用
            drawRegionPath.reset()
            drawRegion.getBoundaryPath(drawRegionPath)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (drawRegion.contains(it.x.toInt(), it.y.toInt())) {
                Toast.makeText(context, "点到了", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "没点到~", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, bt)
        //调试drawRegion的path用
        canvas?.drawPath(drawRegionPath, bt3)
    }
}