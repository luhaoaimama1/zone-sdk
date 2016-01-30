package com.example.mylib_test.activity.touch;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.touch.view.TouchExampleView;

import android.app.Activity;
import android.os.Bundle;

public class VeclocityTrackerActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.veclocity);
//		setContentView(new TouchExampleView(this,null,0));
	}

}
