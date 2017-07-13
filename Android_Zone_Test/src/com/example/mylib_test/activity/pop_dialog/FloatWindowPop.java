package com.example.mylib_test.activity.pop_dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import com.example.mylib_test.R;
import com.zone.lib.base.WindowPop;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * [2017] by Zone
 */

public class FloatWindowPop extends WindowPop {
    private static final String TAG = "FloatWindowPop";
    @Bind(R.id.float_id)
    Button floatId;
    private View mMenuView;

    /**
     * @param context 尽量用 Application activity和他的生命周期不同!
     */
    public FloatWindowPop(Context context) {
        super(context.getApplicationContext(),true);
        setPopContentView(R.layout.float_layout);
    }

    @Override
    protected void findView(View mMenuView) {
        this.mMenuView = mMenuView;
        ButterKnife.bind(this, mMenuView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setLocation() {
        showAtLocation(Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    @OnClick(R.id.float_id)
    public void onClick() {
        ToastUtils.showShort(context, "onClick");
    }
}
