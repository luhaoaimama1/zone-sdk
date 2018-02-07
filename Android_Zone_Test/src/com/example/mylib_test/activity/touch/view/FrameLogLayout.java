package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.mylib_test.activity.touch.EventPassLogActivity;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class FrameLogLayout extends FrameLayout {

    private EventPassLogActivity activiy;

    public FrameLogLayout(@NonNull Context context) {
        super(context);
    }

    public FrameLogLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setWillNotDraw(false);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        activiy.log(this, "dispatchTouchEvent");
        boolean result = super.dispatchTouchEvent(ev);
        activiy.log(this, "dispatchTouchEvent 结果->:" + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        activiy.log(this, "onInterceptTouchEvent");
        boolean result = super.onInterceptTouchEvent(ev);
        activiy.log(this, "onInterceptTouchEvent 结果->:" + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        activiy.log(this, "onTouchEvent");
        boolean result = super.onTouchEvent(event);
        activiy.log(this, "onTouchEvent 结果->:" + result);
        return result;
    }



    public void setActiviy(EventPassLogActivity activiy) {
        this.activiy = activiy;
    }
}
