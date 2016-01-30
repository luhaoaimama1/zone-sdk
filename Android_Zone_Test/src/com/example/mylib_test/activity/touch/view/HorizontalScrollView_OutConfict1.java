package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;


public class HorizontalScrollView_OutConfict1 extends LinearLayout{
	private static final String TAG = "HorizontalScrollView_OutConfict1";
	private Scroller mScroller;
	private GestureDetector mGestureDetector;
	private Context context;
	
	

	public HorizontalScrollView_OutConfict1(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
		this.context=context;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i(TAG, " dispatchTouchEvent");
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			resetGesture();
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
	
	private int downx,downy;
	private boolean first;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i(TAG, " onInterceptTouchEvent");
		boolean intercepter=false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN :
			intercepter=false;
			downx=(int) ev.getX();
			downy=(int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX=(int)ev.getX()-downx;
			int deltaY=(int)ev.getY()-downy;
			if(Math.abs(deltaX)>Math.abs(deltaY)+50&&Math.abs(deltaX)<Math.abs(deltaY)+100){
				Log.i(TAG, "ACTION_MOVE intercepter   true \tX:"+ev.getX()+"  \ty:"+ev.getY());
				intercepter=true;
			}else if(Math.abs(deltaX)>Math.abs(deltaY)+100){
				//TODO  此方法不会再走  因为一旦返回true  onInterceptTouchEvent就不会走了  直接走其对应的onTouch
				intercepter=false;
				Log.i(TAG, "Math.abs(deltaY)+200 不拦截  false");
			}
			else{
				intercepter=false;
				Log.i(TAG, " ACTION_MOVE intercepter   false");
			}
			break;
		case MotionEvent.ACTION_UP:
			intercepter=false;
			break;

		default:
			break;
		}
		return intercepter;
	}
	private void resetGesture() {
		mGestureDetector = new GestureDetector(context, new CustomGestureListener());
		first = true;

	}
	public HorizontalScrollView_OutConfict1(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public HorizontalScrollView_OutConfict1(Context context) {
		this(context,null);
	}
	//调用此方法滚动到目标位置
		public void smoothScrollTo(int fx, int fy) {
			int dx = fx - mScroller.getFinalX();
			int dy = fy - mScroller.getFinalY();
			smoothScrollBy(dx, dy);
		}
		//调用此方法设置滚动的相对偏移
		public void smoothScrollBy(int dx, int dy) {

			//设置mScroller的滚动偏移量
			mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
			invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
		}
		
		@Override
		public void computeScroll() {
			//先判断mScroller滚动是否完成
			if (mScroller.computeScrollOffset()) {
				//这里调用View的scrollTo()完成实际的滚动
				scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
				//必须调用该方法，否则不一定能看到滚动效果
				postInvalidate();
			}
			super.computeScroll();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			Log.i(TAG, " onTouchEvent");
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "onTouchEvent ACTION_DOWN  mGestureDetector\tX:"+event.getX()+"  \ty:"+event.getY());
				return mGestureDetector.onTouchEvent(event);
			case MotionEvent.ACTION_UP :
				Log.i(TAG, " onTouchEvent ACTION_UP");
				Log.i(TAG, "get Sy" + getScrollY());
				break;
			default:
				Log.i(TAG, "onTouchEvent ACTION_Move mGestureDetector\tX:"+event.getX()+"  \ty:"+event.getY());
				return mGestureDetector.onTouchEvent(event);
			}
			return super.onTouchEvent(event);
		}
		class CustomGestureListener implements GestureDetector.OnGestureListener {

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				int dis = (int) distanceX;
				Log.i(TAG,"dis"+ dis );
				if(e1!=null){
//					System.err.println("e1X:"+e1.getX());
				}
				if(e2!=null){
//					System.err.println("e2X:"+e2.getX()+"\te2History"+e2.getSize());
				}
				if (first&&e1==null) {
					first=false;
				}else{
					smoothScrollBy(dis, 0);
				}
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				return false;
			}
			
		}

}
