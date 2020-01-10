package com.zone.lib.utils.activity_fragment_ui

import android.content.Context
import android.widget.Toast

/**
 * Toast统一管理类
 *
 * //todo zone  统一处理异常  需要有个异常管理类。 例如 json转换错误应该显示什么呢？当然数据应该弄到反馈里不然也修复不了
 *      统一过滤？类似okhttp的拦截器？
 *      可以子线程中使用
 *      https://www.jianshu.com/p/d6c9a485c061   某个view为基点的 gravity
 *      自定义view 可以 有动画么？
 */
object ToastUtils {
    private var showToast = true

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    fun showShort(context: Context, message: CharSequence) {
        if (showToast)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    fun showShort(context: Context, message: Int) {
        if (showToast)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    fun showLong(context: Context, message: CharSequence) {
        if (showToast)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    fun showLong(context: Context, message: Int) {
        if (showToast)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun show(context: Context, message: CharSequence, duration: Int) {
        if (showToast)
            Toast.makeText(context, message, duration).show()
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun show(context: Context, message: Int, duration: Int) {
        if (showToast)
            Toast.makeText(context, message, duration).show()
    }



    fun openToast() {
        showToast = true
    }

    fun closeToast() {
        showToast = false
    }
}
