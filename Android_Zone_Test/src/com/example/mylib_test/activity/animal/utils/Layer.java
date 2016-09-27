package com.example.mylib_test.activity.animal.utils;

import android.graphics.Matrix;

/**
 * Created by Administrator on 2016/9/16.
 * <p>
 * rotation by parent x,y,z
 * 坐标轴都参考 CameraInvert 类；
 *
 *
 */
public class Layer {

//    设计规范 layer.setlocaiton().setPoivet.ro.ro.ro.atttch（parent(location).ro.ro）


    private float z;
    private float px, py;
    private float rX_Degrees, rY_Degrees, rZ_Degrees;
    private Matrix mMatrix;

    private Layer() {
    }

    //可以正负  如果 parent (100,200,-500)  relativeZPosition(-400) 那么我的位置就是 （100,200,-900{parent.z+this.z}）
    public Layer relativeZPosition(float z) {
        this.z = z;
        return this;
    }

    //注意 set此以后 坐标系更改 translate也会因为他改变而改变额  因为又pre操作
    public static Layer setPivot(float px, float py) {
        Layer layer = new Layer();
        layer.px = px;
        layer.py = py;
        return layer;
    }

    public Layer rotationX(float degrees) {
        rX_Degrees = degrees;
        return this;
    }

    public Layer rotationY(float degrees) {
        rY_Degrees = degrees;
        return this;

    }

    public Layer rotationZ(float degrees) {
        rZ_Degrees = degrees;
        return this;
    }

    public Matrix attach(LayerParent parent) {
        mMatrix = new Matrix();

        CameraInvert cameraInvert = new CameraInvert(new ZCamera());
        cameraInvert.setPivot(px, py);

        //parent 旋转 围绕 parent.z
        cameraInvert.translate_3D(0, 0, parent.z + z);

        cameraInvert.rotateX_3D(parent.rX_Degrees, parent.z);
        cameraInvert.rotateY_3D(parent.rY_Degrees, parent.z);
        cameraInvert.rotateZ_3D(parent.rZ_Degrees, parent.z);
        cameraInvert.postConcat(mMatrix);

        //layer旋转；
        cameraInvert.rotateX_3D(rX_Degrees);//更改  2D旋转(不能放3D前面  不然变得跟平面一样)  为啥会变成这样呢 因为都是操作 Matrix, Matrix 是2D的； 那么你更改X旋转即可； matrix没有 所以用camera操作；
        cameraInvert.rotateY_3D(rY_Degrees);//更改  2D旋转(不能放3D前面  不然变得跟平面一样)  为啥会变成这样呢 因为都是操作 Matrix, Matrix 是2D的； 那么你更改X旋转即可； matrix没有 所以用camera操作；
        cameraInvert.rotateZ_3D(rZ_Degrees);//更改  2D旋转(不能放3D前面  不然变得跟平面一样)  为啥会变成这样呢 因为都是操作 Matrix, Matrix 是2D的； 那么你更改X旋转即可； matrix没有 所以用camera操作；
        cameraInvert.postConcat(mMatrix);

        // 相当于给 parent 坐标轴移动 移动到 x,y,z那点；
        cameraInvert.translate_3D(-px + parent.x, -py + parent.y, 0);
        cameraInvert.postConcat(mMatrix);

        return mMatrix;
    }
}
