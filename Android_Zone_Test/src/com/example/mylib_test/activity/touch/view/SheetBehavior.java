package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.mylib_test.LogApp;
import com.zone.lib.utils.view.graphics.MathUtils;

/**
 * @param <V>
 */
public class SheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public SheetBehavior() {
    }

    public SheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private View mMoveView = null;
    private int mPeekLength = 300;
    private ViewDragHelper mViewDragHelper;
    Rect layoutRect = new Rect();

    protected float mTotalUnconsumed;

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection); //不写空白

        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(parent, this.mCallback);
        }
        this.mMoveView = child;
        layoutRect.set(0, 0, mMoveView.getMeasuredWidth(), mMoveView.getMeasuredHeight());
        mode.layout(mMoveView, layoutRect);
        return true;
    }

    class ShowMode {

        int getViewHorizontalDragRange() {
            return -1;
        }

        int getViewVerticalDragRange() {
            return -1;
        }


        public int clampViewPositionHorizontal(@NonNull View child, int left) {
            return 0;
        }

        public int clampViewPositionVertical(@NonNull View child, int top) {
            return 0;
        }

        public void layout(View mMoveView, Rect layoutRect) {
            //zone todo: 2020/7/31
        }

        boolean isClosed(int top) {
            return true;
        }

        void open() {

        }

        void close() {

        }

        int getInitOffset() {
            return 0;
        }

        boolean onNestStart(boolean isAllowVerticalScroll) {
            return false;
        }

        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                      @NonNull View target, int dx, int dy,
                                      @NonNull int[] consumed, int type) {
        }

        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                   @NonNull View target, int dxConsumed, int dyConsumed,
                                   int dxUnconsumed, int dyUnconsumed, int type) {
        }
    }

    ShowMode TOP = new ShowMode() {
        @Override
        int getInitOffset() {
            return -mMoveView.getMeasuredHeight() + mPeekLength;
        }

        @Override
        int getViewVerticalDragRange() {
            return 1;
        }

        @Override
        public void layout(View mMoveView, Rect layoutRect) {
            ViewCompat.offsetTopAndBottom(mMoveView, getInitOffset());
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top) {
            int linear = MathUtils.clamp(top, getInitOffset(), 0);
            LogApp.INSTANCE.d("top：" + top + "\t result: " + linear);
            return linear;
        }

        @Override
        boolean isClosed(int top) {//open
            int topMap = MathUtils.clamp(top,getInitOffset(),0);
            return Math.abs(topMap) >= Math.abs(getInitOffset()) / 2;
        }

        @Override
        void open() {
            super.open();
            smoothSlideViewTo(0, 0);
        }

        @Override
        void close() {
            super.close();
            smoothSlideViewTo(0, getInitOffset());
        }

        @Override
        boolean onNestStart(boolean isAllowVerticalScroll) {
            return isAllowVerticalScroll;
        }

        @Override
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                      @NonNull View target, int dx, int dy,
                                      @NonNull int[] consumed, int type) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

            if (target != mMoveView || type == ViewCompat.TYPE_NON_TOUCH) return;

            log("onNestedPreScroll dy:" + dy);
            //向下滑动 dy=-18
            int top = mMoveView.getTop();
            //这个区间才能滑动
            int initOffset = getInitOffset();
            if (top > initOffset && top < 0) {//没有等于  比child先处理
//      如果要移动的话=         top - dy;
                if (dy > 0) {
                    if (top - dy < initOffset) {
                        consumed[1] = top - initOffset;
                    } else {
                        consumed[1] = dy;
                    }
                } else if (dy < 0) { //向下滑动
                    if (top - dy > 0) {
                        consumed[1] = top;
                    } else {
                        consumed[1] = dy;
                    }
                }
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
            }
        }

        //        float newTop = (float)child.getTop() + yvel * 0.1F;
        @Override
        public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                   @NonNull View target, int dxConsumed, int dyConsumed,
                                   int dxUnconsumed, int dyUnconsumed, int type) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
            log("onNestedScroll dyUnconsumed:" + dyUnconsumed);
