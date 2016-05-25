package com.example.mylib_test.activity.three_place;
import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.recycleradapter.RecyclerBaseAdapterTest_Muli;
import com.zone.adapter.QuickManager;
import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

//瀑布刘　动画添加问题　　还有　onclick　位置问题　
public class RecyclerActivity extends Activity implements Handler.Callback{

	private RecyclerView rv;
	private List<String> mDatas=new ArrayList<String>();
	private RecyclerBaseAdapterTest_Muli muliAdapter;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		handler=new Handler(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_recycler);
		rv=(RecyclerView)findViewById(R.id.rv);
		for (int i = 'A'; i <='z'; i++) {
			mDatas.add("" + (char) i);
		}
		muliAdapter=new RecyclerBaseAdapterTest_Muli(this, mDatas);
//		//基础测试
		String type=getIntent().getStringExtra("type");
		if("Linear".equals(type)){
			rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
		}
		if("Grid".equals(type)){
			rv.setLayoutManager(new GridLayoutManager(this,3));
		}
		if("StaggeredGrid".equals(type)){
//			rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
			rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
			muliAdapter.openHeightRanmdom(true);
		}
		QuickManager.with(muliAdapter, rv)
		.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_simple, null))
		.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_simple, null))
		.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_simple, null))
		.setOnLoadMoreListener(new OnLoadMoreListener() {
			boolean refesh = true;
			@Override
			public void onLoadMore() {
				final List<String> mDatasa = new ArrayList<String>();
				for (int i = 0; i < 5; i++) {
					mDatasa.add("insert " + i);
				}
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (refesh) {
							muliAdapter.onLoadMoreComplete();
							muliAdapter.addAll(mDatasa);
						} else {
							muliAdapter.onLoadMoreFail();
						}
						refesh = !refesh;
					}
				}, 1000);
			}
		})
		.setOnItemClickListener(new IAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(ViewGroup parent, View view, int position, long id) {
				System.out.println("onItemClick position:" + position);
			}
		}).setOnItemLongClickListener(new IAdapter.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(ViewGroup parent, View view, int position, long id) {
				System.out.println("onItemLongClick position:" + position);
				return true;
			}
		}).perform();

		View tv_change = findViewById(R.id.tv_change);
		tv_change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				muliAdapter.ani().add(1, "insert one load ani");

			}
		});


	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.recycler_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case  R.id.all:
			muliAdapter.ani().add(1, "insert one load ani");
			break;
		case  R.id.add:
			muliAdapter.add(1, "insert one no ani");
			break;
		case  R.id.delete:
			muliAdapter.ani().remove(1);
			break;
		case  R.id.removeAll:
			muliAdapter.clear();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
}
