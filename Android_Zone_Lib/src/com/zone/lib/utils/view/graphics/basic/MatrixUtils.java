package com.zone.lib.utils.view.graphics.basic;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Created by fuzhipeng on 16/9/30.
 */

public class MatrixUtils {
    /**
     * @return 顺时针的四个顶点
     */
    public static float[] getVertices(RectF rectf) {
        return getVertices(rectf.left, rectf.top, rectf.right, rectf.bottom);
    }

    /**
     * @return 顺时针的四个顶点
     */
    public static float[] getVertices(float left, float top, float right, float bottom) {
        return new float[]{
                left, top,
                right, top,
                right, bottom,
                left, bottom
        };
    }

    public static Matrix adjustMatrix(Context context, Matrix matrix) {
        float scale = 1;    // <------- 像素密度
        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;
        float[] mValues = new float[9];
        matrix.getValues(mValues);                //获取数值
        mValues[6] = mValues[6] / scale;            //数值修正  发现这个数是负数就得修正;
        mValues[7] = mValues[7] / scale;            //数值修正
        matrix.setValues(mValues);                //重新赋值
        return matrix;
    }

    public static Matrix adjustClipMatrix(Context context, Matrix matrix) {
        float scale = 1;    // <------- 像素密度
        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;
        float[] mValues = new float[9];
        matrix.getValues(mValues);                //获取数值
//        if (mValues[6] < 0)
            mValues[6] =0;            //数值修正  发现这个数是负数就得修正;
//        if (mValues[7] < 0)
            mValues[7] = 0;            //数值修正
        matrix.setValues(mValues);                //重新赋值
        return matrix;
    }

}
