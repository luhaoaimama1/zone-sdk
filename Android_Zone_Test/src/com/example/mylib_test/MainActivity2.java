package com.example.mylib_test;
import com.example.mylib_test.activity.db.entity.MenuEntity;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.QuickManager;
import com.zone.adapter.callback.Helper;
import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity implements Handler.Callback{
	private ListView listView1;
	private int positonId=-1;
	private AlertDialog alert;
	private IAdapter<MenuEntity> adapter2;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		handler=new Handler(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_menu);
		
		createDialog();
		listView1=(ListView) findViewById(R.id.listView1);
		final int[] colorArry={Color.WHITE,Color.GREEN,Color.YELLOW,Color.CYAN};
		adapter2=new QuickAdapter<MenuEntity>(this,MainMenu.menu) {
			@Override
			public void fillData(final Helper<MenuEntity> helper, final MenuEntity item, boolean itemChanged, int layoutId) {
				helper.setText(R.id.tv,item.info).setBackgroundColor(R.id.tv,colorArry[helper.getPosition()%colorArry.length])
						.setOnClickListener(R.id.tv, new OnClickListener() {
							@Override
							public void onClick(View v) {
								System.out.println("position:" + helper.getPosition());
								if (helper.getData().goClass != null) {
									startActivity(new Intent(MainActivity2.this, helper.getData().goClass));
								}
							}
						})
						.setOnLongClickListener(R.id.tv, new OnLongClickListener() {
							@Override
							public boolean onLongClick(View v) {
								positonId = helper.getPosition();
								alert.show();
								return false;
							}
						});
			}

			@Override
			public int getItemLayoutId(MenuEntity menuEntity, int position) {
				return R.layout.item_menu;
			}

		};
		QuickManager.with(adapter2,listView1)
				.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_simple, null))
				.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_simple, null))
				.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_simple, null))
				.setOnLoadMoreListener(new OnLoadMoreListener() {
					boolean refesh=true;
					@Override
					public void onLoadMore() {
						final List<MenuEntity> mDatasa=new ArrayList<>();
						for (int i = 0; i <5 ; i++) {
							mDatasa.add(new MenuEntity("insert " + i,null));
						}
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								if (refesh) {
									adapter2.onLoadMoreComplete();
									adapter2.addAll(mDatasa);
								}else{
									adapter2.onLoadMoreFail();
								}
								refesh=!refesh;
							}
						},1000);
					}
				}).perform();
	}
	private void createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to remove?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   if(positonId!=-1){
		        		   MainMenu.menu.remove(positonId);
			        	   positonId=-1;
						   adapter2.notifyDataSetChanged();
		        	   }
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		 alert = builder.create();
	}


	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
}
