package com.example.mylib_test.activity.custom_view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.mylib_test.R;

import and.abstractclass.adapter.Adapter_Zone;
import and.abstractclass.adapter.core.ViewHolder_Zone;
import and.listview.RefreshZone;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RefreshListViewActivity extends Activity{
	RefreshZone rf_list;
	List<String> data=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_customview_refreshlist);
		rf_list=(RefreshZone) findViewById(R.id.rf_list);
		addData();
		rf_list.setAdapter(new Adapter_Zone<String>(this, data) {


			@Override
			public int setLayoutID() {
				return R.layout.item_textview;
			}

			@Override
			public void setData(ViewHolder_Zone holder, String data,
					int position) {
				TextView tv=(TextView) holder.findViewById(R.id.tv);
				tv.setText(data);
			}

		});
	}
	private void addData() {
		for (int i = 0; i < 15; i++) {
			data.add("虚拟数据："+i);
		}
	}
}
