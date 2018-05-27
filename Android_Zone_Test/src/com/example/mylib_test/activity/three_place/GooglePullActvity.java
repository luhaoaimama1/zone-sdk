package com.example.mylib_test.activity.three_place;

import java.util.LinkedList;

import com.example.mylib_test.R;
import com.example.mylib_test.delegates.PullDelegates;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mylib_test.delegates.TextDelegates;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.adapter3.loadmore.OnScrollRcvListener;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetManager;

public class GooglePullActvity extends Activity implements OnRefreshListener,Handler.Callback {
	private RecyclerView lv;
	private SwipeRefreshLayout swipe_container;
	private Handler handler=new Handler(this);
	private IAdapter adapter;
	private static LinkedList<String> data=new LinkedList<String>();
	static{
		for (int i = 0; i < 30; i++) {
			data.add("一直很桑心");
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_threeplace_google);
		
		swipe_container=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
		swipe_container.setOnRefreshListener(this);
//		swipe_container.setColorScheme(android.R.color.holo_blue_bright,  android.R.color.holo_green_light, 
//	            android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipe_container.setColorScheme(android.R.color.holo_red_light);
		lv=(RecyclerView) findViewById(R.id.lv);
		lv.setLayoutManager(new LinearLayoutManager(this));
		adapter=new QuickRcvAdapter(this, data)
				.addViewHolder(new TextDelegates())
				.relatedList(lv)
				.addOnScrollListener(new OnScrollRcvListener(){
					@Override
					protected void loadMore(RecyclerView recyclerView) {
						super.loadMore(recyclerView);
						//相当于告诉他加载完成了
						new Handler().postDelayed(new Runnable() {
							public void run() {
								data.addLast("上啦加载的数据~");
								adapter.notifyDataSetChanged();
								adapter.loadMoreComplete();
							}
						}, 3000);
					}
				});
	}
	@Override
	public void onRefresh() {
		if (!NetManager.haveNetWork(this)) {
			handler.sendEmptyMessage(1);
			return ;
		}
		new Handler().postDelayed(new Runnable() {
	        public void run() {
				// Do work to refresh the list here.
				new GetDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        }
	    }, 3000);
	}

	@Override
	public boolean handleMessage(Message msg) {
		data.addFirst("当前没有网络呢");
		adapter.notifyDataSetChanged();
		swipe_container.setRefreshing(false);
		return false;
	}


	private class GetDataTask extends AsyncTask<Void, Void, String> {
		// 后台处理部分
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String str="Added after refresh...I add";
			return str;
		}

		//这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		//根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(String result) {
			//在头部增加新添内容
			data.addFirst(result);
			//通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			adapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			swipe_container.setRefreshing(false);
			super.onPostExecute(result);//这句是必有的，AsyncTask规定的格式
		}
	}
}
