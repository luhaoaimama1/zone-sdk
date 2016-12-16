package com.example.mylib_test.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

/**
 * custom LayoutAnimationController for playing child animation
 * in any order.
 */
public class CustomLayoutAnimationController extends LayoutAnimationController {

    // 7 just lucky number
    public static final int ORDER_CUSTOM = 7;

    private OnIndexListener onIndexListener;

    public void setOnIndexListener(OnIndexListener onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public CustomLayoutAnimationController(Animation anim) {
        super(anim);
    }

    public CustomLayoutAnimationController(Animation anim, float delay) {
        super(anim, delay);
    }

    public CustomLayoutAnimationController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * override method for custom play child view animation order
     */
    protected int getTransformedIndex(AnimationParameters params) {
        //同时等1的话 就是同时
//        if (getOrder() == ORDER_CUSTOM && onIndexListener != null) {
//            return onIndexListener.onIndex(this, params.count, params.index);
//        } else {
//            return super.getTransformedIndex(params);
//        }
        System.out.println("params_count:"+params.count+"_index:"+params.index);
        return -2;
    }

    /**
     * callback for get play animation order
     */
    public static interface OnIndexListener {

        public int onIndex(CustomLayoutAnimationController controller, int count, int index);
    }
}