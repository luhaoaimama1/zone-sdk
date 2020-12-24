package com.example.mylib_test.activity.frag_viewpager_expand

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager

class ViewPagerIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    private var chatDrawable: Drawable? = null

    private var rects: Array<IntArray>? = null
    private var startpoint = 0
    private var kuand = 0
    private val bounds: Rect by lazy {
        Rect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        chatDrawable?.let {
            bounds.set(startpoint, 0, startpoint + kuand, height)
            fixedWithCallback?.invoke(bounds)
            it.bounds = bounds
            it.draw(canvas)
        }
    }

    var fixedWithCallback: ((bounds: Rect) -> Unit)? = null

    var initComplete = false

    private fun init(position: Int) {
        val viewsTemp = views ?: return
        //绘制 rects[position] 第一此的setposition
        val rectsTemp = Array(viewsTemp.size) { IntArray(2) }.apply {
            rects = this
        }
        for (i in rectsTemp.indices)
            viewsTemp[i].getLocationOnScreen(rectsTemp[i])
        startpoint = rectsTemp[position][0]
        kuand = viewsTemp[position].width
        invalidate()
        initComplete = true

    }

    var vp: ViewPager? = null
    var views: List<View>? = null

    fun setViewPagerWithItemView(vp: ViewPager, vararg views: View) {
        initComplete = false
        this.vp = vp
        this.views = views.toList()
        if (vp.adapter?.count ?: 0 != views.size)
            throw IllegalStateException("长度不一样!")

        if (views.isNotEmpty()) {
            this.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    this@ViewPagerIndicator.viewTreeObserver.removeOnPreDrawListener(this)
                    return this@ViewPagerIndicator.vp?.let {
                        init(it.currentItem)
                        false
                    } ?: true
                }
            })

            vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (initComplete) {
                        val rectsTemp = rects ?: return
                        if (position + 1 < views.size) {//防止数组越界
                            startpoint = (rectsTemp[position][0] + (rectsTemp[position + 1][0] - rectsTemp[position][0]) * positionOffset).toInt()
                            kuand = (views[position].width + (views[position + 1].width - views[position].width) * positionOffset).toInt()
                            invalidate()
                        }
                    }
                    log("onPageScrolled:" + position +
                            "\t positionOffset:" + positionOffset + "\t positionOffsetPixels:" + positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    log("onPageSelected:$position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    log("state:$state")
                }
            })
        }
    }

    fun setDrawRes(resId: Int) {
        chatDrawable = resources.getDrawable(resId)
    }

    private fun log(str: String) {
        Log.i("MyImageView2", str)
    }

}
