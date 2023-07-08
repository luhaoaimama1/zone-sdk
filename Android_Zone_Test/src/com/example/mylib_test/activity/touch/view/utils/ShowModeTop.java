package com.example.mylib_test.activity.touch.view.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.example.mylib_test.LogApp;
import com.zone.lib.utils.view.graphics.MathUtils;

public class ShowModeTop extends ShowMode {
    public interface Callback {
        void onOpen();

        void onClose();

        void onLayout();

        void onDrag();

        void onDragReleased();

        void onAutoScroll();

        void onAutoScrollStop();
    }

    private int getInitOffset = 0;
    Callback callback;
    int mPeekLength = 300;

    public ShowModeTop(SheetBehavior sheetBehavior, Callback callback) {
        super(sheetBehavior);
        this.callback = callback;
    }


    @Override
    int getViewVerticalDragRange() {
        return 1;
    }

    @Override
    public void layout(View mMoveView) {
        getInitOffset = -sheetBehavior.mMoveView.getMeasuredHeight() + mPeekLength;
        ViewCompat.offsetTopAndBottom(mMoveView, getInitOffset);
        status = STATUS_LAYOUT;
        if (callback != null) {
            callback.onLayout();
        }
    }

    @Override
    public int clampViewPositionVertical(@NonNull View child, int top) {
        int linear = MathUtils.clamp(top, getInitOffset, 0);
        LogApp.INSTANCE.d("top：" + top + "\t result: " + linear);
        return linear;
    }

    @Override
    public void released(@NonNull View releasedChild) {
        super.released(releasedChild);
        if (callback != null) {
            callback.onDragReleased();
        }
        if (isClosed(releasedChild)) {
            close();//关闭
        } else {
            open();//开启
        }
    }

    boolean isClosed(View releasedChild) {//open
        int topMap = MathUtils.clamp(releasedChild.getTop(), getInitOffset, 0);
        return Math.abs(topMap) >= Math.abs(getInitOffset) / 2;
    }

    public void open() {
        sheetBehavior.smoothSlideViewTo(0, 0);
        if (callback != null) {
            callback.onOpen();
        }
    }

    public void close() {
        sheetBehavior.smoothSlideViewTo(0, getInitOffset);
        if (callback != null) {
            callback.onClose();
        }
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy,
                                  @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (target != sheetBehavior.mMoveView || type == ViewCompat.TYPE_NON_TOUCH) return;
        int top = sheetBehavior.mMoveView.getTop();
        sheetBehavior.log(
                "onNestedPreScroll dy:" + dy
                        + ":top " + top
                        + "measure " + sheetBehavior.mMoveView.getMeasuredHeight()
        );
        //这个区间才能滑动
        int getInitOffset = this.getInitOffset;

        //getInitOffset ->0 就是 折叠->不折叠。
        if (top >= getInitOffset && top <= 0) {//没有等于  比child先处理
            //viewOffset 就是先把能用的偏移量全部用掉 但是把边界截取掉。
            int viewOffset = MathUtils.clamp(top - dy, getInitOffset, 0);
            //通过实际使用的viewOffset 和 top 计算出 实际消耗的consumeY .
            // 由于向下滑动 dy是负的。而viewOffset-top则是正的 去反则是consumed[1]消耗的值。
            //消耗的值 要自己去offset偏移量
            int consumedY = viewOffset - top;
            consumed[1] = -consumedY;
            ViewCompat.offsetTopAndBottom(child, consumedY);

            status = STATUS_DRAG;
            if (callback != null) {
                callback.onDrag();
            }
        }
    }

    public int getPeekLength() {
        return mPeekLength;
    }

    public void setPeekLength(int mPeekLength) {
        this.mPeekLength = mPeekLength;
    }

    @Override
    public void onAutoScroll() {
        super.onAutoScroll();
        status = STATUS_SCROLL_AUTO;
        if (callback != null) {
            callback.onAutoScroll();
        }
    }

    @Override
    public void onAutoScrollStop() {
        super.onAutoScrollStop();
        if (sheetBehavior.mMoveView.getTop() == 0) {
            status = STATUS_OPEN;
            if (callback != null) {
                callback.onOpen();
            }
        } else {
            status = STATUS_CLOSE;
            if (callback != null) {
                callback.onClose();
            }
        }
        if (callback != null) {
            callback.onAutoScrollStop();
        }
    }

    private static final int STATUS_FREE = 0;
    private static final int STATUS_CLOSE = 1;
    private static final int STATUS_OPEN = 2;
    private static final int STATUS_DRAG = 3;
    private static final int STATUS_SCROLL_AUTO = 4;
    private static final int STATUS_LAYOUT = 5;
    private int status = STATUS_FREE;
}
