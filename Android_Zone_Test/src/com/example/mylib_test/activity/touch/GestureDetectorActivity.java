package com.example.mylib_test.activity.touch;

import com.example.mylib_test.R;

import and.log.ToastUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class GestureDetectorActivity extends Activity {
	protected static final String DEBUG_TAG = "GestureDetectorActivity";
	private GestureDetectorCompat mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_gesturedetector);


		mGestureDetector = new GestureDetectorCompat(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						// 单击事件
						Log.d(DEBUG_TAG, "onSingleTapUp: " + e.toString());
						showMessage("单击事件");
						return super.onSingleTapUp(e);
					}

					@Override
					public boolean onFling(MotionEvent event1,
							MotionEvent event2, float velocityX, float velocityY) {
						// 快速滑动
						Log.d(DEBUG_TAG, "onFling: " + event1.toString()
								+ event2.toString());
						showMessage("快速滑动");
						return true;
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						// 滑动
						showMessage("滑动");
						return super.onScroll(e1, e2, distanceX, distanceY);
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// 长嗯
						showMessage("onLongPress");
						Log.d(DEBUG_TAG, "onLongPress: ");
						super.onLongPress(e);
					}

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						// 双击
						showMessage("onDoubleTap");
						Log.d(DEBUG_TAG, "onDoubleTap: ");
						return super.onDoubleTap(e);
					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						//这才是click 点击
						Log.d(DEBUG_TAG, "onSingleTapConfirmed: ");
						return super.onSingleTapConfirmed(e);
					}

					@Override
					public boolean onDoubleTapEvent(MotionEvent e) {
						Log.d(DEBUG_TAG, "onDoubleTapEvent: ");
						return super.onDoubleTapEvent(e);
					}

					@Override
					public void onShowPress(MotionEvent e) {
						Log.d(DEBUG_TAG, "onShowPress: ");
						super.onShowPress(e);
					}

					@Override
					public boolean onDown(MotionEvent e) {
						Log.d(DEBUG_TAG, "onDown: ");
						return super.onDown(e);
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	private void showMessage(String str){
		ToastUtils.showLong(this,str);
	}

}
