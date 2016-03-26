package com.example.mylib_test.activity.system;

import and.base.activity.BaseActvity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.mylib_test.R;


public class ResultBase1 extends BaseActvity{
	Button bt_returnOne;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_result3);
	}

	@Override
	public void findIDs() {
		bt_returnOne=(Button) findViewById(R.id.bt_returnOne);
		bt_returnOne.setText("跳转到另一个activity  那个点击特殊返回则刷新");
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void setListener() {
		
	}

	@Override
	public void backRefresh() {
		System.err.println("ResultBase1__________backRefresh");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_returnOne:
			startActivityWithRefresh(new Intent(this,ResultBase2.class));
			break;

		default:
			break;
		}
		super.onClick(v);
	}

}
