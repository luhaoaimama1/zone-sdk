package com.example.mylib_test.activity.three_place.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.adapter.entity.SortModel;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;

import java.util.List;

public class SortAdapter2 extends QuickAdapter<SortModel> {

	public SortAdapter2(Context context, List<SortModel> data) {
		super(context, data);
	}

	public int getSelectionByPosition(int position){
		return data.get(position).getSortLetter().charAt(0);
	}
	
	/**
	 * 通过首字母获取显示该首字母的姓名的人，如：C,成龙
	 * @author Xubin
	 *
	 */
	public int getPositionBySelection(int selection){
		for (int i = 0; i < getCount(); i++) {
			String sortStr=data.get(i).getSortLetter();
			char firstChar=sortStr.toUpperCase().charAt(0);
			if (firstChar == selection) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void fillData(Helper<SortModel> helper, SortModel item, boolean itemChanged, int layoutId) {

		int selection=getSelectionByPosition(helper.getPosition());	// 首字母字符
		int index=getPositionBySelection(selection);
		if (helper.getPosition() == index) {
			// 说明这个条目是第一个，需要显示字母
			helper.getView(R.id.word).setVisibility(View.VISIBLE);
			helper.setText(R.id.word,item.getSortLetter());
		}else
			helper.getView(R.id.word).setVisibility(View.GONE);
		helper.setText(R.id.title,item.getName());
	}

	@Override
	public int getItemLayoutId(SortModel sortModel, int position) {
		return R.layout.layout_lv_item;
	}
}
