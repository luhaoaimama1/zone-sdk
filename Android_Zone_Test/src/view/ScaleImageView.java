package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import view.utils.ZGestrueDetector;

public class ScaleImageView extends ImageView {

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        zGestrueDetector = new ZGestrueDetector(context, new ZGestrueDetector.OnGestureListener() {
            @Override
            public void onZoomIn() {

                System.out.println("ScaleImageView onZoomIn ");
            }

            @Override
            public void onKeading() {
                System.out.println("ScaleImageView onKeading ");
            }

            @Override
            public void onUpSilde() {
                System.out.println("ScaleImageView onUpSilde ");
            }

            @Override
            public void onDownSilde() {
                System.out.println("ScaleImageView onDownSilde ");
            }

            @Override
            public void onClick() {
                System.out.println("ScaleImageView onClick ");

                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        System.out.println("ScaleImageView==>"+animation.getAnimatedValue());
                    }
                });
                animator.reverse();
            }
        });
    }
    ValueAnimator animator=ValueAnimator.ofFloat(0f,1f);

    private ZGestrueDetector zGestrueDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = zGestrueDetector.onTouchEvent(event);
        return result;
    }

}
