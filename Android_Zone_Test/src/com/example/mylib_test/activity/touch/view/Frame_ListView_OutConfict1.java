package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class Frame_ListView_OutConfict1 extends ListView{

	private static final String TAG = "Frame_ListView_OutConfict1";
	public Frame_ListView_OutConfict1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Frame_ListView_OutConfict1(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public Frame_ListView_OutConfict1(Context context) {
		this(context,null);
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
	private int downx,downy;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean use=false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			downx=(int) ev.getX();
			downy=(int) ev.getY();
			Log.i(TAG, "onTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "onTouchEvent ACTION_MOVE");
			int deltaX=(int)ev.getX()-downx;
			int deltaY=(int)ev.getY()-downy;
			if(Math.abs(deltaX)<Math.abs(deltaY)){
				Log.i(TAG, "ACTION_MOVE use   true \tX:"+ev.getX()+"  \ty:"+ev.getY());
				use=true;
			}else{
				use=false;
				Log.i(TAG, " ACTION_MOVE use   false");
			}
			break;
		case MotionEvent.ACTION_UP:
			break;

		default:
			break;
		}
		if(use){
			return super.onTouchEvent(ev);	
		}else
			return false;
		
	}
	

}
