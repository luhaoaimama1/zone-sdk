package com.example.mylib_test.activity.animal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.utils.test.CustomAnim;
import com.example.mylib_test.activity.animal.utils.test.Matrix3DAnimTest;

import butterknife.ButterKnife;

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


    public void onClick(View view) {
        CustomAnim ani = new CustomAnim();
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

            case R.id.utils_xR:
                ani.setRotateX(30);
                iv.startAnimation(ani);
                break;
            case R.id.utils_yR:
                ani.setRotateY(30);
                iv.startAnimation(ani);
                break;
            case R.id.utils_zR:
                ani.setRotateZ(30);
                iv.startAnimation(ani);
                break;
        }
        test2D(view);
        mx3DTest(view);
    }



    public void test2D(View view) {
        switch (view.getId()) {
            case R.id.zR:
                iv.animate().rotation(30).start();
                break;
            case R.id.zYR:
                iv.animate().rotationX(30).start();
                break;

        }
    }

    public void mx3DTest(View view) {

        switch (view.getId()) {
            case R.id.mx3D_tran:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.compose);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_tran2:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.compose2);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_xR:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.composeX);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_yR:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.composeY);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D_zR:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.composeZ);
                iv.startAnimation(ani);
                break;
            case R.id.mx3D:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.compose3);
                iv.startAnimation(ani);
                break;
            case R.id.layer:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.layer);
                iv.startAnimation(ani);
                break;
            case R.id.layer2:
                ani.setView(iv);
                ani.setTest(Matrix3DAnimTest.Test.layer2);
                iv.startAnimation(ani);
                break;
        }
    }
}
