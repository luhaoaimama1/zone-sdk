package com.example.mylib_test.activity.animal;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.viewa.CustomAnim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CustomAniActivity extends Activity{

	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_animal_custom_ani);
		
		
		iv=(ImageView) findViewById(R.id.iv);
		CustomAnim ani = new CustomAnim();
		ani.setRotateX(-30);
		iv.startAnimation(ani);
	}
}
