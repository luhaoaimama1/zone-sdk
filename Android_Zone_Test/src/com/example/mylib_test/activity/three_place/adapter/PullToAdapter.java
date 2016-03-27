package com.example.mylib_test.activity.three_place.adapter;

import java.util.List;
import com.example.mylib_test.R;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import android.content.Context;
public class PullToAdapter extends QuickAdapter<String> {


	public PullToAdapter(Context context, List<String> data) {
		super(context, data);
	}

	@Override
	public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
		helper.setText(R.id.tv,item);
	}

	@Override
	public int getItemLayoutId(String s, int position) {
		return R.layout.item_threethird_pull;
	}
}
