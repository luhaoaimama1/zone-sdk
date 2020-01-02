package com.example.mylib_test.activity.frag_viewpager_expand

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.viewpager.widget.ViewPager

import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView

class ViewPagerIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    private var chatDrawable: Drawable? = null

    private var rects: Array<IntArray>? = null
    private var state = -1
    private var startpoint = 0
    private var kuand = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (chatDrawable != null) {
            chatDrawable!!.setBounds(startpoint, 0, startpoint + kuand, height)
            chatDrawable!!.draw(canvas)
        }
    }


    fun setViewPagerWithItemView(vp: ViewPager, vararg views: View) {
        if (vp.adapter!!.count != views.size)
            throw IllegalStateException("长度不一样!")


        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (state == -1) {
                    //绘制 rects[position]
                    //第一此的setposition
                    vp.postDelayed({
                        rects = Array(views.size) { IntArray(2) }
                        for (i in rects!!.indices)
                        //postDelay 因为有些机器需要延时才能得到正确的值 不然值特别奇怪
                        //而且延时200都不一定获得正确的值... 如果有人知道更好的办法请告诉我~
                            views[i].getLocationOnScreen(rects!![i])
                        startpoint = rects!![position][0]
                        kuand = views[position].width
                        invalidate()
                    }, 500)
                } else {
                    if (position + 1 < views.size) {//防止数组越界
                        startpoint = (rects!![position][0] + (rects!![position + 1][0] - rects!![position][0]) * positionOffset).toInt()
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
                this@ViewPagerIndicator.state = state
            }
        })
    }

    fun setDrawRes(resId: Int) {
        chatDrawable = resources.getDrawable(resId)
    }

    private fun log(str: String) {
        Log.i("MyImageView2", str)
    }

}
