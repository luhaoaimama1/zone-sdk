package com.example.mylib_test.activity.three_place.recycleradapter;

import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RvAdapter_Pull extends Adapter<MyViewHolder> {

	private List<Integer> datasHeight;
	private List<String> datas;
	private Context context;

	public RvAdapter_Pull(Context context, List<String> datas) {
		this.context = context;
		this.datas = datas;
		datasHeight=new ArrayList<Integer>();
		for (String item : datas) {
			datasHeight.add((int) (100+Math.random()*300));
		}
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder arg0, int arg1) {
		LayoutParams lp = arg0.tv.getLayoutParams();
		lp.height=datasHeight.get(arg1);
		arg0.tv.setLayoutParams(lp);
		arg0.tv.setText(datas.get(arg1));
		
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
				context).inflate(R.layout.item_recycler, arg0, false));
		return holder;
	}
	public void addAllData(String str){
		datas.add(1, str);
		notifyDataSetChanged();
	}
	public void addData(String str){
		datas.add(1, str);
		notifyItemInserted(1);
	}
	public void deleteData(){
		datas.remove(1);
		notifyItemRemoved(1);
	}

}
class MyViewHolder extends ViewHolder {
	TextView tv;
	LinearLayout ll_item;
	public MyViewHolder(View itemView) {
		super(itemView);
		tv = (TextView) itemView.findViewById(R.id.id_num);
		ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
	}
}

