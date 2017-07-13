package com.zone.lib.utils.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

/**
 * 引用项目:https://github.com/openproject/LessCode
 * View相关工具类
 */
public final class FindView {

    /**
     * ***********************************************************
     * findViewById的一种更优雅的写法
     * 原理:泛型的类型推断
     * ***********************************************************
     */
    public static <T extends View> T get(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    public static <T extends View> T get(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }

    public static <T extends View> T get(Dialog dialog, int viewId) {
        return (T) dialog.findViewById(viewId);
    }
}
