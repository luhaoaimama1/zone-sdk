package com.example.mylib_test;
import com.example.mylib_test.activity.db.entity.MenuEntity;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.adapter.callback.IAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ListView;

public class MainActivity2 extends Activity{
	private ListView listView1;
	private int positonId=-1;
	private AlertDialog alert;
	private IAdapter<MenuEntity> adapter2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_menu);
		
		createDialog();
		listView1=(ListView) findViewById(R.id.listView1);
		final int[] colorArry={Color.WHITE,Color.GREEN,Color.YELLOW,Color.CYAN};
		adapter2=new QuickAdapter<MenuEntity>(this,MainMenu.menu) {
			@Override
			public void convert(final Helper helper, final MenuEntity item, boolean itemChanged, int layoutId) {
				helper.setText(R.id.tv,item.info).setBackgroundColor(R.id.tv,colorArry[helper.getPosition()%colorArry.length])
						.setOnClickListener(R.id.tv,new OnClickListener() {
							@Override
							public void onClick(View v) {
//								ToastUtils.showLong(getApplication(), "真的好使吗");//真的好使
								startActivity(new Intent(MainActivity2.this,item.goClass));
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
		adapter2.relatedList(listView1);
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
	

}
