package com.example.mylib_test.activity.animal.utils.test;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

import com.example.mylib_test.activity.animal.utils.ZCamera;

public class CustomAnim extends Animation {

    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new ZCamera();
    private float mRotateX = 0.0f, mRotateY = 0.0f, mRotateZ = 0.0f;
    private float mX = 0.0f, mY = 0.0f, mZ = 0.0f;

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
    public void setRotateY(float rotateY) {
        mRotateY = rotateY;
    }

    public void setRotateX(float rotateX) {
        this.mRotateX = rotateX;
    }

    public void setRotateZ(float rotateZ) {
        this.mRotateZ = rotateZ;
    }


    // 暴露接口-设置旋转角度
    public void setX(float x) {
        clear();
        mX = x;

    }

    private void clear() {
        mX = mZ = mY = 0;
    }

    // 暴露接口-设置旋转角度
    public void setY(float y) {
        clear();
        mY = y;
    }

    // 暴露接口-设置旋转角度
    public void setZ(float z) {
        clear();
        mZ = z;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void applyTransformation(
            float interpolatedTime,
            Transformation t) {
        final Matrix matrix = t.getMatrix();
        mCamera.save();

        mCamera.translate(mX * interpolatedTime, mY * interpolatedTime, mZ * interpolatedTime);

        mCamera.rotateX(mRotateX * interpolatedTime);
        mCamera.rotate(0, mRotateY * interpolatedTime, 0);
        mCamera.rotateZ(mRotateZ * interpolatedTime);

        // 将旋转变换作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        System.out.println("mRotateX:___" + mRotateX + "\t mRotateY:" + mRotateY + "\t mRotateZ:" + mRotateZ);
        System.out.println("camera x:" + mCamera.getLocationX() + "\t y:" + mCamera.getLocationY() + "\t z:" + mCamera.getLocationZ());
        System.out.println("Matrix:___" + matrix.toString());


        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
//        matrix.preTranslate(mCenterWidth, mCenterHeight);
//        matrix.postTranslate(-mCenterWidth, -mCenterHeight);
    }
}
