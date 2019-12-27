package view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mylib_test.LogApp
import com.zone.lib.utils.view.DrawUtils
import com.zone.lib.utils.view.ViewTreeObserver
import java.lang.IllegalStateException

//弹窗的时候加载这个Rl,而不是一直存在
/**
 *  范例：
 *  //这个代表 TipConstraintLayout展示的层级
    TipConstraintLayout.attachToRootView(container)?.let { tipConstraintLayout ->
        //圆角尺寸
        val dp2px = DensityUtils.dp2px(container.context, 6F).toFloat()

        //用于上方展示的indicateItem
        val topView = LayoutInflater.from(container.context).inflate(R.layout.tips_drawer_top, tipConstraintLayout, false)
        val indicateItemTop = TipConstraintLayout.IndicateItem(topView, TipConstraintLayout.IndicateViewGravity.TopCenter)

        //用于下方展示的indicateItem
        val bottomView = LayoutInflater.from(container.context).inflate(R.layout.tips_drawer_bottom, tipConstraintLayout, false)
        val indicateItemBottom = TipConstraintLayout.IndicateItem(bottomView, TipConstraintLayout.IndicateViewGravity.BottomCenter)

        //indicateItemBottom 设置替换的indicateItemTop。indicateItem不能完全显示 则用替换的依次直到能完全显示的  就展示
        indicateItemBottom.setReplaceIndicateItems(indicateItemTop,...)

        //表示 计算高亮view的宽高信息  通过setGlobalLayout的view去监听
        tipConstraintLayout.setGlobalLayout(container)

        //高亮点击
        tipConstraintLayout.setHighLightOnClickListener(View.OnClickListener {
        if (it == highLightView) {
            tipConstraintLayout.dismiss()
            }
        })

        //设置indicateItem的展示区域  用于计算 indicateItem是否完全可以展示的
        tipConstraintLayout.indicateShowArea = container.rootView.findViewById(android.R.id.content)
        //添加高亮
        tipConstraintLayout.addTipItem(highLightView, dp2px, indicateItemBottom)
    }
 */
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

    var isInterceptBackClick = true

    private val itemList: ArrayList<TipItem> by lazy {
        ArrayList<TipItem>()
    }

    var indicateShowArea: View? = null

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

    var mHighLightOnClickListener: OnClickListener? = null
    fun setHighLightOnClickListener(onClickListener: OnClickListener) {
        mHighLightOnClickListener = onClickListener
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
                mHighLightOnClickListener?.let { listener ->
                    replaceView.isClickable = true
                    replaceView.setOnClickListener {
                        listener.onClick(tipsItem.view)
                    }
                }
                addIndicateView(tipsItem, replaceView, tipsItem.indicateItems)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    private fun addIndicateView(tipsItem: TipItem, replaceView: View, indicateItems: Array<out IndicateItem>) {
        indicateItems.forEach { indicateItem ->
            //测量下 不然没有measuredHeight
            val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            var indicateItemRealShow: IndicateItem? = null
            indicateItem.mReplaceIndicateItems?.let {
                indicateItemRealShow = findCanShowIndicate(indicateItem, tipsItem, it)
            }

            indicateItemRealShow?.let {
                realGenerateLp(it, lp, replaceView)
                if (it.indicateView.parent != null) {
                    it.indicateView.layoutParams = lp
                } else {
                    addView(it.indicateView, lp)
                }
            } ?: LogApp.e("indicate is can't show !")
        }
    }

    /**
     * 当indicateItem显示不全的话。从replaceIndicateItems找到一个可以显示全的去显示
     */
    private fun findCanShowIndicate(indicateItem: IndicateItem,
                                    tipsItem: TipItem,
                                    replaceIndicateItems: Array<out IndicateItem>): IndicateItem? {
        val indicateItems = ArrayList<IndicateItem>()
        indicateItems.add(indicateItem)
        indicateItems.addAll(replaceIndicateItems)

        var result: IndicateItem? = null
        val indicateShowAreaRect = Rect()
        indicateShowArea?.getGlobalVisibleRect(indicateShowAreaRect)
        loop@ for (index in 0 until indicateItems.size) {
            indicateItems[index].indicateView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            when (indicateItems[index].relativteGravity) {
                IndicateViewGravity.BottomCenter,
                IndicateViewGravity.BottomLeft,
                IndicateViewGravity.BottomRight -> {
                    val showBottom = if (indicateShowArea != null) indicateShowAreaRect.bottom
                    else height
                    if ((showBottom - tipsItem.rect.bottom) - indicateItems[index].indicateView.measuredHeight > 0) {
                        result = indicateItems[index]
                        break@loop
                    }
                }
                IndicateViewGravity.TopCenter,
                IndicateViewGravity.TopLeft,
                IndicateViewGravity.TopRight -> {
                    val showTop = if (indicateShowArea != null) indicateShowAreaRect.top
                    else 0
                    if (tipsItem.rect.top - showTop - indicateItem.indicateView.measuredHeight > 0) {
                        result = indicateItems[index]
                        break@loop
                    }
                }

                //todo zone
                IndicateViewGravity.LeftCenter,
                IndicateViewGravity.LeftTop,
                IndicateViewGravity.LeftBottom -> {
                    result = indicateItems[index]
                    break@loop
                }
                //todo zone
                IndicateViewGravity.RightCenter,
                IndicateViewGravity.RightTop,
                IndicateViewGravity.RightBottom -> {
                    result = indicateItems[index]
                    break@loop
                }
                else -> {
                }
            }
        }
        return result
    }

    private fun realGenerateLp(indicateItem: IndicateItem, lp: LayoutParams, replaceView: View) {
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
        LogApp.d("addTipItemReal")
        itemList.add(TipItem(view, round, indicateItems))
        if (viewOnGlobalLayout == null) throw IllegalStateException("Please use  method:setGlobalLayout ")

        ViewTreeObserver.addOnGlobalLayoutListenerDelete(viewOnGlobalLayout) {
            measureItems()
        }
        setFocusableInTouchMode(true);
        requestFocus();
        setOnKeyListener(object :OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                return event?.run {
                    var result = false
                    if (isInterceptBackClick && keyCode == KeyEvent.KEYCODE_BACK && action == KeyEvent.ACTION_UP) {
                        result = true
                    }
                    result
                }?:false
            }
        });
    }

    fun addTipItem(@IdRes resId: Int, round: Float = 0F, vararg indicateItems: IndicateItem) {
        LogApp.d("rootView.findViewById<View>(resId):${rootView.findViewById<View>(resId)}")
        rootView.findViewById<View>(resId)?.let {
            addTipItemReal(it, round, indicateItems)
        } ?: throw  IllegalStateException("view is null")
    }

    fun addTipItem(vararg @IdRes resIds: Int, round: Float = 0F) {
        resIds.forEach {
            rootView.findViewById<View>(it)?.let {
                addTipItem(it, round)
            }
        }
    }

    fun dismiss() {
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

    data class IndicateItem(val indicateView: View, val relativteGravity: IndicateViewGravity = IndicateViewGravity.None) {
        internal var mReplaceIndicateItems: Array<out IndicateItem>? = null
        fun setReplaceIndicateItems(vararg replaceIndicateItems: IndicateItem) {
            this.mReplaceIndicateItems = replaceIndicateItems
        }
    }

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