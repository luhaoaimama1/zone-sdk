package com.zone.zrflist.headercustom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.zone.zrflist.R;

/**
 * Created by Administrator on 2016/3/31.
 */
public class HeaderCustomDrawable extends Drawable implements Animatable {

    private final Bitmap windmill;
    private final Animation animation;
    private final Matrix matrix;
    private final View parent;

    private boolean  isRunning;
    private boolean isFirstDraw=true;

    public HeaderCustomDrawable(Context context, View parent) {
        this.parent = parent;
        windmill=BitmapFactory.decodeResource(context.getResources(), R.drawable.windmill);
        matrix = new Matrix();
        animation = new RotateAnimation(360, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setFillAfter(true);
    }

    @Override
    public void draw(Canvas canvas) {
        showRight();
        Paint p = new Paint();
        canvas.drawBitmap(windmill, matrix, p);

    }

    private void showRight() {
        //因为默认不写是左上角的起点   这样就是移到中心点了  但是记住为啥减呢 imageview中心点为bitmap的中心点而不是bitmap的左上角的点
        if (isFirstDraw) {
            isFirstDraw = false;
            matrix.setTranslate((getBounds().width() - windmill.getWidth()) / 2, (getBounds().height() - windmill.getHeight()) / 2);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }


    @Override
    public void start() {
        parent.startAnimation(animation);
        isRunning=true;
    }

    @Override
    public void stop() {
        parent.clearAnimation();
        isRunning=false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
    public void postRotation(int degree) {
        showRight();
        matrix.postRotate(degree, getBounds().exactCenterX(), getBounds().exactCenterY());
        invalidateSelf();
    }
}
