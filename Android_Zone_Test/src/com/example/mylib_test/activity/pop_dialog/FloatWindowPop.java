package com.example.mylib_test.activity.pop_dialog;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.example.mylib_test.R;
import and.base.WindowPop;
import and.utils.activity_fragment_ui.ToastUtils;
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
        super(context.getApplicationContext());
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
        floatId.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                int x = (int) event.getRawX() - mMenuView.getMeasuredWidth() / 2;
                //Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
                Log.i(TAG, "RawX" + event.getRawX());
                Log.i(TAG, "X" + event.getX());
                //25为状态栏的高度
                int y = (int) event.getRawY() - mMenuView.getMeasuredHeight() / 2 - 25;
                // Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredHeight()/2);
                Log.i(TAG, "RawY" + event.getRawY());
                Log.i(TAG, "Y" + event.getY());

                updateLocation(Gravity.LEFT | Gravity.TOP, x, y);
                //刷新
                return false;
            }
        });
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
