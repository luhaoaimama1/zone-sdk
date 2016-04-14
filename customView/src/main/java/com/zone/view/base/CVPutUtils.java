package com.zone.view.base;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Administrator on 2016/4/14.
 * custom view  put  工具类
 */
public class CVPutUtils {
    //  把bt摆放到 view的中心  左,上
    public static float[] putCenterXY(View view, Bitmap bt) {
        return new float[]{(view.getWidth() - bt.getWidth()) / 2, (view.getHeight() - bt.getHeight()) / 2};
    }
}
