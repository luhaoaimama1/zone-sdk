package com.zone.lib.utils.permissions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat

object NotificationPermissionUtils {

    /**
     * 检查系统通知设置状态
     * 4.4.2以下调用该方法并不会出错,只是全部返回true,默认开启状态
     */
    fun isEnable(activity: Activity): Boolean = NotificationManagerCompat.from(activity).areNotificationsEnabled()

    /**
     * 打开通知系统设置页
     */
    fun guideToPermissionWindow(activity: Activity) {
        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //android 5.0-7.0
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", activity.packageName)
            intent.putExtra("app_uid", activity.applicationInfo.uid)
        } else {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", activity.packageName, null)
        }
        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", activity.packageName, null)
            try {
                activity.startActivity(intent)
            } catch (e1: ActivityNotFoundException) {
                e1.printStackTrace()
            }

        }
    }
}
