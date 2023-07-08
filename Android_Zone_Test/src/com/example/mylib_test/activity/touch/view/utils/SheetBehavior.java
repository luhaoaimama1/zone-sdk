package com.example.mylib_test.activity.touch.view.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.mylib_test.LogApp;

public class SheetBehavior  extends CoordinatorLayout.Behavior<View> {

    public static SheetBehavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        } else {
            CoordinatorLayout.Behavior behavior = ((androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) params).getBehavior();
            if (!(behavior instanceof SheetBehavior)) {
                throw new IllegalArgumentException("The view is not associated with SheetBehavior");
            } else {
                return (SheetBehavior) behavior;
            }
        }
    }

    public SheetBehavior() {
    }

    public SheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ShowMode showStyle = new ShowModeTop(this, null);
    View mMoveView = null;
    ViewDragHelper mViewDragHelper;

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection); //不写空白
        if (this.mViewDragHelper == null) {
            this.mViewDragHelper = ViewDragHelper.create(parent, this.mCallback);
        }
        this.mMoveView = child;
        showStyle.layout(mMoveView);
        return true;
    }

    private final ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mMoveView != null && mMoveView == child;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return showStyle.getViewHorizontalDragRange();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return showStyle.getViewVerticalDragRange();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return showStyle.clampViewPositionHorizontal(child, left);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return showStyle.clampViewPositionVertical(child, top);
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
            released(releasedChild);
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

    public void released(@NonNull View releasedChild) {
        if (releasedChild == mMoveView) {//手指释放后
            showStyle.released(mMoveView);
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
                log("SettleRunnable, continue top:"+this.view.getTop());
                ViewCompat.postOnAnimation(this.view, this);
                showStyle.onAutoScroll();
            } else {
                log("SettleRunnable, stop");
                showStyle.onAutoScrollStop();
            }
        }
    }

    void log(String content) {
        LogApp.INSTANCE.d(content);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (showStyle.getViewVerticalDragRange() > 0) {
            return target == mMoveView && (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        } else if (showStyle.getViewHorizontalDragRange() > 0) {
            return target == mMoveView && (axes & ViewCompat.SCROLL_AXIS_HORIZONTAL) != 0;
        } else return false;//默认不处理
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        released(target);
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy,
                                  @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        showStyle.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    //不使用此方法 因为 onNestedScroll中没有int[] consumed, 也就是消耗完了事件别人并不知道 如果有人在处理就不对了。
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    //如果处理的快速滑动事件，那么别人就不处理了 例如 recycelrview就不处理了
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        log("onNestedPreFling " + "velocityY:" + velocityY + "");
        return false;
    }

    public ShowMode getShowStyle() {
        return showStyle;
    }

    public void setShowStyle(ShowMode mShowMode) {
        this.showStyle = mShowMode;
    }
}

