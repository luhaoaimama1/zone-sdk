package com.example.mylib_test.activity.custom_view.gui.feedback;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;


public class FocusUtil {
    private final static Long ANIM_DURATION = 300L;

    public static void focus(View view, int color, int strokeWidth, int strokeColor, float radius, float scale) {
        view.animate()
//                .alpha(0.5f)
                .scaleX(scale)
                .scaleY(scale)
                .setDuration(ANIM_DURATION);

        // 可指定圆角尺寸，颜色值，边框尺寸及颜色
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
//        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
        view.setAlpha(0.7f);
        view.setForeground(gradientDrawable);
    }

    public static void normal(View view) {
        view.animate()
                .alpha(1f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(ANIM_DURATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setForeground(null);
        }
    }
}
