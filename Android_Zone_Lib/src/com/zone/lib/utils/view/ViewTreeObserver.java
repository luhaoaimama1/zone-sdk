package com.zone.lib.utils.view;

import android.os.Build;
import android.view.View;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

/**
 * Copyright (c) 2018 BiliBili Inc.
 * [2018/7/20] by Zone
 */
public class ViewTreeObserver {

    public static void addOnDrawListener(final View view, final android.view.ViewTreeObserver.OnDrawListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN)
            view.getViewTreeObserver().addOnDrawListener(listener);
    }

    public static void addOnDrawListenerDelete(final View view, final android.view.ViewTreeObserver.OnDrawListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN)
            view.getViewTreeObserver().addOnDrawListener(new android.view.ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    if (Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        listener.onDraw();
                        view.getViewTreeObserver().removeOnDrawListener(this);
                    }
                }
            });
    }

    public static void addOnGlobalFocusChangeListener(final View view, final android.view.ViewTreeObserver.OnGlobalFocusChangeListener listener) {
        view.getViewTreeObserver().addOnGlobalFocusChangeListener(listener);
    }

    public static void addOnGlobalFocusChangeListenerDelete(final View view, final android.view.ViewTreeObserver.OnGlobalFocusChangeListener listener) {
        view.getViewTreeObserver().addOnGlobalFocusChangeListener(new android.view.ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    listener.onGlobalFocusChanged(oldFocus, newFocus);
                    view.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
                }
            }
        });
    }

    public static void addOnGlobalLayoutListener(final View view, final android.view.ViewTreeObserver.OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    public static void addOnGlobalLayoutListenerDelete(final View view, final android.view.ViewTreeObserver.OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onGlobalLayout();
                if (Build.VERSION.SDK_INT < JELLY_BEAN) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    public static void addOnPreDrawListener(final View view, final android.view.ViewTreeObserver.OnPreDrawListener listener) {
        view.getViewTreeObserver().addOnPreDrawListener(listener);
    }

    public static void addOnPreDrawListenerDelete(final View view, final android.view.ViewTreeObserver.OnPreDrawListener listener) {
        view.getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                boolean result = listener.onPreDraw();
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                return result;
            }
        });
    }

    public static void addOnScrollChangedListener(final View view, final android.view.ViewTreeObserver.OnScrollChangedListener listener) {
        view.getViewTreeObserver().addOnScrollChangedListener(listener);
    }

    public static void addOnScrollChangedListenerDelete(final View view, final android.view.ViewTreeObserver.OnScrollChangedListener listener) {
        view.getViewTreeObserver().addOnScrollChangedListener(new android.view.ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                listener.onScrollChanged();
                view.getViewTreeObserver().removeOnScrollChangedListener(this);
            }
        });
    }

    public static void addOnTouchModeChangeListener(final View view, final android.view.ViewTreeObserver.OnTouchModeChangeListener listener) {
        view.getViewTreeObserver().addOnTouchModeChangeListener(listener);
    }

    public static void addOnTouchModeChangeListenerDelete(final View view, final android.view.ViewTreeObserver.OnTouchModeChangeListener listener) {
        view.getViewTreeObserver().addOnTouchModeChangeListener(new android.view.ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {
                listener.onTouchModeChanged(isInTouchMode);
                view.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            }
        });
    }

    public static void addOnWindowAttachListener(final View view, final android.view.ViewTreeObserver.OnWindowAttachListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2)
            view.getViewTreeObserver().addOnWindowAttachListener(listener);
    }

    public static void addOnWindowAttachListenerDelete(final View view, final OnWindowAttachListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2)
            view.getViewTreeObserver().addOnWindowAttachListener(new android.view.ViewTreeObserver.OnWindowAttachListener() {
                @Override
                public void onWindowAttached() {
                    if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2) {
                        if (listener.onWindowAttached()) {
                            view.getViewTreeObserver().removeOnWindowAttachListener(this);
                        }
                    }
                }

                @Override
                public void onWindowDetached() {
                    if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2) {
                        if (listener.onWindowDetached()) {
                            view.getViewTreeObserver().removeOnWindowAttachListener(this);
                        }
                    }
                }
            });
    }

    public interface OnWindowAttachListener {
        boolean onWindowAttached();

        boolean onWindowDetached();
    }

    public static void addOnWindowFocusChangeListener(final View view, final android.view.ViewTreeObserver.OnWindowFocusChangeListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2)
            view.getViewTreeObserver().addOnWindowFocusChangeListener(listener);
    }

    public static void addOnWindowFocusChangeListenerDelete(final View view, final android.view.ViewTreeObserver.OnWindowFocusChangeListener listener) {
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2)
            view.getViewTreeObserver().addOnWindowFocusChangeListener(new android.view.ViewTreeObserver.OnWindowFocusChangeListener() {
                @Override
                public void onWindowFocusChanged(boolean hasFocus) {
                    if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR2) {
                        listener.onWindowFocusChanged(hasFocus);
                        view.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
                    }

                }
            });
    }

}
