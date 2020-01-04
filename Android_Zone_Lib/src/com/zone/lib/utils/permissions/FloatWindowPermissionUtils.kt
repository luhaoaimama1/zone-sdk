package com.zone.lib.utils.permissions

import android.app.Activity
import com.zone.lib.utils.permissions.rom.FloatWindowManagerNoDialog


//<uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW"/>
//http://www.manongjc.com/article/4252.html  8.0 dialog 则需要看前面这个链接
object FloatWindowPermissionUtils {

    /**
     * 检查系统通知设置状态
     * 4.4.2以下调用该方法并不会出错,只是全部返回true,默认开启状态
     */
    fun isEnable(activity: Activity): Boolean = FloatWindowManagerNoDialog.checkPermission(activity)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return Settings.canDrawOverlays(activity)
//        } else {
//            return true
//        }

    fun guideToPermissionWindow(activity: Activity) {
        FloatWindowManagerNoDialog.applyPermission(activity)
    }
//    fun guideToPermissionWindow(activity: Activity) {
//        //若没有权限，提示获取.
//        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
//        try {
//            activity.startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            e.printStackTrace()
//            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//            intent.data = Uri.fromParts("package", activity.packageName, null)
//            try {
//                activity.startActivity(intent)
//            } catch (e1: ActivityNotFoundException) {
//                e1.printStackTrace()
//            }
//
//        }
//    }
}
