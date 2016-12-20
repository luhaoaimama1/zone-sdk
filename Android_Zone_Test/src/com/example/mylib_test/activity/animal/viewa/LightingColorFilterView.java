package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.mylib_test.R;

import and.utils.image.compress2sample.SampleUtils;
import and.utils.view.DrawUtils;

/**
 * Created by fuzhipeng on 2016/12/19.
 */

public class LightingColorFilterView extends View {
    private final Bitmap mBmp;
    private Paint mPaint;

    public LightingColorFilterView(Context context) {
        super(context);
        mPaint=DrawUtils.getBtPaint();
        mBmp=SampleUtils.load(context, R.drawable.blog2).bitmap();
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        System.out.println("View.isHardwareAccelerated:"+isHardwareAccelerated());
        int width  = 300;
        //这里是为了等比缩放
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        //为什么是ff而不能是01呢 因为A默认值是ff 所以 矩阵相乘的时候  会在mod255
        canvas.translate(0,height);
        mPaint.setColorFilter(new LightingColorFilter(0xffffff,0x0000f0));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        canvas.translate(0,height);
        mPaint.setColorFilter(new LightingColorFilter(0x00ff00,0x000000));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
    }
}
