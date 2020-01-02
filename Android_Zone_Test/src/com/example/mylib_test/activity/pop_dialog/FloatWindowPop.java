package com.example.mylib_test.activity.pop_dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.mylib_test.R;
import com.zone.lib.base.WindowPop;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

/**
 * [2017] by Zone
 */

public class FloatWindowPop extends WindowPop {
    Button floatId;
    /**
     * @param context 尽量用 Application activity和他的生命周期不同!
     */
    public FloatWindowPop(Context context) {
        super(context.getApplicationContext(), true);
        setPopContentView(R.layout.float_layout);
    }

    @Override
    protected void findView(View mMenuView) {
        floatId = mMenuView.findViewById(R.id.float_id);
        floatId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(context, "onClick");
            }
        });
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
}
