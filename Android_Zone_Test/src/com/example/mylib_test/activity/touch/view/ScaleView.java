package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.mylib_test.R;

import and.utils.image.compress2sample.SampleUtils;
import and.utils.data.info.ScreenUtils;

/**
 * //TODO 用这两种手势做个缩放 空间
 * Created by Administrator on 2016/1/30.
 */
public class ScaleView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ScaleView";
    private final Bitmap mBitmap;
    private ScaleGestureDetector mScaleGestureDetector = null;
    private android.graphics.Matrix mMatrix;

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap size = BitmapFactory.decodeResource(getResources(), R.drawable.aaaaaaaaaaaab, options);
//        options.inSampleSize =  Compress_Sample_Utils.calculateInSampleSize(options,
//                ScreenUtils.getScreenPixByResources(activity)[0], ScreenUtils.getScreenPixByResources(activity)[1]);
        options.inSampleSize =  SampleUtils.load().override(ScreenUtils.getScreenPixByResources(context)[0]
                , ScreenUtils.getScreenPixByResources(context)[1]).calculateInSampleSize();
        options.inJustDecodeBounds = false;
        //另外，为了节约内存我们还可以使用下面的几个字段：
        options.inPurgeable = true;//true 内存不足可收回 再次用的时候 重新解码   false则不可收回
        options.inInputShareable = true;//设置是否深拷贝，与inPurgeable结合使用，inPurgeable为false时，该参数无意义。

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aaaaaaaaaaaab,options );
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mMatrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0, new Paint());
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin : " + detector.getScaleFactor());
        invalidate();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleEnd : " + detector.getScaleFactor());

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.d(TAG, "onScale : " + detector.getScaleFactor());
        //缩放比例
        float scale = detector.getScaleFactor();
        mMatrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
        invalidate();
        return true;
    }

    ;
}
