package com.example.mylib_test.activity.animal.utils.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylib_test.R;

import and.utils.image.compress2sample.SampleUtils;
import and.utils.view.DrawUtils;

/**
 * Created by Administrator on 2016/9/16.
 */
public class MatrixStudy extends View {


    private final Matrix matrix;
    private final Bitmap bm;
    private float[] values;

    public MatrixStudy(Context context) {
        this(context, null);
    }

    public MatrixStudy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixStudy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bm = SampleUtils.load(context, R.drawable.abcd).bitmap();
        values = new float[9];
        matrix = new Matrix();
        matrix.getValues(values);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm, matrix, DrawUtils.getBtPaint());

    }

    private void refresh() {
        matrix.setValues(values);
        invalidate();
    }

    public void set(int postion, float change) {
        values[postion] = change;
        refresh();
    }

}
