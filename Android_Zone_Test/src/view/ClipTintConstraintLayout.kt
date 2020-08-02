//package view
///*
// * 可以把 ClipViewGroup dispatchDraw展示的内容 裁剪成 圆角矩形
// */
//class ClipTintConstraintLayout : TintConstraintLayout {
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        if (attrs != null) {
//            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipTintConstraintLayout, 0, 0)
//            try {
//                if (typedArray != null) {
//                    rounder = typedArray.getDimension(R.styleable.ClipTintConstraintLayout_radius, 0F)
//                }
//            } finally {
//                typedArray.recycle()
//            }
//        }
//    }
//
//    var rounder = 0F
//        set(value) {
//            field = value
//            invalidate()
//        }
//
//    private val rectRoundPath = Path()
//
//    override fun dispatchDraw(canvas: Canvas?) {
//
//        rectRoundPath.reset()
//
//        rectRoundPath.moveTo(rounder, 0F)
//
//        rectRoundPath.lineTo(width - rounder, 0F)
//        rectRoundPath.quadTo(width.toFloat(), 0F, width.toFloat(), rounder)
//
//        rectRoundPath.lineTo(width.toFloat(), height.toFloat() - rounder)
//        rectRoundPath.quadTo(width.toFloat(), height.toFloat(), width.toFloat() - rounder, height.toFloat())
//
//        rectRoundPath.lineTo(rounder, height.toFloat())
//        rectRoundPath.quadTo(0F, height.toFloat(), 0F, height.toFloat() - rounder)
//
//        rectRoundPath.lineTo(0F, rounder)
//        rectRoundPath.quadTo(0F, 0F, rounder, 0F)
//        rectRoundPath.close()
//
//        canvas?.save()
//        canvas?.clipPath(rectRoundPath)
//        super.dispatchDraw(canvas)
//        canvas?.restore()
//    }
//}