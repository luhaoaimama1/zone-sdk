package com.example.mylib_test.activity.animal.viewa;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

public class CustomAnim extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;
    private float mX = 0.0f, mY = 0.0f, mZ = 0.0f
            , cX = 0.0f, cY = 0.0f, cZ = 0.0f;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(2000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
//        setInterpolator(new BounceInterpolator());
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    // 暴露接口-设置旋转角度
    public void setRotateX(float rotateY) {
        mRotateY = rotateY;
    }

    // 暴露接口-设置旋转角度
    public void setX(float x) {
        mX = x;
    }

    // 暴露接口-设置旋转角度
    public void setY(float y) {
        mY = y;
    }

    // 暴露接口-设置旋转角度
    public void setZ(float z) {
        mZ = z;
    }

    // 暴露接口-设置旋转角度
    public void setCameraX(float x) {
        cX = x;
        cZ=cY=0;
    }
    // 暴露接口-设置旋转角度
    public void setCameraY(float x) {
        cY = x;
        cZ=cX=0;
    }
    // 暴露接口-设置旋转角度
    public void setCameraZ(float x) {
        cZ = x;
        cY=cX=0;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void applyTransformation(
            float interpolatedTime,
            Transformation t) {
        final Matrix matrix = t.getMatrix();
        mCamera.save();
        if (Math.floor(cX) == 0&&Math.floor(cY) == 0&&Math.floor(cZ) == 0)
            mCamera.translate(mX * interpolatedTime, mY * interpolatedTime, mZ * interpolatedTime);
        else
            mCamera.setLocation(cX*interpolatedTime,cY*interpolatedTime,-8+cZ*interpolatedTime);
//        mCamera.translate(0,0,100*interpolatedTime);
        // 使用Camera设置旋转的角度
//        mCamera.rotateX(mRotateY * interpolatedTime);
        // 将旋转变换作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        System.out.println("camera x:" + mCamera.getLocationX() + "\t y:" + mCamera.getLocationX() + "\t z:" + mCamera.getLocationZ());
        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
//        matrix.preTranslate(mCenterWidth, mCenterHeight);
//        matrix.postTranslate(-mCenterWidth, -mCenterHeight);
    }
}
