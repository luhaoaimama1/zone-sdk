package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
public class ArcMenuTestActivity extends Activity
{


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//四角 
//		setContentView(R.layout.a_arc_menu_test_main);
		//中心 角度可扩张
		setContentView(R.layout.a_arc_menu_test_r);
//		mArcMenuLeftTop = (ArcMenu) findViewById(R.id.id_arcmenu1);
//		//动态添加一个MenuItem
//		ImageView people = new ImageView(this);
//		people.setImageResource(R.drawable.composer_with);
//		people.setTag("People");
//		mArcMenuLeftTop.addView(people);
//
//	
//		mArcMenuLeftTop
//				.setOnMenuItemClickListener(new OnMenuItemClickListener()
//				{
//					@Override
//					public void onClick(View view, int pos)
//					{
//						Toast.makeText(MainActivity.this,
//								pos + ":" + view.getTag(), Toast.LENGTH_SHORT)
//								.show();
//					}
//				});
	}

}
