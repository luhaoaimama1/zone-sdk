package com.example.mylib_test.activity.animal.viewa;

import android.graphics.Camera;

/**
 * Created by fuzhipeng on 16/9/14.
 */
public class CameraInverseUtils {
    private Camera mCamera;

    public CameraInverseUtils(Camera mCamera) {
        this.mCamera = mCamera;
    }


    public void translate(float x, float y, float z){
        mCamera.translate(-x,y,-z);
    }
}
