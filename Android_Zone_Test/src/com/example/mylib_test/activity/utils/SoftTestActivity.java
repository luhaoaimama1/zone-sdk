package com.example.mylib_test.activity.utils;

import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.example.mylib_test.delegates.MechaImgDelegates;
import com.zone.adapter3.QuickRcvAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class SoftTestActivity extends Activity{
	private List<Integer> list=new ArrayList<Integer>();
	Integer[] a=new Integer[]{ R.drawable.t0, R.drawable.t0,R.drawable.t0,R.drawable.t0, R.drawable.t0};
//	,R.drawable.t5, R.drawable.t6,R.drawable.t7, R.drawable.t8};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RecyclerView rv = new RecyclerView(this);
		setContentView(rv);
		for (int i = 0; i < 15; i++) {
			list.add( R.drawable.t0);
		}

		new QuickRcvAdapter<Integer>(this,list)
				.addViewHolder(new MechaImgDelegates())
				.relatedList(rv);
	}
}
