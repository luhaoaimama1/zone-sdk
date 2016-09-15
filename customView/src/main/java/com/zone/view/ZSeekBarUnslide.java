package com.zone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class ZSeekBarUnslide extends SeekBar {

    public ZSeekBarUnslide(Context context) {
        super(context);
    }


    public ZSeekBarUnslide(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }


    public ZSeekBarUnslide(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * onTouchEvent 是在 SeekBar 继承的抽象类 AbsSeekBar 里 你可以看下他们的继承关系
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    // 原来是要将TouchEvent传递下去的,我们不让它传递下去就行了
    // return super.onTouchEvent(event);
        return false;
    }


}