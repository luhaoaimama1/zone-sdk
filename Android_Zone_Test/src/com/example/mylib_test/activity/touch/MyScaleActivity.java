package com.example.mylib_test.activity.touch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.example.mylib_test.R;

public class MyScaleActivity extends Activity {
    private Button mButton = null;
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;
    private ScaleGestureDetector mScaleGestureDetector = null;
    private Bitmap mBitmap = null;
    private Matrix mMatrix;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_scalegesture);
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
        mButton = (Button) this.findViewById(R.id.button);
        //按钮监听
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aaaaaaaaaaaab);
                //锁定整个SurfaceView
                Canvas mCanvas = mSurfaceHolder.lockCanvas();
                //画图
                mCanvas.drawBitmap(mBitmap, 0f, 0f, null);
                //绘制完成，提交修改
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                //重新锁一次
                mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //返回给ScaleGestureDetector来处理
        return mScaleGestureDetector.onTouchEvent(event);
    }


    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        private float mScaleFactor;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub

          
            //缩放比例
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 3.0f));
            System.out.println("mScaleFactor"+mScaleFactor);
            mMatrix = new Matrix();
            mMatrix.postScale(mScaleFactor, mScaleFactor,detector.getFocusX(),detector.getFocusY());

            //锁定整个SurfaceView
            Canvas mCanvas = mSurfaceHolder.lockCanvas();
            //清屏
            mCanvas.drawColor(Color.BLACK);
            //画缩放后的图
            mCanvas.drawBitmap(mBitmap, mMatrix, null);
            //绘制完成，提交修改
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            //重新锁一次
            mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            //一定要返回true才会进入onScale()这个函数
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
        }

    }
}