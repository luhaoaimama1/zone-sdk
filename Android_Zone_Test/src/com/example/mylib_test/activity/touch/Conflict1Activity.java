package com.example.mylib_test.activity.touch;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.Images;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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
	public class Adapter extends Adapter_Zone<String> {

		public Adapter(Context context, List<String> data) {
			super(context, data);
		}
		@Override
		public int setLayoutID() {
			return  R.layout.item_textview;
		}

		@Override
		public void setData(ViewHolder_Zone holder, String data, int position) {
			TextView tv=(TextView) holder.findViewById(R.id.tv);
			tv.setText(data);
		}
		
	} 
}
