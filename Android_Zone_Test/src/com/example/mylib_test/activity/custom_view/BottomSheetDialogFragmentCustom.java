package com.example.mylib_test.activity.custom_view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import  com.google.android.material.R;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

public class BottomSheetDialogFragmentCustom extends DialogFragment {
    private BottomSheetBehavior<FrameLayout> behavior;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;

    @Override
    public void onStart() {
        super.onStart();
        if (this.behavior != null && this.behavior.getState() == STATE_HIDDEN) {
            this.behavior.setState(STATE_COLLAPSED);
        }
    }

    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        FrameLayout container = (FrameLayout)View.inflate(this.getContext(), R.layout.design_bottom_sheet_dialog, (ViewGroup)null);
        CoordinatorLayout coordinator = (CoordinatorLayout)container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = this.getLayoutInflater().inflate(layoutResId, coordinator, false);
        }

        FrameLayout bottomSheet = (FrameLayout)coordinator.findViewById(R.id.design_bottom_sheet);
        this.behavior = BottomSheetBehavior.from(bottomSheet);
        this.behavior.setBottomSheetCallback(this.bottomSheetCallback);
        this.behavior.setHideable(this.cancelable);
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }

        coordinator.findViewById(R.id.touch_outside).setOnClickListener(v -> {
            if (BottomSheetDialogFragmentCustom.this.getDialog() != null && BottomSheetDialogFragmentCustom.this.getDialog().isShowing()) {
                dismissAllowingStateLoss();
            }
        });

        ViewCompat.setAccessibilityDelegate(bottomSheet, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (BottomSheetDialogFragmentCustom.this.cancelable) {
                    info.addAction(1048576);
                    info.setDismissable(true);
                } else {
                    info.setDismissable(false);
                }

            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == 1048576 && BottomSheetDialogFragmentCustom.this.cancelable) {
                    BottomSheetDialogFragmentCustom.this.dismissAllowingStateLoss();
                    return true;
                } else {
                    return super.performAccessibilityAction(host, action, args);
                }
            }
        });

        bottomSheet.setOnTouchListener((view1, event) -> true);
        return container;
    }
}
