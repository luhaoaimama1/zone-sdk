package com.example.mylib_test.activity.pop_dialog;

import and.log.ToastUtils;
import view.DialogCustemZone;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Bottom;
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Photo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Dialog_Pop_Adapter_MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_pop_dialog_adapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pop:
//			PopCus pc=new PopCus(this, R.layout.poptest);
//			pc.showPop();
			Pop_Photo pop=new Pop_Photo(this, R.id.flowLayoutZone1);
			pop.show();
			break;
		case R.id.pop_bottom:
//			PopCus pc=new PopCus(this, R.layout.poptest);
//			pc.showPop();
			Pop_Bottom pop_bottom=new Pop_Bottom(Dialog_Pop_Adapter_MainActivity.this,R.id.pop_bottom);
			pop_bottom.show();
			break;
		case R.id.dialog:
			//dialog自定义测试
			DialogCustemZone dcz=new DialogCustemZone(this) {
				
				@Override
				public void notSure() {
					toToast("这个is not OK!");
				}
				
				@Override
				public void isSure() {
					toToast("这个is OK,流逼！");
				}

				@Override
				public void addSetProperty(Builder db) {
					db.setIcon(R.drawable.ic_launcher);
				}
				
			};
			break;
		case R.id.textGaoLiang:
			//点击文字高粱等效果
			Button bt = (Button) findViewById(R.id.textGaoLiang);
			Spannable span = new SpannableString("I am what are you doing，fucking!");
			span.setSpan(new AbsoluteSizeSpan(20), 1, 16,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//字体大小
			span.setSpan(new ForegroundColorSpan(Color.RED), 5, 16,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//前景色
			span.setSpan(new BackgroundColorSpan(Color.YELLOW), 11, 16,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//背景色
			bt.setText(span);
//			bt.setText(Html.fromHtml(  "<font color=#E61A6B>红色代码</font> "+
//			"<I><font color=#1111EE>蓝色斜体代码</font></I>"+"<u><i><font color=#1111EE>蓝色斜体加粗体下划线代码</font></i></u>"));
			break;

		default:
			break;
		}
	}
	public void toToast(String str) {
		ToastUtils.showShort(this, str);
	}
}
