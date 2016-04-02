package com.example.mylib_test.activity.three_place.recycleradapter;
import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
public class RecyclerBaseAdapterTest_Muli extends QuickRcvAdapter<String> {
	private List<Integer> datasHeight;
	public RecyclerBaseAdapterTest_Muli(Context context, List<String> data) {
		super(context, data);
	}
	boolean isRandom=false;
	public void openHeightRanmdom(boolean isRandom){
		if (isRandom) {
			datasHeight=new ArrayList<>();
			for (int i = 0; i <10000 ; i++) {
				datasHeight.add((int) (100+Math.random()*300));
			}
			notifyDataSetChanged();
		}
		this.isRandom=isRandom;
	}
	@Override
	public void fillData(final Helper helper, String item, boolean itemChanged, int layoutId) {
		helper.setText(R.id.id_num,item).setOnClickListener(R.id.ll_item,new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("haha position:" + helper.getPosition());
			}
		});
		if (isRandom) {
			TextView tv_id_num=(TextView)helper.getView(R.id.id_num);
			LayoutParams lp = tv_id_num.getLayoutParams();
			lp.height=datasHeight.get(helper.getPosition());
			tv_id_num.setLayoutParams(lp);
			tv_id_num.setText(item);
		}
	}

	@Override
	public int getItemLayoutId(String s, int position) {
//		if(position%2==0)
			return  R.layout.item_recycler;
//		else
//			return  R.layout.item_recycler_muptli;
	}

	
}
