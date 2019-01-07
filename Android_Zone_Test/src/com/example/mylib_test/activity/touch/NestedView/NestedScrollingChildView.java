package com.example.mylib_test.activity.touch.NestedView;

import android.content.Context;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.OverScroller;

public class NestedScrollingChildView extends LinearLayout implements NestedScrollingChild, OnGestureListener {

    private final OverScroller mScroller;
    private GestureDetectorCompat mDetector;

    public NestedScrollingChildView(Context context) {
        this(context, null);
    }

    public NestedScrollingChildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        mDetector = new GestureDetectorCompat(context, this);
        //todo 1
        setNestedScrollingEnabled(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean handled = mDetector.onTouchEvent(event);
        if (!handled && event.getAction() == MotionEvent.ACTION_UP) {
            //todo 5
            stopNestedScroll();
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        return true;
    }


    int[] offsetInWindow=new int[2];

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int[] consumed = new int[2];
        System.out.println("e1 y:"+e1.getY()+"\t rawY:"+e1.getRawY());
        System.out.println("e2 y:"+e2.getY()+"\t rawY:"+e2.getRawY());
        System.out.println("distanceY:" + distanceY);
        System.out.println("offsetInWindow[1]:"+offsetInWindow[1]);
        int restDistanceY = (int) distanceY-offsetInWindow[1];
        //todo 2
        if (dispatchNestedPreScroll(0, restDistanceY, consumed, offsetInWindow)) {
            //offsetInWindow 如果不加这个参数会出现抖动，
            // 因为父亲消耗后，布局会偏移导致  每次的触摸事件e 其实也跟着偏移了(偏移量是上一次的布局的位移)，
            //所以e需要修正
            restDistanceY = restDistanceY-consumed[1];
            System.out.println("consumed:" + consumed[1]);
        }
        if (Math.abs(restDistanceY) > 1){
            scrollBy(0, restDistanceY);
            System.out.println("scrollBy:" + restDistanceY);
        }
//        dispatchNestedScroll(0, 0, 0, 0, null);
        return true;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //todo 3
        if (!dispatchNestedPreFling(velocityX, -velocityY)) {
            //最后一个限制 就是本身的高度了~
            mScroller.fling(0, getScrollY(), 0, -(int) velocityY, 0, 0, 0, getMeasuredHeight());
            invalidate();
        }
        return true;
    }


    @Override
    public void scrollTo(int x, int y) {
        //防止滑动出界； 滑动多了也不怕
        if (y < 0)
            y = 0;
        if (y > getMeasuredHeight())
            y = getMeasuredHeight();
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
//固定用法

    private final NestedScrollingChildHelper mNestedScrollingChildHelper;

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        //确定能不能 嵌套滑动；
        mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        //因为上面的方法 helper已经得出结论直接调用下面的方法即可
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        //做准备：主要是找到 Nested(嵌套)ScrollingParent
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        //停止嵌套滑动；
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        //因为 startNestedScroll里面已经得出结论了  直接调用下方法即可；
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        //如果返回true证明 parent消耗了位移 ；
        //主要计算->offsetInWindow,代表 child就是当前 经过此方法后 在屏幕上的差值；
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        //如果返回true证明 parent消耗了位移 ；
        //主要计算->offsetInWindow,代表 child就是当前 经过此方法后 在屏幕上的差值；
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mNestedScrollingChildHelper.onDetachedFromWindow();
    }


//另一个接口

    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }



}
