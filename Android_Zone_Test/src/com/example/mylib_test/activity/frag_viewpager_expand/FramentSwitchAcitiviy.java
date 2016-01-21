package com.example.mylib_test.activity.frag_viewpager_expand;

import com.example.mylib_test.R;

import and.utlis.FragmentSwitchUtils;
import and.utlis.FragmentSwitchUtils.BackStatue;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class FramentSwitchAcitiviy extends FragmentActivity implements OnClickListener{

	private FragmentSwitchUtils fragmentSwitch;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.a_frament_switch_test);
		 fragmentSwitch = new FragmentSwitchUtils(this, R.id.framelayout);
		 fragmentSwitch.switchPage(Tab1.class);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open1:
			fragmentSwitch.switchPage(Tab1.class);
			break;
		case R.id.open2:
			fragmentSwitch.switchPage(Tab2.class,android.R.anim.fade_in,android.R.anim.fade_out);
			break;
		case R.id.open3:
			fragmentSwitch.switchPage(Tab3.class);
			break;
		case R.id.open4:
			fragmentSwitch.switchToNull();
			break;

		default:
			break;
		}
	}
}
