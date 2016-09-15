package com.zone.view.ninegridview.preview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.zone.customview.ninegridview.R;
import com.zone.zbanner.ViewPagerCircle;

import java.util.List;

import and.utils.activity_fragment_ui.MeasureUtils;

/**
 * Created by Administrator on 2016/4/12.
 */
public class ImagePreviewActivity extends Activity implements ViewTreeObserver.OnPreDrawListener {
    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String INDEX = "index";
    public static final int ANIMATE_DURATION = 300;
    private int currentItem;
    private List<ImageInfoInner> imageInfo;

    private ViewPagerCircle vpc;
    private TextView tv_pager;
    private RelativeLayout rootView;
    private PhotoViewPagerAdapterCycle viewPagerAdapter;
    private boolean debug;
    private int imageHeight, imageWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_a_preview);
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        vpc = (ViewPagerCircle) findViewById(R.id.vpc);
        tv_pager = (TextView) findViewById(R.id.tv_pager);

        vpc.getViewTreeObserver().addOnPreDrawListener(this);

        Intent intent = getIntent();
        imageInfo = (List<ImageInfoInner>) intent.getSerializableExtra(IMAGE_INFO);
        currentItem = intent.getIntExtra(INDEX, 0);
        vpc.setAdapter(viewPagerAdapter = new PhotoViewPagerAdapterCycle(this, imageInfo, false), currentItem);
        tv_pager.setText(String.format("%1$s/%2$s", currentItem + 1, imageInfo.size()));
        vpc.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tv_pager.setText(String.format("%1$s/%2$s", position + 1, imageInfo.size()));
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onPreDraw() {
        vpc.getViewTreeObserver().removeOnPreDrawListener(this);
        startValueAnimator(true);
        return true;
    }

    private void startValueAnimator(boolean isEnter) {
        //你只要想执行这个过度  值之间的过度 或者对象之间的过度
        ValueAnimator mValueAnimator = null;
        if (isEnter)
            mValueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        else
            mValueAnimator = ValueAnimator.ofFloat(1.0f, 0);

        computeImageWidthAndHeight(viewPagerAdapter.pv);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //取出 你在上面付的值或者对象进行强转   记住  如果是对象 请setObjectValue和setEvaluator
                float fraction = (float) animation.getAnimatedValue();
                View view = vpc;
                // 我明白了 是两个图片的中心点 对齐  并且大小一样的时候 这样才算重合 而不是 左上角重合
                //imageInfo.get(currentItem).imageViewX 是左上角点；+ imageInfo.get(currentItem).imageViewWidth / 2 就是左上角点对其 图片的中心点；
                //- view.getWidth() / 2最后 就是现在图片的中心点对其图片的中心点
                ViewHelper.setTranslationX(view, floatEvaluate(fraction, imageInfo.get(currentItem).imageViewX + imageInfo.get(currentItem).imageViewWidth / 2 - view.getWidth() / 2, 0));
                ViewHelper.setTranslationY(view, floatEvaluate(fraction, imageInfo.get(currentItem).imageViewY + imageInfo.get(currentItem).imageViewHeight / 2 - view.getHeight() / 2, 0));
                //主要是  imageWidth=0的时候取0
                float scaleX=(imageWidth!=0?(float) imageInfo.get(currentItem).getImageViewWidth() / imageWidth:0);
                float scaleY=imageHeight!=0?(float) imageInfo.get(currentItem).getImageViewHeight() / imageHeight :0;
                ViewHelper.setScaleX(view, floatEvaluate(fraction, scaleX, 1.0f));
                ViewHelper.setScaleY(view, floatEvaluate(fraction, scaleY, 1.0f));
                ViewHelper.setAlpha(view, fraction);
                rootView.setBackgroundColor(argbEvalueator(fraction, Color.TRANSPARENT, Color.BLACK));
                if (debug) {
                    System.out.println("fraction:" + fraction
                                    + "\t setTranslationX:" + floatEvaluate(fraction, imageInfo.get(currentItem).imageViewX + imageInfo.get(currentItem).imageViewWidth / 2 - view.getWidth() / 2, 0)
                                    + "\t setTranslationY:" + floatEvaluate(fraction, imageInfo.get(currentItem).imageViewY + imageInfo.get(currentItem).imageViewHeight / 2 - view.getHeight() / 2, 0)
                                    + "\t setScaleX:" + floatEvaluate(fraction, scaleX, 1.0f)
                                    + "\t setScaleY:" + floatEvaluate(fraction,scaleY, 1.0f)
                                    + "\t setBackgroundColor:" + argbEvalueator(fraction, Color.TRANSPARENT, Color.BLACK)
                    );
                }
            }
        });
        addListener(mValueAnimator, isEnter);
        //时间
        mValueAnimator.setDuration(ANIMATE_DURATION);
        mValueAnimator.start();
    }


    @Override
    public void onBackPressed() {
        finishActivityAnim();
    }

    private void finishActivityAnim() {
        currentItem = vpc.getCurrentItem();
        System.out.println("currentItem:" + currentItem);
        startValueAnimator(false);
    }


    /**
     * 计算图片的宽高
     */
    private void computeImageWidthAndHeight(ImageView imageView) {
        MeasureUtils.measureImage(imageView, new MeasureUtils.ImageListener() {
            @Override
            public void imageShowProperty(ImageView iv, float left, float top, int imageShowX, int imageShowY) {
                imageWidth=  imageShowX;
                imageHeight=  imageShowY;
            }
        });
    }

    public Float floatEvaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    //直接到 TypeEvaluator ArgbEvaluator里找到
    public int argbEvalueator(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24//
                | (startR + (int) (fraction * (endR - startR))) << 16//
                | (startG + (int) (fraction * (endG - startG))) << 8//
                | (startB + (int) (fraction * (endB - startB)));
    }

    /**
     * 进场动画过程监听
     */
    private void addListener(ValueAnimator valueAnimator, final boolean isEnter) {
        valueAnimator.addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                if (isEnter)
                    rootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                if (!isEnter) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

            }
        });
    }


}
