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
    override fun setContentView() {
        setContentView(R.layout.a_animaltest)
    }

    override fun initData() {
        val drawable = bt_clip_drawable.background as ClipDrawable
        drawable.level = 5000//0-10000
        sfl.startSwipe(R.drawable.header_time_reward_yellow,true)
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_drawRounder -> startActivity(Intent(this, RounderViewActivity::class.java))
            R.id.btMatteView -> startActivity(Intent(this, MatteViewActivity::class.java))
            R.id.animal -> startActivity(Intent(this, AniPro::class.java))
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
            R.id.btLrcView -> startActivity(Intent(this, CanvasTest::class.java).putExtra("type", "LrcView"))
            R.id.bt_Pixels -> startActivity(Intent(this, PixelsAcitivity::class.java))
            R.id.bt_customAni -> startActivity(Intent(this, CustomAniActivity::class.java))
            R.id.bt_cgr -> startActivity(Intent(this, ChoreographerStudyActivity::class.java))
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
            R.id.bt_interview -> startActivity(Intent(this, InterViewActivity::class.java))
            R.id.bt_spannable -> startActivity(Intent(this, TextViewLinkActivity::class.java))
            else -> {
            }
        }
    }
}
