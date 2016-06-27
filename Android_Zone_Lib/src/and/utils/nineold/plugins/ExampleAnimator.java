package and.utils.nineold.plugins;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import and.utils.nineold.NineHelper;

/**
 * Created by sxl on 2016/6/27.
 */
public class ExampleAnimator implements NineHelper.BaseViewAnimator {
    @Override
    public void prepare(AnimatorSet mAnimatorSet) {
        mAnimatorSet.playSequentially(
                NineHelper.ofFloat("scaleX",1,0.9f,0.9f,1.1f,1.1f,1.1f,1.1f,1.1f,1.1f,1),
                NineHelper.ofFloat("scaleY",1,0.9f,0.9f,1.1f,1.1f,1.1f,1.1f,1.1f,1.1f,1),
                NineHelper.ofFloat("rotation",0 ,-3 , -3, 3, -3, 3, -3,3,-3,0)
        );
        mAnimatorSet.setDuration(1200);
    }
}
