package com.example.mylib_test.activity.touch;

import java.util.Arrays;
import java.util.List;

import com.example.mylib_test.R;
import com.example.mylib_test.Images;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class Conflict1Activity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String type = getIntent().getStringExtra("type");
		if ("out".equals(type)) {
			setContentView(R.layout.a_touch_confict1);
		} else if ("frame".equals(type)) {
			setContentView(R.layout.a_touch_confict1_frame);
		}else if("innerOntouch".equals(type)){
			setContentView(R.layout.a_touch_confict1_ontouch);
		} else {
			setContentView(R.layout.a_touch_confict1_inner);
		}
		
		ListView lv=(ListView) findViewById(R.id.lv);
		List<String> temp = Arrays.asList(Images.imageThumbUrls);
		lv.setAdapter(new Adapter(this, temp));
	}
	public class Adapter extends QuickAdapter<String> {

		public Adapter(Context context, List<String> data) {
			super(context, data);
		}

		@Override
		public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
			helper.setText(R.id.tv,item);
		}

		@Override
		public int getItemLayoutId(String s, int position) {
			return R.layout.item_textview;
		}
	}
}
