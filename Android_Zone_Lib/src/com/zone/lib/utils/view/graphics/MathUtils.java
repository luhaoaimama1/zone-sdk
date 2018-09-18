package com.zone.lib.utils.view.graphics;

/**
 * Created by fuzhipeng on 2016/11/15.
 */

public class MathUtils {
//    t, tMin, tMax, value1, value2
    public static float linearMap(float srcNow, float src1, float src2, float dst1, float dst2){
        return  (srcNow-src1)*(dst2-dst1)/(src2-src1)+dst1;
    }
    public static double linearMap(double srcNow, double src1, double src2, double dst1, double dst2){
        return  (srcNow-src1)*(dst2-dst1)/(src2-src1)+dst1;
    }
}
