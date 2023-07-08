package com.example.mylib_test.activity.touch.view.utils;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class ShowMode{

    protected SheetBehavior sheetBehavior;

    public ShowMode(SheetBehavior sheetBehavior) {
        this.sheetBehavior = sheetBehavior;
    }

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

    public void layout(View mMoveView) {
    }

    public void released(@NonNull View releasedChild) {

    }

    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child,
                                  @NonNull View target, int dx, int dy,
                                  @NonNull int[] consumed, int type) {
    }

    public void onAutoScroll() {

    }

    public void onAutoScrollStop() {

    }
}
