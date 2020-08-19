package com.example.mylib_test.activity.pop_dialog.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.zone.lib.base.BasePopWindow;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

public class PopFullScrenn extends BasePopWindow implements OnClickListener {
    public PopFullScrenn(Activity activity) {
        super(activity);
        setPopContentView(R.layout.popwindow_fill, -1);
    }

	@Override
	protected void setWH(int width, int height) {
		super.setWH(width, height);
		setClippingEnabled(false);
	}

	@Override
    protected void findView(View mMenuView) {
//		mMenuView.findViewById(R.id.view).setTranslationY(-200);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setLocation(View view) {
        this.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
