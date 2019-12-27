package view

import android.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.zone.lib.LogZSDK
import com.zone.lib.utils.view.DrawUtils
import com.zone.lib.utils.view.ViewTreeObserver
import java.lang.IllegalStateException

//弹窗的时候加载这个Rl,而不是一直存在
class TipConstraintLayout : ConstraintLayout {
    private val contentViewRect = Rect()
    private var paint: Paint
    private val paintLayer1: Paint
    private val paintLayer2: Paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        @JvmStatic
        fun attachToRootView(view: View): TipConstraintLayout? {
            view.rootView?.let {
                if (it is ViewGroup) {
                    val tclView = TipConstraintLayout(it.context)
                    it.addView(tclView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    return tclView
                }
            }
            return null
        }

    }

    private val itemList: ArrayList<TipItem> by lazy {
        ArrayList<TipItem>()
    }

    init {
        //模糊效果
        setWillNotDraw(false)
        paint = DrawUtils.getStrokePaint(Paint.Style.FILL, 10F)
        //模糊效果
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
//        paint.maskFilter= BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
        paintLayer1 = DrawUtils.getBtPaint().apply {

        }
        paintLayer2 = DrawUtils.getBtPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }
    }

    private fun measureItems() {
        //   val contentView =rootView.findViewById<View>(android.R.id.content)
        this.getGlobalVisibleRect(contentViewRect)
        itemList.forEach { tipsItem ->
            tipsItem.view.getGlobalVisibleRect(tipsItem.rect)
            tipsItem.rect.offset(0, -contentViewRect.top) //状态栏的问题
            tipsItem.toRectF()

            if (tipsItem.indicateItems.isNotEmpty()) {
                addReplaceView(tipsItem)
                val replaceView = tipsItem.replaceView!!
                replaceView.isClickable=true
                replaceView.setOnClickListener {
                    tipsItem.view.performClick()
                }
                addIndicateView(tipsItem, replaceView)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    private fun addIndicateView(tipsItem: TipItem, replaceView: View) {
        tipsItem.indicateItems.forEach { indicateItem ->
            val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            when (indicateItem.relativteGravity) {
                IndicateViewGravity.BottomCenter -> {
                    lp.endToEnd = replaceView.id
                    lp.startToStart = replaceView.id
                    lp.topToBottom = replaceView.id
                }
                IndicateViewGravity.BottomLeft -> {
                    lp.startToStart = replaceView.id
                    lp.topToBottom = replaceView.id
                }
                IndicateViewGravity.BottomRight -> {
                    lp.endToEnd = replaceView.id
                    lp.topToBottom = replaceView.id
                }

                IndicateViewGravity.TopCenter -> {
                    lp.bottomToTop = replaceView.id
                    lp.endToEnd = replaceView.id
                    lp.startToStart = replaceView.id
                }
                IndicateViewGravity.TopLeft -> {
                    lp.bottomToTop = replaceView.id
                    lp.startToStart = replaceView.id
                }
                IndicateViewGravity.TopRight -> {
                    lp.bottomToTop = replaceView.id
                    lp.endToEnd = replaceView.id
                }

                IndicateViewGravity.LeftCenter -> {
                    lp.bottomToBottom = replaceView.id
                    lp.endToStart = replaceView.id
                    lp.topToTop = replaceView.id
                }
                IndicateViewGravity.LeftTop -> {
                    lp.endToStart = replaceView.id
                    lp.topToTop = replaceView.id
                }
                IndicateViewGravity.LeftBottom -> {
                    lp.bottomToBottom = replaceView.id
                    lp.endToStart = replaceView.id
                }

                IndicateViewGravity.RightCenter -> {
                    lp.bottomToBottom = replaceView.id
                    lp.startToEnd = replaceView.id
                    lp.topToTop = replaceView.id
                }
                IndicateViewGravity.RightTop -> {
                    lp.startToEnd = replaceView.id
                    lp.topToTop = replaceView.id
                }
                IndicateViewGravity.RightBottom -> {
                    lp.bottomToBottom = replaceView.id
                    lp.startToEnd = replaceView.id
                }
                else -> {
                }
            }

            if (indicateItem.indicateView.parent != null) {
                indicateItem.indicateView.layoutParams = lp
            } else {
                addView(indicateItem.indicateView, lp)
            }
        }
    }

    private fun TipConstraintLayout.addReplaceView(tipsItem: TipItem) {
        val replaceViewLp = LayoutParams(tipsItem.rect.width(), tipsItem.rect.height())
        replaceViewLp.topToTop = 0
        replaceViewLp.leftToLeft = 0
        replaceViewLp.leftMargin = tipsItem.rect.left
        replaceViewLp.topMargin = tipsItem.rect.top
        if (tipsItem.replaceView == null) {
            val replaceView = View(context).apply {
                id = View.generateViewId()
            }
            tipsItem.replaceView = replaceView
            addView(replaceView, replaceViewLp)
        } else {
            tipsItem.replaceView?.layoutParams = replaceViewLp
        }
    }

    fun addTipItem(view: View, round: Float = 0F, vararg indicateItems: IndicateItem) {
        addTipItemReal(view, round, indicateItems)
    }
    private var viewOnGlobalLayout: View? = null

    fun setGlobalLayout(view: View) {
        viewOnGlobalLayout = view
    }
    private fun addTipItemReal(view: View, round: Float, indicateItems: Array<out IndicateItem>) {
        LogZSDK.d("addTipItemReal")
        itemList.add(TipItem(view, round, indicateItems))
        if (viewOnGlobalLayout == null) throw IllegalStateException("Please use  method:setGlobalLayout ")

        ViewTreeObserver.addOnGlobalLayoutListenerDelete(viewOnGlobalLayout) {
            measureItems()
        }
    }

    fun addTipItem(@IdRes resId: Int, round: Float = 0F, vararg indicateItems: IndicateItem) {
        LogZSDK.d("rootView.findViewById<View>(resId):${rootView.findViewById<View>(resId)}")
        rootView.findViewById<View>(resId)?.let {
            addTipItemReal(it, round, indicateItems)
        }?:throw  IllegalStateException("view is null")
    }

    fun addTipItem(vararg @IdRes resIds: Int, round: Float = 0F) {
        resIds.forEach {
            rootView.findViewById<View>(it)?.let {
                addTipItem(it, round)
            }
        }
    }

    fun dismiss(){
        val parentView = parent
        if (parentView != null && parentView is ViewGroup) {
            parentView.removeView(this)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val restoreCount = canvas.saveLayerAlpha(0f, 0f, width.toFloat(), height.toFloat(), 127,
                    Canvas.ALL_SAVE_FLAG)
            canvas.drawColor(Color.BLACK)
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paintLayer2,
                    Canvas.ALL_SAVE_FLAG)
            itemList.forEach {
                //模糊效果
//                canvas.drawOval(it.rectf, paint)
                canvas.drawRoundRect(it.rectf, it.round, it.round, paint)
            }
            canvas.restoreToCount(restoreCount)
        }
    }

    data class IndicateItem(val indicateView: View, val relativteGravity: IndicateViewGravity = IndicateViewGravity.None)

    private class TipItem(val view: View, val round: Float = 0F, val indicateItems: Array<out IndicateItem>) {
        val rect = Rect()
        val rectf = RectF()
        var replaceView: View? = null
        fun toRectF() {
            rectf.set(rect)
        }
    }

    enum class IndicateViewGravity {
        LeftTop, LeftBottom, LeftCenter,
        TopLeft, TopRight, TopCenter,
        RightTop, RightBottom, RightCenter,
        BottomLeft, BottomRight, BottomCenter,
        None
    }
}