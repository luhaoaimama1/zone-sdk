package com.example.mylib_test.activity.statu

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.mylib_test.R
import com.jaeger.library.StatusBarUtil
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_statu_test.*

/**
 * [2017] by Zone
 * fitsystem =true优点坑啊
 * fitsystem=true不好使的时候自己设置状态栏高度站位把
 */

class StatuMainActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_statu_test)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 延伸显示区域到刘海
            val lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.setAttributes(lp);
        }

        status_view.layoutParams.height = getStatusBarHeight(this)

        setFullScreen(false)
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.bt4FullScreen -> setFullScreen(true)
            R.id.bt4NotFullScreen -> setFullScreen(false)
            R.id.btBg -> btBg()
            R.id.demo -> {
                val uri = Uri.parse("http://www.baidu.com"); //浏览器
                val intent2 = Intent(Intent.ACTION_VIEW, uri)
//                intent2.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                intent2.setComponent(ComponentName("com.android.browser", "com.android.browser.BrowserActivity"))
                //com.android.browser.BrowserActivity 不能用startActivityForResult 如果用了的话 无法通过返回键返回到当前页面了
//                startActivityForResult(intent2, 5)
                startActivity(intent2)
            }
            else -> {
            }
        }
    }

    //https://www.dengw.xyz/2019/01/09/%E5%8E%BB%E6%8E%89%E6%A0%87%E9%A2%98%E6%A0%8F%E6%88%96%E7%8A%B6%E6%80%81%E6%A0%8F%E4%B8%8E%E6%B2%89%E6%B5%B8%E5%BC%8F%E7%8A%B6%E6%80%81%E6%A0%8F/
    @TargetApi(Build.VERSION_CODES.M)
    private fun setFullScreen(isFullScreen: Boolean) {
        if (!isFullScreen) {//设置为非全屏
            StatusBarUtil.setColor(this, Color.TRANSPARENT, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val flag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                getWindow().getDecorView().setSystemUiVisibility(flag)
            }
//            StatusBarUtil.setLightMode(this | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        } else {//设置为全屏
            //SYSTEM_UI_FLAG_LIGHT_STATUS_BAR这个 是为了保持黑色模式 退出状态栏
            //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
            //隐藏状态栏，同时Activity会伸展全屏显示。
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.INVISIBLE
            } else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.INVISIBLE
            getWindow().getDecorView().setSystemUiVisibility(flag)
        }
    }

    fun btBg() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800)
        window.setBackgroundDrawable(ColorDrawable(-0x50000000))
    }
}
