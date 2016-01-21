package com.example.mylib_test.activity.three_place;

import java.util.LinkedList;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.adapter.PullToAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import and.image.lruutils.DiskLruUtils;
import and.log.ToastUtils;
import and.network.RefreshUtils;
import and.network.RefreshUtils.PullToRefreshListener;
import and.wifi.NetManager;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;

public class PullToRefreshTestActivity extends Activity implements Callback,PullToRefreshListener{
	protected static final int DownToRefresh = 0, Refresh = 1;
	private PullToRefreshListView list;
	private Handler handler=new Handler(this);
	private PullToAdapter adapter;
	private static LinkedList<String> data=new LinkedList<String>();
	static{
		for (int i = 0; i < 20; i++) {
			data.add("一直很桑心");
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_threethrid_pull);
		list=(PullToRefreshListView) findViewById(R.id.list);
		adapter=new PullToAdapter(this, data);
		RefreshUtils.initPullToRefreshListView(list, adapter, this);
	}
//	@Override
//	public boolean handleMessage(Message msg) {
//		switch (msg.what) {
//		case DownToRefresh:
//			data.add("下拉加载哈！");
//			break;
//		case Refresh:
//			data.add("上啦刷新呢！");
//			break;
//
//		default:
//			break;
//		}
//		adapter.notifyDataSetChanged();
//		list.onRefreshComplete();
//		return false;
//	}
	@Override
	public boolean handleMessage(Message msg) {
		list.onRefreshComplete();
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
			list.onRefreshComplete();
			super.onPostExecute(result);//这句是必有的，AsyncTask规定的格式
		}
	}
	@Override
	public void loadMore(View v) {
		//这里需要封装　　一个handler 带网络请求的handler
		ToastUtils.showLong(this, "loadMore");
		HttpUtils http = new HttpUtils();
		//设置超市时间
		http.configCurrentHttpCacheExpiry(1000 * 10);
		http.send(HttpRequest.HttpMethod.GET, "http://www.baidu.com",
		// params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// resultText.setText("response:" +
						// responseInfo.result);
						System.out.println("咋不上天呢？回来的数据："+responseInfo.result);
						data.add("onSuccess");
						adapter.notifyDataSetChanged();
						DiskLruUtils disk = DiskLruUtils.openLru(PullToRefreshTestActivity.this);
						disk.addUrl_String("http://www.baidu.com", responseInfo.result);
						
						System.out.println("咋不上天呢？Read:"+disk.getStringByUrl("http://www.baidu.com"));
						
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						data.add("onFailure");
						adapter.notifyDataSetChanged();
					}
				});
	}
	@Override
	public void onRefresh(View v) {
		if (!NetManager.haveNetWork(this)) {
			handler.sendEmptyMessage(1);
			return ;
		}
		String label = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		list.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		new GetDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

}
