package and.utils.nineold;

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.view.ViewHelper;

import java.util.Collection;
import java.util.List;

/**
 * Created by zone on 2016/6/27.
 * 特定顺序：NineHelper.newInstance().play(bounceAnim).before(squashAnim1).with(stretchAnim2).after(stretchAnim2);
 */
public class NineHelper {
    public  static long DURATION = 500;

    private static AnimatorSet newInstance() {
        AnimatorSet mAnimatorSet=new AnimatorSet();
        mAnimatorSet.setDuration(DURATION);
        return mAnimatorSet;
    }

    public static AnimatorSetProxy playSequentially(Animator... items){
        AnimatorSet mAnimatorSet=newInstance();
        mAnimatorSet.playSequentially(items);
        return new AnimatorSetProxy(mAnimatorSet);
    }
    public static AnimatorSetProxy playSequentially(List<Animator> items){
        AnimatorSet mAnimatorSet=newInstance();
        mAnimatorSet.playSequentially(items);
        return new AnimatorSetProxy(mAnimatorSet);
    }
    public static AnimatorSetProxy playTogether(Animator... items){
        AnimatorSet mAnimatorSet=newInstance();
        mAnimatorSet.playTogether(items);
        return new AnimatorSetProxy(mAnimatorSet);
    }


    public static AnimatorSetProxy playTogether(Collection<Animator> items){
        AnimatorSet mAnimatorSet=newInstance();
        mAnimatorSet.playTogether(items);
        return new AnimatorSetProxy(mAnimatorSet);
    }


    public static  AnimatorSetProxy play(BaseViewAnimator mBaseViewAnimator){
        AnimatorSet mAnimatorSet=newInstance();
        mBaseViewAnimator.prepare(mAnimatorSet);
        return new AnimatorSetProxy(mAnimatorSet);
    };


    private static  AnimatorSetProxy play(Class<? extends BaseViewAnimator> mBaseViewAnimator){
        try {
            return play(mBaseViewAnimator.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    };


    public static ObjectAnimator ofFloat(String propertyName, float... values){
        return ObjectAnimator.ofFloat(null,propertyName,values);
    };
    public static ObjectAnimator ofInt(String propertyName, int... values){
        return ObjectAnimator.ofInt(null,propertyName,values);
    };
    public static ObjectAnimator ofObject(String propertyName, TypeEvaluator evaluator, Object... values){
        return ObjectAnimator.ofObject(null,propertyName,evaluator,values);
    };


    public static void reset(View target) {
        ViewHelper.setAlpha(target, 1);
        ViewHelper.setScaleX(target, 1);
        ViewHelper.setScaleY(target, 1);
        ViewHelper.setTranslationX(target, 0);
        ViewHelper.setTranslationY(target, 0);
        ViewHelper.setRotation(target, 0);
        ViewHelper.setRotationY(target, 0);
        ViewHelper.setRotationX(target, 0);
        ViewHelper.setPivotX(target, target.getMeasuredWidth() / 2.0f);
        ViewHelper.setPivotY(target, target.getMeasuredHeight() / 2.0f);
    }
    //为了添加预设而用  //灵感来自 daimajia的AndroidViewAnimations
    public interface BaseViewAnimator{
         void prepare(AnimatorSet mAnimatorSet) ;
    }
}
