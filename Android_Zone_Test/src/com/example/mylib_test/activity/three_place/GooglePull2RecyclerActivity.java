package com.example.mylib_test.activity.three_place;
import java.util.LinkedList;
import and.base.activity.BaseActivityZ;
import network.engine.utils.XutilsHttpUtils;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.mylib_test.R;
import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.adapter.callback.IAdapter;


public class GooglePull2RecyclerActivity extends BaseActivityZ implements OnRefreshListener{

	private SwipeRefreshLayout swipe_container;
	private RecyclerView rv;
	private IAdapter<String> adapter2;
	private static final int tag=1111;
	private  LinkedList<String> data=new LinkedList<String>();
	@Override
	public void setContentView() {
		setContentView(R.layout.a_pull2recycler);
		for (int i = 0; i < 15; i++) {
			data.add("一直很桑心");
		}
	}

	@Override
	public void findIDs() {
		swipe_container=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
//		swipe_container.setColorScheme(android.R.color.holo_blue_bright,  android.R.color.holo_green_light, 
//        android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipe_container.setColorScheme(android.R.color.holo_red_light);
		swipe_container.setOnRefreshListener(this);
		
		rv=(RecyclerView)findViewById(R.id.rv);
		rv.setLayoutManager(new LinearLayoutManager(this));
		adapter2=new QuickRcvAdapter<String>(this,data) {
			@Override
			public void fillData(Helper helper, String item, boolean itemChanged, int layoutId) {
				helper.setText(R.id.id_num,item);
			}

			@Override
			public int getItemLayoutId(String s, int position) {
				return  R.layout.item_recycler;
			}
		};
		adapter2.relatedList(rv);
	}

	@Override
	public void initData() {
	}

	@Override
	public void setListener() {
		
	}
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case tag:
		  	swipe_container.setRefreshing(false);
		  	adapter2.ani().add(1, "baidu返回结果");
			System.out.println("输出结果是："+msg.obj);
			break;

		default:
			break;
		}
		return super.handleMessage(msg);
	}

	@Override
	public void onRefresh() {
		adapter2.add(1,"baidu刷新");
		XutilsHttpUtils.getHandler_Json(this, "https://www.baidu.com/", handler, tag);
	}

}
