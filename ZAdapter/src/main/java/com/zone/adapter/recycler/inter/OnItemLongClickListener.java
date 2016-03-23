package com.zone.adapter.recycler.inter;

import android.view.View;

import com.zone.adapter.recycler.core.RecyclerHolder_Zone;


public interface OnItemLongClickListener {
	void onItemLongClick(View convertView, int position, RecyclerHolder_Zone holder);
}
