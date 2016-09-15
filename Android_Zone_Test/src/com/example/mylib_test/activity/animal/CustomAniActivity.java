package com.example.mylib_test.activity.animal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.utils.test.CustomAnim;
import com.example.mylib_test.activity.animal.utils.test.Matrix3DAnimTest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomAniActivity extends Activity {

    private ImageView iv;
    private Matrix3DAnimTest  ani = new Matrix3DAnimTest(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animal_custom_ani);
        ButterKnife.bind(this);


        iv = (ImageView) findViewById(R.id.iv);

    }

    @OnClick({R.id.x, R.id.y, R.id.z, R.id.zR, R.id.cX, R.id.cY, R.id.cZ,
            R.id.utils_tran, R.id.utils_xR, R.id.utils_yR, R.id.utils_zR,
            R.id.mx3D_tran, R.id.mx3D_xR, R.id.mx3D_yR, R.id.mx3D_zR})
    public void onClick(View view) {
        CustomAnim ani = new CustomAnim();
//        ani.setRotateX(-30);

        switch (view.getId()) {
            case R.id.x:
                ani.setX(100);
                iv.startAnimation(ani);
                break;
            case R.id.y:
                ani.setY(500);
                iv.startAnimation(ani);
                break;
            case R.id.z:
                ani.setZ(500);
                iv.startAnimation(ani);
                break;
            case R.id.zR:
                iv.animate().rotation(30).start();
                break;
            case R.id.cX:
                ani.setCameraX(3);
                iv.startAnimation(ani);
                break;
            case R.id.cY:
                ani.setCameraY(3);
                iv.startAnimation(ani);
                break;
            case R.id.cZ:
                ani.setCameraZ(-1000);
                iv.startAnimation(ani);
                break;
        }
        utilsTest(view);
        mx3DTest(view);
    }

    @OnClick(R.id.zYR)
    public void onClick() {
        iv.animate().rotationX(30).start();
    }

    public void utilsTest(View view) {
        CustomAnim mCustomAnimUtils = new CustomAnim();
        switch (view.getId()) {
            case R.id.utils_tran:
                iv.startAnimation(mCustomAnimUtils);
                break;
            case R.id.utils_xR:
                mCustomAnimUtils.setRotateX(30);
                iv.startAnimation(mCustomAnimUtils);
                break;
            case R.id.utils_yR:
                mCustomAnimUtils.setRotateY(30);
                iv.startAnimation(mCustomAnimUtils);
                break;
            case R.id.utils_zR:
                mCustomAnimUtils.setRotateZ(30);
                iv.startAnimation(mCustomAnimUtils);
                break;
        }
    }

    public void mx3DTest(View view) {

        switch (view.getId()) {
            case R.id.mx3D_tran:
                ani.setTest(Matrix3DAnimTest.Test.tran);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_xR:
                ani.setTest(Matrix3DAnimTest.Test.rX);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_yR:
                ani.setTest(Matrix3DAnimTest.Test.rY);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_zR:
//                ani.setTest(Matrix3DAnimTest.Test.rZ);
//                ani.setTest(Matrix3DAnimTest.Test.rX_rY);
                ani.setView(iv);
//                ani.setTest(Matrix3DAnimTest.Test.rYSpec);
                ani.setTest(Matrix3DAnimTest.Test.compose);
                iv.startAnimation(ani);
                break;
        }
    }
}
