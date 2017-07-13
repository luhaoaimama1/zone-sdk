package com.zone.lib.utils.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.view.animation.LinearInterpolator;
import com.nineoldandroids.animation.ValueAnimator;
import com.zone.lib.utils.view.DrawUtils;

/**
 * +------------------------+
 * |<--wave length->        |_________
 * |   /\          |   /\   |  |   |
 * |  /  \         |  /  \  |  |  amplitude
 * | /    \        | /    \ |  |   |
 * |/      \       |/      \|  |  _|__
 * |        \      /        |  |
 * |         \    /         |  |
 * |          \  /          |  |
 * |           \/           | water level
 * |                        |  |
 * |                        |  |
 * +------------------------+__|____
 * 参考：https://github.com/race604/WaveLoading
 */
public class WaveHelper implements ValueAnimator.AnimatorUpdateListener {
    private int width;
    private int height;
    private Canvas canvas;
    private ValueAnimator valueAnimator;
    Bitmap waveBitmap;

    //我要制造波浪 需要知道rect
    private float mLevelProgress = 0.5F;
    //pixels
    private float mAmplitude;
    private float mLength;
    private float mSpeed;// pix/s
    private Paint mPaint;
    private float speedOffsetX;
    private float offsetXRadioOfLength;


    private RefreshCallback callback;

    public WaveHelper(int width, int height, RefreshCallback callback) {
        this.callback = callback;
        this.width = width;
        this.height = height;
        initValue();

        waveBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(waveBitmap);
        mPaint = DrawUtils.getStrokePaint(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        startAnimator();
    }

    private void initValue() {
        this.mLength = width * 2F / 3;
        this.mAmplitude = mLength / 8;
        this.mSpeed = 300;
    }

    private void startAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }


    private Bitmap getWave() {
        int count = (int) Math.ceil(width / mLength);

        canvas.drawColor(Color.YELLOW, PorterDuff.Mode.CLEAR);

        Path path = new Path();
        path.moveTo(0, 0);
        for (int i = 0; i < count * 2; i++) {
            path.quadTo(mLength / 4 + i * mLength, mAmplitude * 2, mLength / 2 + i * mLength, 0);
            path.quadTo(mLength * 3 / 4 + i * mLength, -mAmplitude * 2, mLength + i * mLength, 0);
        }
        //rectf.height()+mAmplitude 是因为进度为100的时候 下面不会出现暴露的假象
        path.rLineTo(0, height + mAmplitude);
        path.rLineTo(-count * 2 * mLength, 0);
        path.close();


        //弄到进度为0的位置
        path.offset(0, height + mAmplitude);
        //通过进度计算应该往上偏移多少
        float progressOffset = (height + mAmplitude * 2) * mLevelProgress;
        path.offset(0, -progressOffset);

        //计算水平速度
        path.offset(-speedOffsetX - offsetXRadioOfLength * mLength, 0);
        canvas.drawPath(path, mPaint);
        return waveBitmap;
    }

    public float getSpeed() {
        return mSpeed;
    }

    //mSpeed=pix/s
    public void setSpeed(float mSpeed) {
        this.mSpeed = mSpeed;
        refreshWithNoStart();
    }

    public float getAmplitude() {
        return mAmplitude;
    }

    public void setAmplitude(float mAmplitude) {
        this.mAmplitude = mAmplitude;
        refreshWithNoStart();
    }

    public float getOffsetXRadioOfLength() {
        return offsetXRadioOfLength;
    }

    public void setOffsetXRadioOfLength(float offsetXRadioOfLength) {
        this.offsetXRadioOfLength = offsetXRadioOfLength % 1;
        refreshWithNoStart();
    }

    public float getLength() {
        return mLength;
    }

    //最多
    public void setLength(float mLength) {
        //如果长度为宽切成10段还比8大 可以继续切知道8，如果比8小就停止；
        float value = width * 1F / 10;
        float min = Math.min(8, value);
        if (mLength <= min)
            this.mLength = min;
        else
            this.mLength = mLength;
        refreshWithNoStart();
    }

    private void refreshWithNoStart() {
        if (!valueAnimator.isRunning() && callback != null)
            callback.refresh(getWave());
    }

    public float getLevelProgress() {
        return mLevelProgress;
    }

    public void setLevelProgress(float mLevelProgress) {
        this.mLevelProgress = mLevelProgress;
        refreshWithNoStart();
    }

    public Bitmap getBitmap() {
        return waveBitmap;
    }

    private long mLastTime;

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        long currentTime = SystemClock.uptimeMillis();
        long costTime = currentTime - mLastTime;
        mLastTime = currentTime;

        speedOffsetX += mSpeed * costTime / 1000;
        if (speedOffsetX >= mLength)
            speedOffsetX = speedOffsetX % mLength;

        if (callback != null)
            callback.refresh(getWave());
        else
            getWave();

    }

    public void cancel() {
        if (valueAnimator.isRunning())
            valueAnimator.cancel();
    }

    public void start() {
        if (!valueAnimator.isRunning())
            valueAnimator.start();
    }


    public interface RefreshCallback {
        void refresh(Bitmap wave);
    }
}