package com.example.mylib_test.activity.animal.utils.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.mylib_test.activity.animal.utils.CameraInvert;
import com.example.mylib_test.activity.animal.utils.ZCamera;

public class Matrix3DAnimTest extends Animation {

    private final Context context;
    private int mCenterWidth;
    private int mCenterHeight;
    private Camera mCamera = new ZCamera();
    private Test test;
    private View v;

    public Matrix3DAnimTest(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(3000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
//        setInterpolator(new BounceInterpolator());
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setView(View v) {
        this.v = v;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void applyTransformation(
            float interpolatedTime,
            Transformation t) {
        final Matrix matrix = t.getMatrix();
        CameraInvert matrix3D = new CameraInvert(new ZCamera());
        matrix3D.fix3D_MPERSP(context);
        switch (test) {
            case rX:
                matrix3D.rotateX_3D(150 * interpolatedTime);
                break;
            case rY:
                matrix3D.rotateY_3D(30 * interpolatedTime);
                break;
            case rZ:
                matrix3D.rotateZ_3D(30 * interpolatedTime);
                break;
            case tran:
                matrix3D.translate_3D(100 * interpolatedTime, 200 * interpolatedTime, -50 * interpolatedTime);
                break;
            case rX_rY:
                matrix3D.rotateX_3D(30 * interpolatedTime);
                matrix3D.rotateY_3D(30 * interpolatedTime);
                break;
            case rYSpec:

        }
        matrix3D.getMatrix(matrix);
        System.out.println("Matrix3DAnimTest:___" + matrix.toString());
        if (test == Test.rYSpec && v != null) {
            matrix3D.reset();
            matrix3D.rotateY_3D(30 *interpolatedTime,v.getWidth() / 2,v.getHeight()/2);
            matrix3D.postConcat(matrix);
        }
        if(test == Test.compose && v != null){
            matrix3D.reset();
            matrix3D.translate_3D(0,0,-800);
            matrix3D.rotateX_3D(360 *interpolatedTime,v.getWidth() / 2,v.getHeight()/2);
            matrix3D.postConcat(matrix);
        }

        // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
//        matrix.preTranslate(mCenterWidth, mCenterHeight);
//        matrix.postTranslate(-mCenterWidth, -mCenterHeight);
    }

    public enum Test {
        tran, rX, rY, rZ, rX_rY, rYSpec,compose;
    }

}
