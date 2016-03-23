package com.example.mylib_test.activity.three_place.adapter;

import java.util.List;
import com.example.mylib_test.R;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;

import android.content.Context;
import android.widget.TextView;
public class PullToAdapter extends Adapter_Zone<String> {

	private TextView tv;

	public PullToAdapter(Context context, List<String> data) {
		super(context, data);
	}
	@Override
	public int setLayoutID() {
		return  R.layout.item_threethird_pull;
	}

	@Override
	public void setData(ViewHolder_Zone holder, String data, int position) {
		tv=(TextView)holder.findViewById(R.id.tv);
		tv.setText(data);
	}

}
