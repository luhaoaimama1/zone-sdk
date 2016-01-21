package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;

import view.LabelView;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LabelTestActivity extends Activity implements OnClickListener{
	private LabelView labelView1;
	private boolean i=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_label_test);
		labelView1=(LabelView) findViewById(R.id.labelView1);
		labelView1.addBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.a1));
		labelView1.addBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_launcher));
		//设置边框是否显隐
//		labelView1.setRimVisibility(false);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.labelView1:
			if (i) {
				//这里注释是 控件的ontouch 貌似给他阻拦了  虽然里面的条件还没有判断 但是他比较晕  所以 给阻拦了
//				labelView1.addBitmap(BitmapFactory.decodeResource(
//						getResources(), R.drawable.a1));
//				labelView1.addBitmap(BitmapFactory.decodeResource(
//						getResources(), R.drawable.ic_launcher));
				i=false;
			}
			break;

		default:
			break;
		}
		
	}
	
}
