package view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public abstract class DialogCustemZone {
	private Context context;
	public boolean isSure = false;

	public DialogCustemZone(Context context) {
		this.context = context;
		this.show();
	}

	public void show() {
		AlertDialog.Builder db = new Builder(context);
		db.setTitle("你确定要进行此操作吗？");
		addSetProperty(db);
		db.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				isSure();
			}
		});
		db.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				notSure();
			}
		});
		db.create().show();
	}
	/**
	 * 需要额外的属性可以添加 set方法 
	 * @param db 
	 */
	public abstract void addSetProperty(Builder db);
	/**
	 * 确定的后走的方法
	 */
	public abstract void isSure();
	/**
	 * 取消后的后走的方法
	 */
	public abstract void notSure() ;
}
