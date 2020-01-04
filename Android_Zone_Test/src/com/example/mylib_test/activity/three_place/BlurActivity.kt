package com.example.mylib_test.activity.three_place

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.image.BitmapUtils
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.a_fastblur.*

/**
 *[2018/7/10] by Zone
 */

class BlurActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_fastblur)
    }

    override fun initData() {

        right_bottom.setImageBitmap(BitmapUtils
                .fastblur(this, BitmapFactory
                        .decodeResource(resources, R.drawable.demo), 20))

    }

    override fun setListener() {
        button.setOnClickListener {
            val startMs = System.currentTimeMillis()
            Blurry.with(this@BlurActivity)
                    .radius(25)
                    .sampling(1)
                    .color(Color.argb(66, 0, 255, 255))//Color Filter
                    .async()
                    .capture(findViewById(R.id.right_top))
                    .into(findViewById(R.id.right_top) as ImageView)

            Blurry.with(this@BlurActivity)
                    .radius(10)//模糊半径
                    .sampling(8)//采样 原图像素的 几分之一
                    .async()//会不耗时间？
                    .capture(right_bottom)//把一个控件的图照 下来  background 还src都一样他是截下来的
                    .into(right_bottom as ImageView)//放到另一个控件(ImageView)

            Blurry.with(this@BlurActivity)
                    .radius(25)
                    .sampling(1)
                    .color(Color.argb(66, 255, 255, 0))
                    //            .async()
                    .capture(left_bottom)
                    .into(left_bottom as ImageView)

            Log.d(getString(R.string.app_name), "TIME${(System.currentTimeMillis() - startMs).toString()}ms")
        }


        button.setOnLongClickListener(object : View.OnLongClickListener {
            var blurred = false
            override fun onLongClick(v: View?): Boolean {
                if (blurred) {
                    Blurry.delete(findViewById(R.id.content) as ViewGroup)
                } else {
                    val startMs = System.currentTimeMillis()
                    Blurry.with(this@BlurActivity)
                            .radius(25)
                            .sampling(2)
                            .async()
                            .animate(500)
                            .onto(content as ViewGroup)//这个则是这个父控件里的任何控件(不仅仅是ImageView)都变模糊了
                    Log.d(getString(R.string.app_name),
                            "TIME ${(System.currentTimeMillis() - startMs)} ms")
                }
                blurred = !blurred
                return true
            }

        })
    }
}