package com.example.mylib_test.activity.three_place;

import java.util.LinkedList;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.adapter.PullToAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import and.wifi.NetManager;

public class GooglePullActvity extends Activity implements OnRefreshListener,Handler.Callback {
	private ListView lv;
	private SwipeRefreshLayout swipe_container;
	private Handler handler=new Handler(this);
	private PullToAdapter adapter;
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
		lv=(ListView) findViewById(R.id.lv);
		adapter=new PullToAdapter(this, data);
		adapter.relatedList(lv);
		lv.setOnScrollListener(new OnScrollListener() {
			int scrollState;
			boolean loadMoreOk=true;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.scrollState=scrollState;
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
					if((firstVisibleItem+visibleItemCount)==totalItemCount){
						if (loadMoreOk) {
							loadMore(firstVisibleItem, visibleItemCount,totalItemCount);
							loadMoreOk=false;
						}
					}
				}
			}

			private void loadMore(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				System.out.println("loadMore_______:firstVisibleItem"+firstVisibleItem+"\t visibleItemCount"+visibleItemCount
						+"\t totalItemCount"+totalItemCount);
				data.addLast("上拉加载了~");
				adapter.notifyDataSetChanged();
				//相当于告诉他加载完成了
				new Handler().postDelayed(new Runnable() {
			        public void run() {
			        	loadMoreOk=true;
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
