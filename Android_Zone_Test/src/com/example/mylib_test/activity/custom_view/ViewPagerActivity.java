package com.example.mylib_test.activity.custom_view;

import java.util.ArrayList;

import com.example.mylib_test.R;

import and.viewpage.ViewPagerCircle;
import and.viewpage.ViewPager_CycleAdapter_Zone;
import and.viewpage.ViewPager_CycleAdapter_Zone.OnPageChangeListener_Zone;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ViewPagerActivity extends Activity implements OnPageChangeListener_Zone {
	private int[] imageResIDs;
	private String[] imageTextArray;
	private ArrayList<ImageView> imageViewList;
	private ViewGroup llPointGroup;
	private ViewPagerCircle mViewPager;
	private TextView tvImageDescription;
	private int previousPointPosition = 0; // 前一个被选中的点的索引

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_viewpager);
		mViewPager = (ViewPagerCircle) findViewById(R.id.viewpager);
		tvImageDescription = (TextView) findViewById(R.id.tv_image_description);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
		initData();
		mViewPager.setAdapter(new ViewPager_CycleAdapter_Zone<ImageView>(imageViewList,mViewPager,this) {

			@Override
			public View getViewByList(int position) {
				return imageViewList.get(position);
			}

		},1000);
		// 初始化一下, 图片的描述, 和点的选中状态
		tvImageDescription.setText(imageTextArray[0]);

	}

	/**
	 * 准备数据
	 */
	private void initData() {
		imageResIDs = new int[] { R.drawable.akb, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };

		imageTextArray = new String[] { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱",
				"揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀" };

		imageViewList = new ArrayList<ImageView>();

		ImageView iv;
		View view;
		LayoutParams params;
		for (int i = 0; i < imageResIDs.length; i++) {
			iv = new ImageView(this);
			iv.setBackgroundResource(imageResIDs[i]); // 把图片设置给imageview控件
			imageViewList.add(iv);

			// 向LinearLayout中添加一个view对象, 设置背景为点的背景
			view = new View(this);
			params = new LayoutParams(5, 5);
			params.leftMargin = 10;
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.point_bg);
			view.setEnabled(false); // 把点置为没选中的状态
			llPointGroup.addView(view);
		}
	}


	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		System.out.println("当前选中的是: " + position);
		// 把对应position位置的点选中, 图片的描述要切换到position位置上
		tvImageDescription.setText(imageTextArray[position]);

		llPointGroup.getChildAt(previousPointPosition).setEnabled(false); // 把前一个点置为未选中
		llPointGroup.getChildAt(position).setEnabled(true);// 把当前的点置为选中

		previousPointPosition = position; // 把当前的点用前一个点的变量记录下来

	}
}
