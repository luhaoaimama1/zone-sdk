package com.zone.adapter.adapter;

import android.content.Context;

import com.zone.adapter.adapter.core.BaseAdapter_Zone;

import java.util.List;

/**
 * @author Zone
 */
public abstract class Adapter_Zone<T> extends BaseAdapter_Zone<T> {
	/**
	 * @param context
	 * @param data
	 * @param layout_id
	 */
	public Adapter_Zone(Context context, List<T> data) {
		super(context,data);
	}
	@Override
	public int getLayoutID(int position) {
		return setLayoutID();
	}
	public abstract  int setLayoutID();
}
