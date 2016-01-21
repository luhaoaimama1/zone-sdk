package com.example.mylib_test.activity.three_place;

import java.util.LinkedList;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.adapter.PullToAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class GooglePullActvity extends Activity implements OnRefreshListener{
	private ListView lv;
	private SwipeRefreshLayout swipe_container;
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
		lv.setAdapter(adapter);
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
		new Handler().postDelayed(new Runnable() {
	        public void run() {
	        	swipe_container.setRefreshing(false);
	        }
	    }, 3000);
	}
}
