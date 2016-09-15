package com.example.mylib_test.activity.animal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.viewa.CustomAnim;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomAniActivity extends Activity {

    @Bind(R.id.x)
    Button x;
    @Bind(R.id.y)
    Button y;
    @Bind(R.id.z)
    Button z_;
    @Bind(R.id.z_)
    Button z;
    @Bind(R.id.zR)
    Button zR;
    @Bind(R.id.zYR)
    Button zYR;
    @Bind(R.id.cX)
    Button cX;
    @Bind(R.id.cY)
    Button cY;
    @Bind(R.id.cZ)
    Button cZ;
    @Bind(R.id.utils_tran)
    Button utilsTran;
    @Bind(R.id.utils_xR)
    Button utilsXR;
    @Bind(R.id.utils_yR)
    Button utilsYR;
    @Bind(R.id.utils_zR)
    Button utilsZR;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animal_custom_ani);
        ButterKnife.bind(this);


        iv = (ImageView) findViewById(R.id.iv);

    }

    @OnClick({R.id.x, R.id.y, R.id.z, R.id.z_, R.id.zR, R.id.cX, R.id.cY, R.id.cZ,
            R.id.utils_tran, R.id.utils_xR, R.id.utils_yR, R.id.utils_zR})
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
            case R.id.z_:
                ani.setZ(-500);
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
    }

    @OnClick(R.id.zYR)
    public void onClick() {
        iv.animate().rotationX(30).start();
    }

    public void utilsTest(View view) {
        switch (view.getId()) {
            case R.id.utils_tran:
                break;
            case R.id.utils_xR:
                break;
            case R.id.utils_yR:
                break;
            case R.id.utils_zR:
                break;
        }
    }
}
