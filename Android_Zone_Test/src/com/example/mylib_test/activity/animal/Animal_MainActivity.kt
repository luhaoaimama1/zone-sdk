package com.example.mylib_test.activity.animal

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.ClipDrawable
import android.view.View
import android.view.View.OnClickListener
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.utils.image.compress2sample.CompressUtils
import com.zone.lib.utils.image.compress2sample.SampleUtils
import kotlinx.android.synthetic.main.a_animaltest.*

class Animal_MainActivity : BaseFeatureActivity(), OnClickListener {
    private var bt: Bitmap? = null
    override fun setContentView() {
        setContentView(R.layout.a_animaltest)
    }

    override fun initData() {
        bt = BitmapFactory.decodeResource(resources, R.drawable.abcd)
        val drawable = bt_clip_drawable.background as ClipDrawable
        drawable.level = 5000//0-10000
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_big_save -> saveBigBitmap()
            R.id.animal -> startActivity(Intent(this, AniPro::class.java))
            R.id.viewHelperTest -> startActivity(Intent(this, ViewHelperTestActivity::class.java))
            R.id.color -> startActivity(Intent(this, ColorMarixTry::class.java))
            R.id.bt_canvas -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "layer"))
            R.id.bt_Xfermode -> startActivity(Intent(this, XfermodeActivity::class.java))
            R.id.bt_imageBigTest -> startActivity(Intent(this, ImageShowBigActivity::class.java))
            R.id.bt_imageCenter -> startActivity(Intent(this, ImageCenterActivity::class.java))
            R.id.bt_surface -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_surface"))
            R.id.bt_MatrixPre -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_MatrixPre"))
            R.id.bt_MatrixStudy -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_MatrixStudy"))
            R.id.bt_bitmap -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_bitmap"))
            R.id.bt_bitmaptoRound -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_bitmaptoRound"))
            R.id.bt_bitmaptoScale -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_bitmaptoScale"))
            R.id.bt_bitmaptoRorate -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_bitmaptoRorate"))
            R.id.bt_Pixels -> startActivity(Intent(this, PixelsAcitivity::class.java))
            R.id.bt_customAni -> startActivity(Intent(this, CustomAniActivity::class.java))
            R.id.bt_bitmapRecyle -> bitmapRecyleTest()
            R.id.bt_draw -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_draw"))
            R.id.bt_LightingColorFilter -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_LightingColorFilter"))
            R.id.bt_drawText -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_drawText"))
            R.id.bt_drawTextUtils -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_drawTextUtils"))
            R.id.bt_matrixMethod -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_matrixMethod"))
            //遮罩的几种方式
            R.id.bt_clip_shader -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "shader"))
            //path
            R.id.bt_bezier -> startActivity(Intent(this, PathActivity::class.java).putExtra("type", "QQBizierView"))
            R.id.bt_FlexibleBall -> startActivity(Intent(this, PathActivity::class.java).putExtra("type", "FlexibleBall"))
            R.id.bt_glow -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "bt_glow"))
            R.id.bt_damping -> startActivity(Intent(this, DampingActivity::class.java))
            R.id.bt_PathMeasure -> startActivity(Intent(this, PathMeasureActivity::class.java))
            R.id.bt_wave -> startActivity(Intent(this, WaveActivity::class.java))
            R.id.bt_svg -> startActivity(Intent(this, SVGActivity::class.java))
            R.id.bt_spannable -> startActivity(Intent(this, TextViewLinkActivity::class.java))
            else -> {
            }
        }
    }

    private fun saveBigBitmap() {
        val bt = SampleUtils.load(this, R.drawable.abcd).bitmap()
        Thread(Runnable {
            val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            //获得MemoryInfo对象
            val memoryInfo = ActivityManager.MemoryInfo()
            //获得系统可用内存，保存在MemoryInfo对象上
            mActivityManager.getMemoryInfo(memoryInfo)
            val memSize = memoryInfo.availMem
            //                int width = (int) (Math.sqrt(memSize / 4)) - 8000;
            //x的平方*4(rgb888 每个像素 4byte) =可用像素
            val width = Math.sqrt((memSize / 4).toDouble()).toInt() - 90000

            //经过测试  在创建的时候就已经是申请很多内存了
            val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val scaleWidth = width * 1f / bt.width
            val scaleHeight = width * 1f / bt.height
            //用最小的缩放
            val realScale = if (scaleWidth <= scaleHeight) scaleWidth else scaleHeight
            val mar = Matrix()
            canvas.save()
            mar.postTranslate((width - bt.width) * 1f / 2, (width - bt.height) * 1f / 2)
            mar.postScale(realScale, realScale, width * 1f / 2, width * 1f / 2)
            canvas.drawBitmap(bt, mar, null)
            canvas.restore()

            LogApp.d("bitmap 实例:$bitmap")
            val file = FileUtils.getFile(SDCardUtils.getSDCardDir(), "Zone", "abc.png")

            CompressUtils.saveBitmap(file.path, bitmap)
        }).start()
    }

    private fun bitmapRecyleTest() {
        bt?.let {
            LogApp.d("bitmapRecyleTest" + if (it.isRecycled == true) "回收成功" else "回收失败")
            iv_iv.setImageBitmap(it)
            it.recycle()
            LogApp.d("bitmapRecyleTestre  recycle" + if (it.isRecycled == true) "回收成功" else "回收失败")
            System.gc()
            LogApp.d("bitmapRecyleTestrec gc" + if (it.isRecycled == true) "回收成功" else "回收失败")
        }
    }
}
