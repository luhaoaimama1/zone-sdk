package com.zone.adapter.recycler.inter;

import android.view.View;

import com.zone.adapter.recycler.core.RecyclerHolder_Zone;


public interface OnItemClickListener {
	void onItemClick(View convertView, int position, RecyclerHolder_Zone holder);
}
