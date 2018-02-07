package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mylib_test.activity.touch.EventPassLogActivity;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class TextLogView extends View {
    private EventPassLogActivity activiy;

    public TextLogView(Context context) {
        super(context);
    }

    public TextLogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        activiy.log(this,"dispatchTouchEvent 结果->:"+true);
//        return true;
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            getParent().requestDisallowInterceptTouchEvent(true);//父亲不拦截事件
            activiy.log(this,"父亲不拦截事件");

        }
        if(ev.getAction()==MotionEvent.ACTION_MOVE){
            getParent().requestDisallowInterceptTouchEvent(false);//父亲拦截事件
            activiy.log(this,"父亲拦截事件");
        }


        activiy.log(this,"dispatchTouchEvent");
        boolean result=super.dispatchTouchEvent(ev);
        activiy.log(this,"dispatchTouchEvent 结果->:"+result);
        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        activiy.log(this,"onTouchEvent 结果->:"+false);
//        return false;


        activiy.log(this,"onTouchEvent");
//        if(event.getAction()==MotionEvent.ACTION_MOVE){
//            boolean result=false;
//            activiy.log(this,"onTouchEvent  move 结果->:"+false);
//            return result;
//        }
        boolean result=super.onTouchEvent(event);
        activiy.log(this,"onTouchEvent 结果->:"+result);
        return result;
    }

    public void setActiviy(EventPassLogActivity activiy){
        this.activiy=activiy;
    }
}
