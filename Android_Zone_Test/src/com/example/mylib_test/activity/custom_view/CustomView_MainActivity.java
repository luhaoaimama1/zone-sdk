package com.example.mylib_test.activity.custom_view;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.frag_viewpager_expand.SampleTabsDefault;
import com.example.mylib_test.activity.frag_viewpager_expand.ViewPagerDisableScrollActivity;
import com.example.mylib_test.activity.frag_viewpager_expand.ViewpagerIndicatorActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomView_MainActivity  extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_custom_view);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frg_adapter_circle:
			startActivity(new Intent(this,ViewPagerActivity.class));
			break;
		case R.id.frg_adapter_circle233:
			startActivity(new Intent(this,ViewPagerRepeatWriteActivity.class));
			break;
		case R.id.frg_disable_scroll:
			startActivity(new Intent(this,ViewPagerDisableScrollActivity.class));
			break;
		case R.id.bt_viewPagerIndicator:
			startActivity(new Intent(this,ViewpagerIndicatorActivity.class));
			break;
		case R.id.bt_SampleTabsDefault:
			startActivity(new Intent(this,SampleTabsDefault.class));
			break;
		case R.id.label_test:
			startActivity(new Intent(this,LabelTestActivity.class));
			break;
		case R.id.arcMenu:
			startActivity(new Intent(this,ArcMenuTestActivity.class));
			break;
		case R.id.imageGroup_test:
			startActivity(new Intent(this,ImageGroupTestActivity.class));
			break;
		case R.id.square_test:
			startActivity(new Intent(this,SquareTestActivity.class));
			break;
		case R.id.bt_hero1:
			startActivity(new Intent(this,AndroidHeroActivity.class).putExtra("type", "circle"));
			break;
		case R.id.bt_hero2:
			startActivity(new Intent(this,AndroidHeroActivity.class).putExtra("type", "circle2"));
			break;
		case R.id.bt_scroll:
			startActivity(new Intent(this,AndroidHeroActivity.class).putExtra("type", "scroll"));
			break;
		case R.id.refreshListView:
			startActivity(new Intent(this,RefreshListViewActivity.class));
			break;
		case R.id.imageGroupRecyclerTest:
			startActivity(new Intent(this,ImageGroupRecyclerTestActvitity.class));
			break;

		default:
			break;
		}
	}

}
