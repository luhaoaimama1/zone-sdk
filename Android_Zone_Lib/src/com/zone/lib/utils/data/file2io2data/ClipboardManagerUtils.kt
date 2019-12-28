package com.zone.lib.utils.data.file2io2data

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.TextUtils

//https://blog.csdn.net/Anthonybuer/article/details/85002203
object ClipboardManagerUtils {
    fun getClipBoardText(activity: Activity): String {
        val clipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return clipboardManager.getClipboardText(activity) ?: ""
    }

    /**
     * 兼容 android Q 的剪切板
     */
    fun ClipboardManager.getClipboardText(context: Context): String? {
        if (hasPrimaryClip()) {
            val clip = primaryClip
            if (clip != null && clip.itemCount > 0) {
                val clipboardText = clip.getItemAt(0).coerceToText(context)
                if (clipboardText != null)
                    return clipboardText.toString()
            }
        }
        return null
    }

    /**
     * 放置内容发到剪切板
     */
    fun setClipeBoardContent(context: Context, content: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val primaryClip = ClipData.newPlainText("Label", content)//纯文本内容
        clipboardManager.primaryClip = primaryClip
    }
}