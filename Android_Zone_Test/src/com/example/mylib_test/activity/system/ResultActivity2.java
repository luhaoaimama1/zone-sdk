package com.example.mylib_test.activity.system;

import com.example.mylib_test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ResultActivity2 extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result2);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_toThird:
			startActivityForResult(new Intent(this, ResultActivity3.class),SystemMainActivity.RequestCode);
			break;
		case R.id.bt_returnOne:
			setResult(SystemMainActivity.ResponseCode);
			finish();
			break;
			
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("second onActivityResult____ requestCode:"+requestCode+"\t resultCode"+resultCode);
	}
	

}
