package com.example.mylib_test.activity.animal.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;

/**
 * Created by fuzhipeng on 16/9/15.
 *
 *
 * 都是已 左手坐标系为基准（旋转，位移） ，x右边，y下，通过左手坐标系判断，Z就知道了  向外，非朝向手机里面；
 */
public class CameraInvert {

    private Camera mCamera;

    /**
     * The specified dimension is an absolute number of pixels.
     */
    private static final int ABSOLUTE = 0;

    /**
     * The specified dimension holds a float and should be multiplied by the
     * height or width of the object being animated.
     */
    private static final int RELATIVE_TO_SELF = 1;


    private int mode = RELATIVE_TO_SELF;

    //支持  围绕 z 点的旋转
    private float tz;


    public CameraInvert(ZCamera mCamera) {
        this.mCamera = mCamera;
    }

    public CameraInvert translate_3D(float x, float y, float z) {
        this.tz = -z;
        mCamera.translate(x, -y, -z);
        return this;
    }

    private CameraInvert translate_(float x, float y, float z) {

        mCamera.translate(x, -y, -z);

        return this;
    }

    public void reset() {
        resetZCamera();
        px =py = 0;
    }

    public void resetZCamera() {
        mCamera = new ZCamera();
        degrees3DY= degrees3DX= 0;
        tz = 0;
    }


    public void getMatrix(Matrix matrix) {
        getMatrix_fix3D_MPERSP(matrix, null);

    }

    public void getMatrix_fix3D_MPERSP(Matrix matrix, Context context) {


        if (mode == RELATIVE_TO_SELF) {
            mCamera.getMatrix(matrix);
            if (context != null) {

                // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
                float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;

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
        } else {
            if (px != 0 || py != 0 || pz != 0) {

                computeA(degrees3DX, degrees3DY);
                mCamera.getMatrix(matrix);

                if (context != null) {
                    float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
                    // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
                    float[] mValues = new float[9];
                    matrix.getValues(mValues);              //获取数值
                    mValues[6] = mValues[6] / scale;          //数值修正
                    mValues[7] = mValues[7] / scale;          //数值修正
                    matrix.setValues(mValues);
                }

                matrix.preTranslate(-px, -py);
                matrix.postTranslate(px, py);

            }
        }
        //还原操作  不然每次的操作都叠加了
        resetZCamera();
    }

    public void postConcat(Matrix matrix) {
        postConcat_fix3D_MPERSP(matrix, null);
    }

    public void postConcat_fix3D_MPERSP(Matrix matrix, Context context) {
        Matrix tempMatrix = new Matrix();
        getMatrix_fix3D_MPERSP(tempMatrix, context);
        matrix.postConcat(tempMatrix);
    }

    public void preConcat(Matrix matrix) {
        preConcat_fix3D_MPERSP(matrix, null);
    }


    public void preConcat_fix3D_MPERSP(Matrix matrix, Context context) {
        Matrix tempMatrix = new Matrix();
        getMatrix_fix3D_MPERSP(tempMatrix, context);
        matrix.preConcat(tempMatrix);
    }


    float px, py, pz;

    private void savePxy_Z(float pz) {
        mode = ABSOLUTE;
        this.pz = -pz;
    }

    //注意 set此以后 坐标系更改 translate也会因为他改变而改变额  因为又pre操作
    public CameraInvert setPivot(float px, float py) {
        this.px = px;
        this.py = py;
        return this;
    }

    public CameraInvert rotateY_3D(float degrees) {
        mode = RELATIVE_TO_SELF;
        mCamera.rotateY(-degrees);
        return this;

    }


    public CameraInvert rotateX_3D(float degrees) {
        mode = RELATIVE_TO_SELF;
        mCamera.rotateX(-degrees);
        return this;
//        return rotateX_3D(degrees,0);
    }


    public CameraInvert rotateZ_3D(float degrees) {
        mode = RELATIVE_TO_SELF;
        mCamera.rotateZ(-degrees);
        return this;
    }


    public CameraInvert rotateZ_3D(float degrees, float pz) {
        savePxy_Z(pz);
        mCamera.rotateZ(-degrees);
        return this;
    }

    private float degrees3DY, degrees3DX;

    public CameraInvert rotateX_3D(float degrees, float pz) {
        savePxy_Z(pz);
        mCamera.rotateX(-degrees);
        degrees3DY =0;
        degrees3DX = degrees;
        return this;
    }

    public CameraInvert rotateY_3D(float degrees, float pz) {
        savePxy_Z(pz);
        mCamera.rotateY(-degrees);
        degrees3DX = 0;
        degrees3DY = degrees;
        return this;

    }

    //只能一个一个计算； 因为不能同时计算  mCamera.rotateY(-degrees);会出现问题的
    private void computeA(float rx, float ry) {
        float rZlength = pz - tz;
        //rx
        double y = Math.sin(Math.toRadians(rx)) * rZlength;
        double z = (1 - Math.cos(Math.toRadians(rx))) * rZlength;
        //ry
        double x1 = Math.sin(Math.toRadians(ry)) * rZlength;
        double z1 = (1 - Math.cos(Math.toRadians(ry))) * rZlength;

        float totolY = (float) -y;
        float totolX = (float) x1;
        float totolZ = (float) (z + z1);

        System.out.println("totolX:_" + totolX + "\t totolY:" + totolY + "\t z:" + z + "\t z1:" + z1
                + "\t totolZ:" + totolZ + "\t rx:" + rx + "\t ry:" + ry + "\t rZlength:" + -rZlength);
        translate_(totolX, totolY, totolZ);
    }


}
