package com.example.mylib_test.activity.frag_viewpager_expand;

import com.example.mylib_test.R;

import and.utils.FragmentSwitcher;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class FramentSwitchAcitiviy extends FragmentActivity implements OnClickListener{


	private FragmentSwitcher fragmentSwitcher;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.a_frament_switch_test);

		fragmentSwitcher=new FragmentSwitcher(this, R.id.framelayout);
		fragmentSwitcher.setPriDefaultAnimal(android.R.anim.fade_in,android.R.anim.fade_out);
//		fragmentSwitcher.initFragment(Tab1.class, Tab2.class, Tab3.class);
		Tab2 tab2 = new Tab2();
		Bundle bundle = new Bundle();
		bundle.putString("mode", "normal");
		tab2.setArguments(bundle);
		fragmentSwitcher.initFragment(new Tab1(),tab2, new Tab3());
		fragmentSwitcher.switchToNull();
//		fragmentSwitcher.switchPage(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open1:
			fragmentSwitcher.switchPage(0);
			break;
		case R.id.open2:
			fragmentSwitcher.switchPage(1);
			break;
		case R.id.open3:
			fragmentSwitcher.switchPage(2);
			break;
		case R.id.open4:
			fragmentSwitcher.switchToNull();
			break;

		default:
			break;
		}
	}
}
