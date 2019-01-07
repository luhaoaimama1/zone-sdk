package com.example.mylib_test.activity.frag_viewpager_expand;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.zone.zbanner.ViewPaperDisableScroll;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDisableScrollActivity2 extends FragmentActivity {
	private ViewPaperDisableScroll vPager = null;
	private static final int TAB_COUNT = 4;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private ViewPagerIndicator image1;
	List<TextView> tvlist = new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_frag_scoll2);
		Ui_init();
		vPager.setDisableScroll(false);
		vPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			//这个监听是  那个包固有的监听android.support.v4.view
			@Override
			public void onPageSelected(int arg0) {
				//划到哪页   那个文本高亮
				// TODO Auto-generated method stub
				for (int i = 0; i < tvlist.size(); i++) {
					tvlist.get(i).setTextColor(Color.BLACK);
				}
				tvlist.get(arg0).setTextColor(Color.RED);

			}

			@SuppressLint("NewApi")
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		image1.setViewPagerWithItemView(vPager,tv1,tv2,tv3,tv4);
		image1.setDrawRes(R.drawable.search_line_back);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new Tab1();
			case 1:
				return new Tab2();
			case 2:
				return new Tab3();
			case 3:
				return new Tab1();
			}
			return null;
		}

		@Override
		public int getCount() {
			return TAB_COUNT;
		}
	}

	// 初始化
	private void Ui_init() {
		vPager = (ViewPaperDisableScroll) findViewById(R.id.viewpager);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tvlist.add(tv1);
		tvlist.add(tv2);
		tvlist.add(tv3);
		tvlist.add(tv4);
		tv1.setTextColor(Color.RED);
		tv1.setOnClickListener(jt);
		tv2.setOnClickListener(jt);
		tv3.setOnClickListener(jt);
		tv4.setOnClickListener(jt);
		image1 = (ViewPagerIndicator) findViewById(R.id.image_1);
	}

	// 点击 文本跳到哪页
	public OnClickListener jt = new OnClickListener() {

		// tvlist.setTextColor(Color.RED);
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for (int i = 0; i < tvlist.size(); i++) {
				tvlist.get(i).setTextColor(Color.BLACK);
			}
			((TextView) v).setTextColor(Color.RED);

			switch (v.getId()) {
			case R.id.tv1:
				vPager.setCurrentItem(0);
				break;
			case R.id.tv2:
				vPager.setCurrentItem(1);
				break;
			case R.id.tv3:
				vPager.setCurrentItem(2);
				break;
			case R.id.tv4:
				vPager.setCurrentItem(3);
				break;
			default:
				break;
			}

		}
	};

}