//

            // todo zone    TYPE_NON_TOUCH 阻止了快速滑动抬起的问题
            if (target != mMoveView || type == ViewCompat.TYPE_NON_TOUCH) return;

            log("onNestedPreScroll dyUnconsumed:" + dyUnconsumed);
            //向下滑动 dyUnconsumed=-18
            int top = mMoveView.getTop();
            //这个区间才能滑动
            int initOffset = getInitOffset();
            if (top >= initOffset && top <= 0) {
//      如果要移动的话=         top - dyUnconsumed;
                int cosume = 0;
                if (dyUnconsumed > 0) {
                    if (top - dyUnconsumed < initOffset) {
                        cosume = top - initOffset;
                    } else {
                        cosume = dyUnconsumed;
                    }
                } else if (dyUnconsumed < 0) { //向下滑动
                    if (top - dyUnconsumed > 0) {
                        cosume = top;
                    } else {
                        cosume = dyUnconsumed;
                    }
                }
                ViewCompat.offsetTopAndBottom(child, -cosume);
            }
        }
    };

    private ShowMode mode = TOP;

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mMoveView != null && mMoveView == child;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mode.getViewHorizontalDragRange();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mode.getViewVerticalDragRange();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return mode.clampViewPositionHorizontal(child, left);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return mode.clampViewPositionVertical(child, top);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            released(releasedChild,releasedChild.getTop());
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }
    };

    public void released(@NonNull View releasedChild,int top) {
        //手指释放后  检查mMainView的getLeft
        if (releasedChild == mMoveView) {
            if (mode.isClosed(top)) {
                //关闭
                mode.close();
            } else {
                //开启
                mode.open();
            }
        }
    }

    public void smoothSlideViewTo(int finalLeft, int finalTop) {
        if (mViewDragHelper.smoothSlideViewTo(mMoveView, finalLeft, finalTop)) {
            log("smoothSlideViewTo "
                    + "finalLeft:" + finalLeft
                    + "finalTop:" + finalTop
            );
            ViewCompat.postOnAnimation(mMoveView, new SettleRunnable(mMoveView));
        }
    }

    private class SettleRunnable implements Runnable {
        private final View view;

        SettleRunnable(View view) {
            this.view = view;
        }

        public void run() {
            if (SheetBehavior.this.mViewDragHelper != null && SheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                log("SettleRunnable, stop");
            }

        }
    }

    private void log(String content) {
        LogApp.INSTANCE.d(content);
    }

    private float mTouchX;
    private float mTouchY;

//    @Override
//    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent event) {
//
//        if (child != mMoveView) return super.onInterceptTouchEvent(parent, child, event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mTouchX = event.getX();
//                mTouchY = event.getY();
//                break;
//            default:
//                break;
//        }
//
//        boolean isSpread = mMoveView.getTop() != 0;
//        float v = event.getY() - this.mTouchY;
//        boolean tagetViewCanScroll = mMoveView.canScrollVertically((int) v);
//
//        //什么时候拦截事件？
//        //move的时候recycelr 请求跳过拦截方法
//        //在这之前
//
//        if (mViewDragHelper != null && (!isSpread || !tagetViewCanScroll)
////                && Math.abs((float) this.mTouchY - event.getY()) > (float) mViewDragHelper.getTouchSlop()
//        ) {
////            boolean b = mViewDragHelper.shouldInterceptTouchEvent(event);
////            System.out.println(b);
//            return false;
//        }
//        return super.onInterceptTouchEvent(parent, child, event);
//    }
//
//    @Override
//    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull MotionEvent ev) {
//        if (mViewDragHelper != null) {
//            mViewDragHelper.processTouchEvent(ev);
//            return true;
//        }
//        return super.onTouchEvent(parent, child, ev);
//    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isScrollVetical = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return target == mMoveView && isScrollVetical;
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        float newTop = (float) target.getTop() - mVelocityY * 0.1F; //mVelocityY>0 是手指上滑动
        released(target,(int)newTop);
        //todo zone 根据速度 判断是否回来返回动画
        reset();
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                                  @NonNull View target, int dx, int dy,
                                  @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        mode.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    private void reset() {
        mVelocityY = 0;
        mVelocityX = 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        mode.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    float mVelocityY = 0;
    float mVelocityX = 0;

    //如果处理的快速滑动事件，那么别人就不处理了 例如 recycelrview就不处理了
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        log("onNestedPreFling "
                + "velocityY:" + velocityY +
                ""
        );
        mVelocityY = velocityY;
        mVelocityX = velocityX;
        return false;
    }
}

