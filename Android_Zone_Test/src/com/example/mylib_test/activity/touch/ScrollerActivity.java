package com.example.mylib_test.activity.touch;

import com.example.mylib_test.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class ScrollerActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_touch_scroller);
		ViewGroup vp = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);
		vp.getChildAt(0);
	}

}
