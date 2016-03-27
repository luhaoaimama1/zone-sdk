package com.example.mylib_test.activity.custom_view;

import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.zrflist.RefreshZone;

import android.app.Activity;
import android.os.Bundle;

public class RefreshListViewActivity extends Activity{
	RefreshZone rf_list;
	List<String> data=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_customview_refreshlist);
		rf_list=(RefreshZone) findViewById(R.id.rf_list);
		addData();
		rf_list.setAdapter(new QuickAdapter<String>(this, data) {


			@Override
			public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
				helper.setText(R.id.tv,item);
			}

			@Override
			public int getItemLayoutId(String o, int position) {
				return R.layout.item_textview;
			}

		});
	}
	private void addData() {
		for (int i = 0; i < 15; i++) {
			data.add("虚拟数据："+i);
		}
	}
}
