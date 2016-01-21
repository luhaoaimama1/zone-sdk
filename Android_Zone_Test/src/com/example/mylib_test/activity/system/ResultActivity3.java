package com.example.mylib_test.activity.system;

import com.example.mylib_test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ResultActivity3 extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_returnOne:
			setResult(SystemMainActivity.ResponseCode);
			finish();
			break;

		default:
			break;
		}
	}

}
