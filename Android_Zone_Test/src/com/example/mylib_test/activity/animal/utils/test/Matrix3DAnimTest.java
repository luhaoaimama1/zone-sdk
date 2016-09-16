package com.example.mylib_test.activity.animal.utils.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.mylib_test.activity.animal.utils.CameraInvert;
import com.example.mylib_test.activity.animal.utils.Layer;
import com.example.mylib_test.activity.animal.utils.LayerParent;
import com.example.mylib_test.activity.animal.utils.ZCamera;

public class Matrix3DAnimTest extends Animation {

    private final Context context;
    private int mCenterWidth;
    private int mCenterHeight;

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
        ZCamera mCamera = new ZCamera();
        final Matrix matrix = t.getMatrix();
        CameraInvert matrix3D = new CameraInvert(mCamera);
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
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateY_3D(30 * interpolatedTime);
            matrix3D.postConcat(matrix);
        }
        if (test == Test.composeY && v != null) {
            matrix3D.reset();
            matrix3D.translate_3D(0, 0, 0);//为啥 我移动也会以这个锚点为坐标系移动？
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateY_3D(360 * interpolatedTime, -800);
            matrix3D.postConcat(matrix);
        }
        if (test == Test.composeX && v != null) {
            matrix3D.reset();

            matrix3D.translate_3D(0, 0, 0);
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateX_3D(360 * interpolatedTime, -200);
//            matrix3D.rotateX_3D(-180 ,v.getWidth() / 2,v.getHeight()/2,-200);
            matrix3D.postConcat(matrix);
        }
        if (test == Test.composeZ && v != null) {
            matrix3D.reset();
            matrix3D.translate_3D(0, 0, -800);
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateZ_3D(360 * interpolatedTime, 0);
            matrix3D.postConcat(matrix);
        }
        if (test == Test.compose && v != null) {
            matrix3D.reset();
            matrix3D.translate_3D(0, 0, -800);

            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateX_3D(30, 0);//这个旋转会更改本身的坐标系   所以下面的旋转都会被他影响；postConcat之后就不会了
            matrix3D.rotateY_3D(360 * interpolatedTime, -1600);

            matrix3D.postConcat(matrix);

            matrix3D.rotateZ_3D(30 * interpolatedTime, 0);//更改  2D旋转(不能放3D前面  不然变得跟平面一样)  为啥会变成这样呢 因为都是操作 Matrix, Matrix 是2D的； 那么你更改X旋转即可； matrix没有 所以用camera操作；
            matrix3D.postConcat(matrix);

        }
        if (test == Test.compose2 && v != null) {
            matrix3D.reset();
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);

            matrix3D.translate_3D(0, 0, -800);
            matrix3D.rotateY_3D(360 * interpolatedTime, -1600);
            matrix3D.postConcat(matrix);// 围绕 3D Z旋转；

////
            matrix3D.rotateX_3D(180 * interpolatedTime, 0);//更改  2D旋转(不能放3D前面  不然变得跟平面一样)  为啥会变成这样呢 因为都是操作 Matrix, Matrix 是2D的； 那么你更改X旋转即可； matrix没有 所以用camera操作；
            matrix3D.postConcat(matrix);


            matrix3D.translate_3D(v.getWidth() / 2, 0, -800);//坐标轴移动
            matrix3D.postConcat(matrix);

//            matrix3D.setPivot(0,0);
//            matrix3D.rotateY_3D(80* interpolatedTime);//坐标轴旋转
//            matrix3D.postConcat(matrix);


        }
        if (test == Test.compose3 && v != null) {
//
            matrix3D.reset();
            matrix3D.setPivot(v.getWidth() / 2, v.getHeight() / 2);
            matrix3D.rotateY_3D(360 * interpolatedTime, -600);

            matrix3D.translate_3D(v.getWidth() / 2, 0, 0);
            matrix3D.postConcat(matrix);

        }

        if (test == Test.layer && v != null) {

            matrix.set(
                    Layer.setPivot(v.getWidth() / 2, v.getHeight() / 2)
//                            .setPosition(100, 50, 400)
                            .relativeZPosition(800) //位置 -1600+800=-800 围绕-1600 旋转
//                            .rotationX(30)
//                            .rotationY(30* interpolatedTime)
                            .rotationZ(30 * interpolatedTime)
                            .attach(
//                                  Parent.setPosition(v.getWidth() / 2+100 , v.getHeight() / 2 +50, -1600)
                                    LayerParent.setPosition(v.getWidth() / 2, v.getHeight() / 2, -1600)
                                            .rotationY(360 * interpolatedTime)
                                            .rotationX(30)
                            )
            );

        }

        if (test == Test.layer2 && v != null) {

            matrix.set(
                    Layer.setPivot(v.getWidth() / 2, v.getHeight() / 2)
//                            .setPosition(100, 50, 400)
                            .relativeZPosition(800) //位置 -1600+800=-800 围绕-1600 旋转
                            .rotationX(180 * interpolatedTime)
//                            .rotationY(30* interpolatedTime)
//                            .rotationZ(30 * interpolatedTime)
                            .attach(
                                    LayerParent.setPosition(v.getWidth() / 2+100 , v.getHeight() / 2 +50, -1600)
                                            .rotationY(360 * interpolatedTime)
//                                            .rotationX(30)
                            )
            );

        }

    }

    public enum Test {
        tran, rX, rY, rZ, rX_rY, rYSpec,
        compose, compose2, composeX, composeZ, composeY, compose3,
        layer,layer2;
    }

}
