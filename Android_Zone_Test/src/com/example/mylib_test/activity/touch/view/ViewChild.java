package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by fuzhipeng on 2016/11/22.
 */

public class ViewChild extends View {
    private static final String TAG = "eventPass";


    public ViewChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                Log.i(TAG, "ViewChild->dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ViewChild->dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ViewChild->dispatchTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
//        return true;
        return super.dispatchTouchEvent(ev);
    }
    private float downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downX =event.getX();
                Log.i(TAG, "ViewChild->onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                if(Math.abs(event.getX()-downX)>200){
//                    Log.i(TAG, "ViewChild- >200!!! 请求拦截");
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }
//                else{
//                    Log.i(TAG, "ViewChild- <200!!! 父亲不拦截");
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
                Log.i(TAG, "ViewChild->onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ViewChild->onTouchEvent ACTION_UP");
                break;

            default:
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
