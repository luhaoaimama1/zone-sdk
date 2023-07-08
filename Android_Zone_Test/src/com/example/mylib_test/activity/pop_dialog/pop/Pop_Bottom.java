package com.example.mylib_test.activity.pop_dialog.pop;
import com.example.mylib_test.R;

import com.zone.lib.base.BasePopWindow;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class Pop_Bottom extends BasePopWindow implements OnClickListener{
	private Button bt_how,bt_are,bt_you;
	private Activity context;

	public Pop_Bottom(Activity activity,int showAtLocationViewId) {
		super(activity,  showAtLocationViewId);
		this.context=activity;
		setPopContentView(R.layout.pop_bottom, -1);
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void findView(View mMenuView) {
		bt_how=(Button) mMenuView.findViewById(R.id.bt_how);
		bt_are=(Button) mMenuView.findViewById(R.id.bt_are);
		bt_you=(Button) mMenuView.findViewById(R.id.bt_you);

		setFocusable(false);
		setOutsideTouchable(false);
		setWindowLayoutType(WindowManager.LayoutParams.LAST_SUB_WINDOW);
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
		ToastUtils.INSTANCE.showLong(context, "嘿嘿");
		dismiss();
	}

}
