package com.example.mylib_test.activity.study.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.convert.GsonUtils


/**
 *[2021/07/07] by Zone
 * https://developer.android.com/guide/topics/display-cutout
 * //zone todo: 2021/7/7  event rawX,与X的关系
 * //zone todo: 2021/7/7  window 与screen的关系
 * //zone todo: 2021/7/7  windowInsetsCompat 什么东西
 * //zone todo: 2021/7/7  怎么代码设置displaycutout
 */
class DisplayCutoutActivity : BaseFeatureActivity() {
    lateinit var imageView: ImageView
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun setContentView() {
        LogApp.d("setContentView")
         imageView = ImageView(this)
        setContentView(imageView)
        imageView.setImageResource(R.drawable.abcd)
        imageView.scaleType=ImageView.ScaleType.FIT_XY
//        imageView.setOnClickListener {
//            ViewCompat.setOnApplyWindowInsetsListener(it) { view, windowInsetsCompat -> //This is where you get DisplayCutoutCompat
//                val displayCutout = windowInsetsCompat.displayCutout
//                windowInsetsCompat
//            }
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView()) { view, windowInsetsCompat -> //This is where you get DisplayCutoutCompat
//            val displayCutout = windowInsetsCompat.displayCutout
//            windowInsetsCompat
//        }
//
//        if (imageView.isAttachedToWindow()) {
//            // We're already attached, just request as normal
//            ViewCompat.requestApplyInsets(imageView)
//
//        } else {
//            // We're not attached to the hierarchy, add a listener to request when we are
//            imageView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
//                override fun onViewAttachedToWindow(v: View) {
//                    v.removeOnAttachStateChangeListener(this)
//                    ViewCompat.requestApplyInsets(v)
//                }
//
//                override fun onViewDetachedFromWindow(v: View) = Unit
//            })
//        }

        imageView.setOnClickListener {
            showFullScreenCompate()
        }
    }

    private fun setDisplayShortEdges() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val attributes: WindowManager.LayoutParams = window.attributes
            attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = attributes
//            window.decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            hideSystemUI();
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }
    override fun onPostResume() {
        super.onPostResume()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                // Set the content to appear under the system bars so that the
//                View.SYSTEM_UI_FLAG_IMMERSIVE

                SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LogApp.d("onAttachedToWindow")
        showFullScreenCompate()
    }

    private fun showFullScreenCompate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.decorView.rootWindowInsets?.displayCutout?.apply {
                this.boundingRects //zone todo: 2021/7/7 给h5
                 LogApp.d("给h5d:${GsonUtils.toJson(this.boundingRects)}")
                 setDisplayShortEdges()
            } ?: hideSystemUI()
        }
    }

    override fun onStart() {
        super.onStart()
        LogApp.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        LogApp.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        LogApp.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogApp.d("onStop")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogApp.d("onDetachedFromWindow")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogApp.d("onDestroy")
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}