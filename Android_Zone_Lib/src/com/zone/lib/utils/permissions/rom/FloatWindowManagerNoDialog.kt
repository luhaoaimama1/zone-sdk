/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.zone.lib.utils.permissions.rom

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log

import androidx.appcompat.app.AlertDialog

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-10-17
 * copy from :https://github.com/zhaozepeng/FloatWindowPermission
 * checkPermission
 * applyPermission
 */

object FloatWindowManagerNoDialog : FloatWindowManager() {

    public override fun showConfirmDialog(context: Context, result: FloatWindowManager.OnConfirmResult) {
        result.confirmResult(true)
    }

    public override fun showConfirmDialog(context: Context, message: String, result: FloatWindowManager.OnConfirmResult) {
//        result.confirmResult(true)
    }
}
