package com.example.mylib_test.activity.custom_view;


import com.example.mylib_test.R;

import android.app.Activity;
import android.os.Bundle;

public class AndroidHeroActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String temp = getIntent().getStringExtra("type");
		if("circle".equals(temp)){
			setContentView(R.layout.a_hero_circle);
		}else if("circle2".equals(temp)){
			setContentView(R.layout.a_hero_voice);
		}else{
			setContentView(R.layout.a_hero_scroll);
		}
		
	}

}
