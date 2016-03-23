package com.example.mylib_test.activity.three_place;

import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.recycleradapter.RecyclerBaseAdapterTest;
import com.example.mylib_test.activity.three_place.recycleradapter.RecyclerBaseAdapterTest_Muli;
import com.example.mylib_test.activity.three_place.recycleradapter.RvAdapter_Pull;
import com.zone.adapter.recycler.core.RecyclerHolder_Zone;
import com.zone.adapter.recycler.inter.OnItemClickListener;
import com.zone.adapter.recycler.inter.OnItemLongClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//瀑布刘　动画添加问题　　还有　onclick　位置问题　
public class RecyclerActivity extends Activity {

	private RecyclerView rv;
	private List<String> mDatas=new ArrayList<String>();
	private RvAdapter_Pull pullAdapter;
	Type type=Type.Base;
	private DefaultItemAnimator pullAni;
	private RecyclerBaseAdapterTest baseRecycler;
	private RecyclerBaseAdapterTest_Muli muliAdapter;
	public enum Type{
		Base,Pull,Mulitple;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_recycler);
		rv=(RecyclerView)findViewById(R.id.rv);
		for (int i = 'A'; i <='z'; i++) {
			mDatas.add("" + (char) i);
		}
		rv.setLayoutManager(new LinearLayoutManager(this));
//		noPullAdapter=new RvAdapter(this, mDatas);
		pullAdapter=new RvAdapter_Pull(this, mDatas);
		muliAdapter=new RecyclerBaseAdapterTest_Muli(this, mDatas);
		//基础测试
		baseRecycler=new RecyclerBaseAdapterTest(this, mDatas);
		baseRecycler.setOnItemClickListener(new OnItemClickListener() {
			

			@Override
			public void onItemClick(View convertView, int position,
					RecyclerHolder_Zone holder) {
				// TODO Auto-generated method stub
				
			}
		});
		baseRecycler.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public void onItemLongClick(View convertView, int position,RecyclerHolder_Zone holder) {
				
			}
		});
		rv.setAdapter(baseRecycler);
		// 设置item动画　　　此动画不设置默认也有
		pullAni=new DefaultItemAnimator();
		rv.setItemAnimator(pullAni);
	
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
			switch (type) {
			case Base:
				baseRecycler.addAllData("insert All");
				break;
			case Mulitple:
				muliAdapter.addAllData("insert All");
				break;
			case Pull:
				pullAdapter.addAllData("insert All");
				break;

			default:
				break;
			}
			break;
		case  R.id.add:
			switch (type) {
			case Base:
				baseRecycler.addData("insert one");
				break;
			case Mulitple:
				muliAdapter.addData("insert one");
				break;
			case Pull:
				pullAdapter.addData("insert one");
				break;

			default:
				break;
			}
			break;
		case  R.id.delete:
			switch (type) {
			case Base:
				baseRecycler.deleteData();
				rv.scrollToPosition(0);
				break;
			case Mulitple:
				muliAdapter.deleteData();
				break;
			case Pull:
				pullAdapter.deleteData();
				break;

			default:
				break;
			}
			break;
		case  R.id.action_GirdView:
			rv.setLayoutManager(new GridLayoutManager(this,3));
			rv.setAdapter(baseRecycler);
			type=Type.Base;
			break;
		case  R.id.action_ListView:
			rv.setLayoutManager(new LinearLayoutManager(this));
			rv.setAdapter(baseRecycler);
			type=Type.Base;
			break;
		case  R.id.action_pull:
			rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
			rv.setAdapter(pullAdapter);
			type=Type.Pull;
			break;
		case  R.id.action_H_GirdView:
			rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
			rv.setAdapter(baseRecycler);
			type=Type.Base;
			break;
		case  R.id.baseAdapter:
			rv.setLayoutManager(new LinearLayoutManager(this));
			rv.setAdapter(baseRecycler);
			type=Type.Base;
			break;
		case  R.id.multiples:
			rv.setLayoutManager(new LinearLayoutManager(this));
			rv.setAdapter(muliAdapter);
			type=Type.Mulitple;
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}
