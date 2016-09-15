package com.example.mylib_test.activity.animal.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;

/**
 * Created by fuzhipeng on 16/9/15.
 * todo  1.旋转  失真   2.围绕某一点旋转
 */
public class CameraInvert {

    private Camera mCamera;
    private float scale = -1;    // <------- 像素密度

    /**
     * 这个都是以物体 在2D 坐标系的表现形式 位移旋转的;
     *
     * @param mCamera
     */
    public CameraInvert(ZCamera mCamera) {
        this.mCamera = mCamera;
    }

    public CameraInvert translate_3D(float x, float y, float z) {
        mCamera.translate(x, -y, -z);

        return this;
    }

    public void reset() {
        mCamera = new ZCamera();
    }

    public CameraInvert fix3D_MPERSP(Context context) {
        scale = context.getApplicationContext().getResources().getDisplayMetrics().density;

        return this;
    }

    public void getMatrix(Matrix matrix) {

        mCamera.getMatrix(matrix);

        if (scale != -1) {
            // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
            float[] mValues = new float[9];
            matrix.getValues(mValues);              //获取数值
            mValues[6] = mValues[6] / scale;          //数值修正
            mValues[7] = mValues[7] / scale;          //数值修正
            matrix.setValues(mValues);
        }

        if (px != 0 || py != 0) {
            matrix.preTranslate(-px, -py);
            matrix.postTranslate(px, py);
        }
    }

    public void postConcat(Matrix matrix) {
        Matrix tempMatrix = new Matrix();
        getMatrix(tempMatrix);
        matrix.postConcat(tempMatrix);
    }

    public CameraInvert rotateY_3D(float degrees) {
        return rotateY_3D(degrees, 0, 0);

    }

    float px, py;

    public CameraInvert rotateY_3D(float degrees, float px, float py) {
        savePxy(px, py);
        mCamera.rotateY(-degrees);
        return this;

    }

    private void savePxy(float px, float py) {
        this.px = px;
        this.py = py;
    }


    public CameraInvert rotateX_3D(float degrees) {
        return rotateX_3D(degrees, 0, 0);
    }


    public CameraInvert rotateX_3D(float degrees, float px, float py) {
        savePxy(px, py);
        mCamera.rotateX(-degrees);
        return this;
    }

    public CameraInvert rotateZ_3D(float degrees) {
        return rotateZ_3D(degrees, 0, 0);
    }

    public CameraInvert rotateZ_3D(float degrees, float px, float py) {
        savePxy(px, py);
        mCamera.rotateZ(-degrees);
        return this;
    }

    //    x:   z y , ro :x
//
//    y:  z x,  ro:y
//
//    z:    ro:z;
    public CameraInvert rotateZ_3D(float degrees, float px, float py, float pz) {
        return rotateZ_3D(degrees,px,py);
    }
    public CameraInvert rotateX_3D(float degrees, float px, float py,float pz) {


        savePxy(px, py);
        mCamera.rotateX(-degrees);
        return this;
    }


}
