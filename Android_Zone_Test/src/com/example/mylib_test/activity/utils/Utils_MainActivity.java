package com.example.mylib_test.activity.utils;

import com.example.mylib_test.R;
import and.utils.text_edit2input.KeyBoardUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Utils_MainActivity extends Activity implements OnClickListener{
	private EditText keyboard;
	private View view1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_utils_test);
		keyBoardTest();
		

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Log.e("Utils_MainActivity:――――――――-" + "  DisplayMetrics", "density=" + density + "; densityDPI=" + densityDpi);  
        float tem = getResources().getDimension(R.dimen.test);
        System.err.println(tem);
	
	}

	private void keyBoardTest() {
		keyboard=(EditText) findViewById(R.id.keyboard);
		view1=findViewById(R.id.view1);
		new KeyBoardUtils() {
			
			@Override
			public void openState(int keyboardHeight) {
				System.out.println("键盘：openState 高度:"+keyboardHeight);
			}
			
			@Override
			public void closeState(int keyboardHeight) {
				System.out.println("键盘：closeState 高度:"+keyboardHeight);
			}
		}.monitorKeybordState(findViewById(R.id.flowLayoutZone1));;	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.openKeyboard:
			KeyBoardUtils.openKeybord(keyboard, this);
			break;
		case R.id.closeKeyboard:
			KeyBoardUtils.closeKeybord(keyboard, this);
			break;
		case R.id.bitmapTest:
			startActivity(new Intent(this,SoftTestActivity.class));
			break;
		case R.id.getPhone:
			startActivity(new Intent(this,GetPhoneTest.class));
			break;

		default:
			break;
		}
		
	}

}
