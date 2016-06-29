package and.utils.nineold;

import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;

import java.util.ArrayList;

/**
 * Created by fuzhipeng on 16/6/29.
 */
public class AnimatorSetProxy {
    AnimatorSet mAnimatorSet;

    public AnimatorSetProxy(AnimatorSet mAnimatorSet) {
        this.mAnimatorSet = mAnimatorSet;
    }


    public AnimatorSetProxy setTarget(Object... objs) {
        if(objs.length==0||objs==null)
            return null;
        ArrayList<Animator> childs = mAnimatorSet.getChildAnimations();
        for (int i = 0; i < childs.size(); i++) {
            int length=  objs.length;
            if(i<length)
                childs.get(i).setTarget(objs[i]);
            else
                childs.get(i).setTarget(objs[length-1]);
        }
        return this;
    }

    public AnimatorSetProxy setInterpolator(/*Time*/Interpolator interpolator) {
        mAnimatorSet.setInterpolator(interpolator);
        return this;
    }

    public AnimatorSetProxy setStartDelay(long setStartDelay) {
        mAnimatorSet.setStartDelay(setStartDelay);
        return this;
    }

    public AnimatorSetProxy setupEndValues() {
        mAnimatorSet.setupEndValues();
        return this;
    }

    public AnimatorSetProxy setupStartValues() {
        mAnimatorSet.setupStartValues();
        return this;
    }

    public AnimatorSetProxy setDuration(long duration) {
        mAnimatorSet.setDuration(duration);
        return this;
    }

    public AnimatorSetProxy start() {
        mAnimatorSet.start();
        return this;
    }

    public AnimatorSet getSource() {
        return mAnimatorSet;
    }

}
