package com.example.mylib_test;

import java.util.Map;

import com.example.mylib_test.activity.db.entity.MenuEntity;

import and.abstractclass.adapter.Adapter_Zone;
import and.abstractclass.adapter.core.ViewHolder_Zone;
import and.log.ToastUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity2 extends Activity{
	private ListView listView1;
	private int positonId=-1;
	private AlertDialog alert;
	private Adapter_Zone<MenuEntity> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_menu);
		
		createDialog();
		listView1=(ListView) findViewById(R.id.listView1);
		final int[] colorArry={Color.WHITE,Color.GREEN,Color.YELLOW,Color.CYAN};
		adapter=new Adapter_Zone<MenuEntity>(this,MainMenu.menu) {


			@Override
			public int setLayoutID() {
				return R.layout.item_menu;
			}

			@Override
			public void setData(ViewHolder_Zone holder, MenuEntity data,
					final int position) {
				TextView tv=(TextView) holder.findViewById(R.id.tv);
				tv.setText(MainMenu.menu.get(position).info);
				tv.setBackgroundColor(colorArry[position%colorArry.length]);
				tv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						ToastUtils.showLong(getApplication(), "真的好使吗");//真的好使
						startActivity(new Intent(MainActivity2.this, MainMenu.menu.get(position).goClass));
					}
				});
				tv.setLongClickable(true);
				tv.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						positonId=position;
						alert.show();
						return false;
					}
				});
				
			}
		};
		listView1.setAdapter(adapter);
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
			        	   adapter.notifyDataSetChanged();
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
	

}
