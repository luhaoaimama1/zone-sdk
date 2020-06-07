package com.example.mylib_test.activity.pop_dialog.pop;

import com.example.mylib_test.R;

import com.zone.lib.base.BasePopWindow;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Pop_Photo extends BasePopWindow implements OnClickListener{
	private TextView tv_goodsNumber;
	private TextView tv_call;
	private TextView tv_cancel;
	public Pop_Photo(Activity activity,int showAtLocationViewId,int buttonID) {
		super(activity, showAtLocationViewId);
		setPopContentView( R.layout.popwindow_phone, R.id.ll_cancelId,buttonID);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_call:
			ToastUtils.INSTANCE.showLong(activity, "哈哈");
			break;
		case R.id.tv_cancel:
			dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	protected void findView(View mMenuView) {
		 tv_goodsNumber=(TextView) mMenuView.findViewById(R.id.tv_goodsNumber);
		 tv_call=(TextView) mMenuView.findViewById(R.id.tv_call);
		 tv_cancel=(TextView) mMenuView.findViewById(R.id.tv_cancel);
	}

	@Override
	protected void initData() {
		tv_goodsNumber.setText("gaga");
	}

	@Override
	protected void setListener() {
		tv_call.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
	}

	@Override
	protected void setLocation(View view) {
//		this.setAnimationStyle(R.style.PopSelectPicAnimation);
		this.showAtLocation(view,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);	
	}


}
