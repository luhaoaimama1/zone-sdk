package com.example.mylib_test.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewOverlay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UIDebug {
    public static void debug(Application application){
        enableUIDebugActivity(application);
    }


    private static void enableUIDebugActivity(Application application) {

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof AppCompatActivity) {
                    ((AppCompatActivity) activity).getSupportFragmentManager()
                            .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
                                @Override
                                public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                                    super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                                    ViewOverlay overlay = v.getOverlay();
                                    final FragmentTipsDrawable drawable = new FragmentTipsDrawable(f.getClass().getSimpleName(), 0, false);
                                    overlay.add(drawable);

                                    v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                                        @Override
                                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                            //because  ViewOverlay no update inner Drawable bounds。so myself setBounds and invalidate
                                            drawable.setBounds(left, top, right, bottom);
                                            drawable.invalidateSelf();
                                        }
                                    });
                                }
                            }, true);

                    FragmentTipsDrawable drawable = new FragmentTipsDrawable(activity.getClass().getSimpleName(),
                            getStatusBarHeight(activity), true);
                    try {
                        activity.findViewById(android.R.id.content)
                                .getRootView()
                                .getOverlay()
                                .add(drawable);
                    } catch (Exception ignored)  {}

                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    /**
     * 获取状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    private static class FragmentTipsDrawable extends ColorDrawable {

        private final String fragmentName;
        private final String textSign;
        private final float textLen, signTextLen, baseLineY;
        private final TextPaint textPaint;
        private final Paint bgPaint, circlePaint, signCirlcePaint;
        private final static int DRAW_HEIGHT = 70, CIRCLE_RADIUS = 25;
        private final int topMargin;
        private final boolean isActivity;

        private FragmentTipsDrawable(String fragmentName, int topMargin, Boolean isActivity) {
            super(Color.TRANSPARENT);
            this.isActivity = isActivity;
            this.fragmentName = fragmentName;
            this.topMargin = topMargin;

            bgPaint = new Paint();
            bgPaint.setColor(Color.parseColor("#88888888"));

            int textColor = Color.parseColor("#343434");
            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(textColor);
            textPaint.setTextSize(24);
            Typeface boldFont = Typeface.create("Zone加粗", Typeface.BOLD);
            textPaint.setTypeface(boldFont);
            textLen = textPaint.measureText(fragmentName);

            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setColor(Color.parseColor("#07b900"));
            circlePaint.setAlpha(180);

            signCirlcePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            signCirlcePaint.setColor(textColor);
            signCirlcePaint.setTextSize(30);
            signCirlcePaint.setTypeface(boldFont);
            Paint.FontMetrics fontMetrics = signCirlcePaint.getFontMetrics();
            baseLineY = DRAW_HEIGHT * 1F / 2 - (fontMetrics.top + fontMetrics.bottom) / 2;
            textSign = isActivity ? "A" : "F";
            signTextLen = signCirlcePaint.measureText(textSign);
        }


        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (!isActivity) {
                canvas.save();
                canvas.translate(getBounds().width() / 2, 0);
            }

            canvas.drawCircle(CIRCLE_RADIUS, topMargin + DRAW_HEIGHT / 2, CIRCLE_RADIUS, circlePaint);
            canvas.drawText(textSign, CIRCLE_RADIUS - signTextLen / 2, topMargin + baseLineY, signCirlcePaint);

            int circle_shift = 2 * CIRCLE_RADIUS + 10;

            canvas.drawRect(circle_shift, topMargin, circle_shift + textLen + 20, DRAW_HEIGHT + topMargin, bgPaint);
            canvas.drawText(fragmentName, 10 + circle_shift, 40 + topMargin, textPaint);

            if (!isActivity) {
                canvas.restore();
            }
        }
    }
}
