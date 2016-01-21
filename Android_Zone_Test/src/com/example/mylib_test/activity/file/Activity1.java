package com.example.mylib_test.activity.file;

import android.app.Activity;
import android.content.Intent;

public class Activity1 extends Activity{
	
	private boolean condition=true;
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("Activity1====onStart");
	}
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("Activity1====onResume");
		if (condition) {
			startActivity(new Intent(this, Activity2.class));
			condition=false;
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("Activity1====onStop");
	}
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("Activity1====onPause");
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("Activity1====onRestart");
	}

}
