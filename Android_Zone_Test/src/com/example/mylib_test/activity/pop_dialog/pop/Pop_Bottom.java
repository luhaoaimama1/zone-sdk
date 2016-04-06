package com.example.mylib_test.activity.pop_dialog.pop;
import com.example.mylib_test.R;

import and.base.Pop_Zone;
import and.utils.ToastUtils;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pop_Bottom extends Pop_Zone implements OnClickListener{
	private Button bt_how,bt_are,bt_you;
	private Activity context;

	public Pop_Bottom(Activity activity,int showAtLocationViewId) {
		super(activity,  showAtLocationViewId);
		this.context=activity;
		setPopContentView(R.layout.pop_bottom, Mode.Wrap, -1);
	}

	@Override
	protected void findView(View mMenuView) {
		bt_how=(Button) mMenuView.findViewById(R.id.bt_how);
		bt_are=(Button) mMenuView.findViewById(R.id.bt_are);
		bt_you=(Button) mMenuView.findViewById(R.id.bt_you);
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void setListener() {
		bt_how.setOnClickListener(this);
		bt_are.setOnClickListener(this);
		bt_you.setOnClickListener(this);
	}

	@Override
	protected void setLocation(View view) {
		this.showAsDropDown(view,0, 0);
	}

	@Override
	public void onClick(View v) {
		ToastUtils.showLong(context, "ºÙºÙ");
		dismiss();
	}

}
