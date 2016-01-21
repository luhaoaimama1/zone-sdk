package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class VeclocityTrackerView extends View {
	private VelocityTracker mVelocityTracker;// 生命变量

	public VeclocityTrackerView(Context context) {
		super(context);
	}

	public VeclocityTrackerView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public VeclocityTrackerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();// 获得VelocityTracker类实例
		}
		mVelocityTracker.addMovement(event);// 将事件加入到VelocityTracker类实例中
		final int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
//			 (float) 4.01: velocityTraker-0.30938187 		即1毫秒　为单位　　速度为：-0.30938187px/1ms
//				1000: 	velocityTraker:-309.38187  		 	即1秒　为单位　　速度为：-309px/1ms
			// 1000 provides pixels per second
			mVelocityTracker.computeCurrentVelocity(1, (float) 4.01); // 设置maxVelocity值为0.1时，速率大于0.01时，显示的速率都是0.01,速率小于0.01时，显示正常
			Log.i("1, (float) 4.01", "velocityTraker" + mVelocityTracker.getXVelocity());
			mVelocityTracker.computeCurrentVelocity(1000); // 设置units的值为1000，意思为一秒时间内运动了多少个像素
			Log.i("1000", "velocityTraker:" + mVelocityTracker.getXVelocity());
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (null != mVelocityTracker) {
				mVelocityTracker.clear();
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;

		default:
			break;
		}
		return true;
	}
}
