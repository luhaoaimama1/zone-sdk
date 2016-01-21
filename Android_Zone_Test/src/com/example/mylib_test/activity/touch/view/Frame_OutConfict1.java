package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class Frame_OutConfict1 extends FrameLayout{
	private static final String TAG = "Frame_OutConfict1";
	public Frame_OutConfict1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Frame_OutConfict1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Frame_OutConfict1(Context context) {
		super(context);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			Log.i(TAG, "dispatchTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "dispatchTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			Log.i(TAG, "onInterceptTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "onInterceptTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN :
			Log.i(TAG, "onTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "onTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

}
