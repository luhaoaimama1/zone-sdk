package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.mylib_test.LogApp;
import com.example.mylib_test.R;
import com.zone.lib.utils.view.graphics.MathUtils;

/**
 * 布局方面：
 * 需要布局 刚好隐藏这个view+peekLength
 * <p>
 * mViewDragHelper 控制滑动方面
 * <p>
 * 1.确定某个view可以拖拽
 * 2.上下左右滑动是否拦截
 * 3.上下左右滑动的范围控制 <===
 * 4.手指释放
 * <p>
 * 5.滚动兼容 computeScroll
 * 6.open close
 * 7.偏移量回调监听
 * <p>
 * mViewDragHelper
 * <p>
 * zone todo: 2020/7/31
 */
public class SheetFrameLayout extends FrameLayout {
    private final ViewDragHelper mViewDragHelper;

    public SheetFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public SheetFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private View mMoveView = null;
    private int mPeekLength = 300;

    int resourceId = -1;

    public SheetFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SheetFrameLayout, 0, 0);
            try {
                resourceId = typedArray.getResourceId(R.styleable.SheetFrameLayout_sheetMoveId, -1);

            } finally {
                typedArray.recycle();
            }
        }

        mViewDragHelper = ViewDragHelper.create(this, mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (resourceId != -1) {
            mMoveView = findViewById(resourceId);
        }
    }

    static class ShowMode {

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

        public void layout(View mMoveView,  Rect layoutRect) {
            //zone todo: 2020/7/31
        }

        boolean isClosed() {
            return true;
        }

        void open() {

        }

        void close() {

        }

        int getInitOffset() {
            return 0;
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
        boolean isClosed() {
            return Math.abs(mMoveView.getTop()) >= Math.abs(getInitOffset()) / 2;
        }

        @Override
        void open() {
            super.open();
            LogApp.INSTANCE.d("smoothSlideViewTo open!");
            mViewDragHelper.smoothSlideViewTo(mMoveView, 0, 0);
            ViewCompat.postInvalidateOnAnimation(SheetFrameLayout.this);//兼容刷新的
        }

        @Override
        void close() {
            super.close();
            LogApp.INSTANCE.d("smoothSlideViewTo close!");
            mViewDragHelper.smoothSlideViewTo(mMoveView, 0, getInitOffset());
            ViewCompat.postInvalidateOnAnimation(SheetFrameLayout.this);//兼容刷新的
        }
    };
    //zone todo: 2020/7/31 其他最后弄
//    ShowMode BOTTOM = new ShowMode() {
//        @Override
//        int getViewVerticalDragRange() {
//            return 1;
//        }
//
//        @Override
//        public int clampViewPositionVertical(@NonNull View child, int top) {
//            return super.clampViewPositionVertical(child, top);
//        }
//
//    };
//    ShowMode LEFT = new ShowMode() {
//        @Override
//        int getViewHorizontalDragRange() {
//            return 1;
//        }
//
//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left) {
//            return super.clampViewPositionHorizontal(child, left);
//        }
//    };
//    ShowMode RIGHT = new ShowMode() {
//        @Override
//        int getViewHorizontalDragRange() {
//            return 1;
//        }
//
//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left) {
//            return super.clampViewPositionHorizontal(child, left);
//        }
//
//    };

    // ViewDragHelper.EDGE_RIGHT:
    private ShowMode mode = TOP;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    Rect layoutRect = new Rect();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutRect.set(0, 0, mMoveView.getMeasuredWidth(), mMoveView.getMeasuredHeight());
        mode.layout(mMoveView, layoutRect);
    }


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
            //手指释放后  检查mMainView的getLeft
            if (releasedChild == mMoveView) {
                if (mode.isClosed()) {
                    //关闭
                    mode.close();
                } else {
                    //开启
                    mode.open();
                }
            }
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

    @Override
    public void computeScroll() {
        //用到  mViewDragHelper.smoothSlideViewTo 即里面封装的scroller 即需要这样集成
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
        }else{
            LogApp.INSTANCE.d("computeScroll stop!");
        }
    }

}
