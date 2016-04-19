package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class ListView_InnerConfict1 extends ListView{

	private static final String TAG = "ListView_InnerConfict1";
	public ListView_InnerConfict1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ListView_InnerConfict1(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ListView_InnerConfict1(Context context) {
		this(context,null);
	}
	private int downx,downy;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i(TAG, "dispatchTouchEvent ");
//		switchbutton (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN :
//			getParent().requestDisallowInterceptTouchEvent(true);
//			Log.i(TAG, "dispatchTouchEvent ACTION_DOWN");
//			Log.i(TAG, "dispatchTouchEvent  ACTION_MOVE   上层拦截 :false");
//			downx=(int) ev.getX();
//			downy=(int) ev.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			Log.i(TAG, "dispatchTouchEvent ACTION_MOVE");
//			int deltaX=(int)ev.getX()-downx;
//			int deltaY=(int)ev.getY()-downy;
//			if(Math.abs(deltaX)>Math.abs(deltaY)){
//				getParent().requestDisallowInterceptTouchEvent(false);
//				Log.i(TAG, "dispatchTouchEvent  ACTION_MOVE   上层拦截 :true");
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			Log.i(TAG, "dispatchTouchEvent  ACTION_UP ");
//			break;
//
//		default:
//			break;
//		}
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i(TAG, "onInterceptTouchEvent");
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			Log.i(TAG, "onInterceptTouchEvent ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "onInterceptTouchEvent ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			Log.i(TAG, "onInterceptTouchEvent ACTION_UP");
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.i(TAG, "onTouchEvent ");
//		switchbutton (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN :
//			Log.i(TAG, "onTouchEvent ACTION_DOWN");
//			break;
//		case MotionEvent.ACTION_MOVE:
//			Log.i(TAG, "onTouchEvent ACTION_MOVE");
//			break;
//		case MotionEvent.ACTION_UP:
//			Log.i(TAG, "onTouchEvent ACTION_UP");
//			break;
//
//		default:
//			break;
//		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN :
				getParent().requestDisallowInterceptTouchEvent(true);
				Log.i(TAG, "onTouchEvent ACTION_DOWN");
				Log.i(TAG, "onTouchEvent  ACTION_MOVE   上层拦截 :false");
				downx=(int) ev.getX();
				downy=(int) ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				Log.i(TAG, "onTouchEvent ACTION_MOVE");
				int deltaX=(int)ev.getX()-downx;
				int deltaY=(int)ev.getY()-downy;
				if(Math.abs(deltaX)>Math.abs(deltaY)){
					getParent().requestDisallowInterceptTouchEvent(false);
					Log.i(TAG, "onTouchEvent  ACTION_MOVE   上层拦截 :true");
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.i(TAG, "onTouchEvent  ACTION_UP ");
				break;

			default:
				break;
		}
		return super.onTouchEvent(ev);
	}
	

}
