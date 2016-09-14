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
    @Bind(R.id.zY)
    Button ZY;
    @Bind(R.id.cX)
    Button cX;
    @Bind(R.id.cY)
    Button cY;
    @Bind(R.id.cZ)
    Button cZ;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_animal_custom_ani);
        ButterKnife.bind(this);


        iv = (ImageView) findViewById(R.id.iv);

    }

    @OnClick({R.id.x, R.id.y, R.id.z, R.id.z_, R.id.zR,R.id.cX,R.id.cY,R.id.cZ})
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
                ani.setCameraZ(-100);
                iv.startAnimation(ani);
                break;
        }
    }

    @OnClick(R.id.zY)
    public void onClick() {
        iv.animate().translationY(100).start();
    }

}
