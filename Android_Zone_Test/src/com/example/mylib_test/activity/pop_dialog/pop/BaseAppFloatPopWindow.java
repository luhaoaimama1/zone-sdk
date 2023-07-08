package com.example.mylib_test.activity.pop_dialog.pop;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.widget.PopupWindow.INPUT_METHOD_FROM_FOCUSABLE;
import static android.widget.PopupWindow.INPUT_METHOD_NEEDED;
import static android.widget.PopupWindow.INPUT_METHOD_NOT_NEEDED;

public abstract class BaseAppFloatPopWindow {
    private WindowManager.LayoutParams wmParams;
    private View mContentView;
    private WindowManager mWindowManager;
    protected Context mContext;
    private ViewGroup mRootView;
    private boolean isShow = false;

    public void showAtLocation(Context context) {
        showAtLocation(context, Gravity.NO_GRAVITY, 0, 0);
    }

    public void showAtLocation(Context context, int gravity, int x, int y) {
        this.mContext = context;
        mRootView = new FrameLayout(context);
        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
//        wmParams.flags = computeFlags(wmParams.flags);
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mContentView = onCreateView(LayoutInflater.from(context), mRootView);

        if (mContentView != null) {
            ViewGroup.LayoutParams lp = mContentView.getLayoutParams();
            wmParams.width = lp.width;
            wmParams.height = lp.height;
            mRootView.addView(mContentView, lp);
        }
        updateParams(gravity, x, y);
        mWindowManager.addView(mRootView, wmParams);
        setShow(true);
    }


    protected void onShow() {

    }

    protected void onD() {

    }

    @Nullable
    protected abstract View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup viewGroup);

    private synchronized void setShow(boolean show) {
        isShow = show;
    }

    public void updateLocation(int gravity, int x, int y) {
        updateParams(gravity, x, y);
        //添加mFloatLayout
        mWindowManager.updateViewLayout(mRootView, wmParams);
    }

    private void updateParams(int gravity, int x, int y) {
        if (mRootView == null) return;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.gravity = gravity;
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = x;
        wmParams.y = y;
    }

    public boolean isShow() {
        return isShow;
    }


    public void dismiss() {
        if (mRootView != null) {
            mWindowManager.removeView(mRootView);
            setShow(false);
        }
    }


    private boolean mFocusable;

    private int mInputMethodMode = INPUT_METHOD_FROM_FOCUSABLE;
    private boolean mTouchable = true;
    private boolean mOutsideTouchable = false;
    private boolean mClippingEnabled = true;
    private boolean mClipToScreen;
    private boolean mLayoutInScreen;
    private int mSplitTouchEnabled = -1;
    private boolean mLayoutInsetDecor = false;
    private boolean mNotTouchModal;
    private boolean mAttachedInDecor = true;
    private boolean mAttachedInDecorSet = false;

    public boolean isLayoutInsetDecor() {
        return mLayoutInsetDecor;
    }

    public void setLayoutInsetDecor(boolean mLayoutInsetDecor) {
        this.mLayoutInsetDecor = mLayoutInsetDecor;
    }

    public boolean isOutsideTouchable() {
        return mOutsideTouchable;
    }

    public void setOutsideTouchable(boolean mOutsideTouchable) {
        this.mOutsideTouchable = mOutsideTouchable;
    }

    public boolean isClippingEnabled() {
        return mClippingEnabled;
    }

    public void setClippingEnabled(boolean mClippingEnabled) {
        this.mClippingEnabled = mClippingEnabled;
    }

    public boolean isClipToScreen() {
        return mClipToScreen;
    }

    public void setClipToScreen(boolean mClipToScreen) {
        this.mClipToScreen = mClipToScreen;
    }

    public boolean isLayoutInScreen() {
        return mLayoutInScreen;
    }

    public void setLayoutInScreen(boolean mLayoutInScreen) {
        this.mLayoutInScreen = mLayoutInScreen;
    }

    public boolean isNotTouchModal() {
        return mNotTouchModal;
    }

    public void setNotTouchModal(boolean mNotTouchModal) {
        this.mNotTouchModal = mNotTouchModal;
    }

    public boolean isFocusable() {
        return mFocusable;
    }

    public void setFocusable(boolean mFocusable) {
        this.mFocusable = mFocusable;
    }


    public int getInputMethodMode() {
        return mInputMethodMode;
    }

    public void setInputMethodMode(int mInputMethodMode) {
        this.mInputMethodMode = mInputMethodMode;
    }

    public boolean isTouchable() {
        return mTouchable;
    }

    public void setTouchable(boolean mTouchable) {
        this.mTouchable = mTouchable;
    }

    public boolean isSplitTouchEnabled() {
        if (mSplitTouchEnabled < 0 && mContext != null) {
            return mContext.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.HONEYCOMB;
        }
        return mSplitTouchEnabled == 1;
    }

    public void setSplitTouchEnabled(boolean enabled) {
        mSplitTouchEnabled = enabled ? 1 : 0;
    }

    public boolean isAttachedInDecor() {
        return mAttachedInDecor;
    }

    public void setAttachedInDecor(boolean enabled) {
        mAttachedInDecor = enabled;
        mAttachedInDecorSet = true;
    }


    private int computeFlags(int curFlags) {
        curFlags &= ~(
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                        // Allows the view to be on top of the StatusBar
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR |
                        WindowManager.LayoutParams.FLAG_SPLIT_TOUCH);
        if (!mFocusable) {
            curFlags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            if (mInputMethodMode == INPUT_METHOD_NEEDED) {
                curFlags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
            }
        } else if (mInputMethodMode == INPUT_METHOD_NOT_NEEDED) {
            curFlags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        }
        if (!mTouchable) {
            curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
        if (mOutsideTouchable) {
            curFlags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        }
        if (!mClippingEnabled || mClipToScreen) {
            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        }
        if (isSplitTouchEnabled()) {
            curFlags |= WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
        }
        if (mLayoutInScreen) {
            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }
        if (mLayoutInsetDecor) {
            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR;
        }
        if (mNotTouchModal) {
            curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        if (mAttachedInDecor) {
            curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR;
        }
        return curFlags;
    }
}
