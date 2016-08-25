package com.example.mylib_test.activity.system;

import com.example.mylib_test.R;

import and.utils.activity_fragment_ui.ToastUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SystemMainActivity extends Activity implements OnClickListener{
	public static int ResponseCode=11111;
	public static int RequestCode=22222;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_system_test);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;//true创建的菜单才能显示
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			
			break;
		case R.id.sb:
			
			break;

		default:
			break;
		}
		ToastUtils.showLong(this, "干啥啊别碰我");
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_activityResult:
			startActivityForResult(new Intent(this,ResultActivity2.class), SystemMainActivity.RequestCode);
			break;
		case R.id.bt_baseResult:
			startActivityForResult(new Intent(this,ResultBase1.class), SystemMainActivity.RequestCode);
			break;
		case R.id.bt_webView:
			startActivity(new Intent(this,WebViewAcitivity.class));
			break;
		case R.id.crash:
			throw new NullPointerException("crash test");
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("first onActivityResult____ requestCode:"+requestCode+"\t resultCode"+resultCode);
	}

}
