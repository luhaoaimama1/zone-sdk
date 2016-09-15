package com.example.mylib_test.activity.animal.utils;

import android.graphics.Camera;

/**
 * Created by fuzhipeng on 16/9/15.
 *
 *  更正  Camera 坐标系;
 */
public class ZCamera extends Camera {
    @Override
    public void rotateY(float deg) {
        super.rotateY(-deg);
    }

    @Override
    public void rotate(float x, float y, float z) {
        super.rotate(-x, -y, z);
    }

    @Override
    public void rotateX(float deg) {
        super.rotateX(-deg);
    }
}
