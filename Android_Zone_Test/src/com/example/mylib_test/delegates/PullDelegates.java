package com.example.mylib_test.delegates;


import com.example.mylib_test.R;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

public class PullDelegates extends ViewDelegates<String> {



	@Override
	public int getLayoutId() {
		return R.layout.item_threethird_pull;
	}

	@Override
	public void fillData(int i, String s, Holder holder) {
		holder.setText(R.id.tv,s);
	}

}
