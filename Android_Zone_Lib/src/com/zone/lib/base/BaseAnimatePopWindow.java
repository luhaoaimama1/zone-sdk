package com.zone.lib.base;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

import androidx.annotation.RequiresApi;

import com.zone.lib.utils.view.ViewTreeObserver;

/**
 * 原因popwindow 页面切到后台在切回来动画依然执行。所以模仿PopupWindow的Transition
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public abstract class BaseAnimatePopWindow extends BasePopWindow {
    private Animation enterAnimation, exitAnimation;
    private AnimationListener animationListener;
    android.view.ViewTreeObserver.OnGlobalLayoutListener listener = new android.view.ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            if (mMenuView != null) {
                mMenuView.startAnimation(enterAnimation);
                mMenuView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    };

    public interface AnimationListener {
        void applyTransformation(float interpolatedTime, boolean isEnter);
    }

    public BaseAnimatePopWindow(Activity activity) {
        super(activity);
    }

    public BaseAnimatePopWindow(Activity activity, int showAtLocationViewId) {
        super(activity, showAtLocationViewId);
    }

    @Override
    public void setAnimationStyle(int animationStyle) {
        super.setAnimationStyle(0);
    }

    public void setAnimationStyle(int enterAnimationRes, int exitAnimationRes) {
        enterAnimation = getAnimation(enterAnimationRes, true);
        exitAnimation = getAnimation(exitAnimationRes, false);

        if (this.enterAnimation != null) {
            mMenuView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            mMenuView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
        }
    }

    private Animation getAnimation(int enterAnimationRes, boolean isEnter) {
        Animation animation = AnimationUtils.loadAnimation(activity, enterAnimationRes);
        AnimationSet animset = new AnimationSet(true);
        animset.addAnimation(animation);
        animset.addAnimation(new AnimationInner(isEnter));

        animset.setInterpolator(animation.getInterpolator());
        animset.setDuration(animation.getDuration());
        animset.setFillAfter(animation.getFillAfter());
        animset.setFillBefore(animation.getFillBefore());
        animset.setRepeatMode(animation.getRepeatMode());
        animset.setStartTime(animation.getStartTime());
        animset.setStartOffset(animation.getStartOffset());
        return animset;
    }

    @Override
    protected void findView(final View mMenuView) {

    }

    @Override
    public void dismiss() {
        if (exitAnimation == null) {
            super.dismiss();
        } else {
            if (!exitAnimation.hasStarted()) {
                exitAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mMenuView.clearAnimation();
                        BaseAnimatePopWindow.super.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                mMenuView.startAnimation(exitAnimation);
            }
        }
    }

    private class AnimationInner extends Animation {
        boolean isEnter;

        public AnimationInner(boolean isEnter) {
            this.isEnter = isEnter;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (animationListener != null) {
                animationListener.applyTransformation(interpolatedTime, isEnter);
            }
        }
    }

    public AnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

}
