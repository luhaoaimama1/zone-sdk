package com.example.mylib_test.activity.touch;

import android.os.Bundle;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.touch.view.ViewDragFrame;

import com.zone.lib.base.activity.BaseActivity;
import com.zone.lib.base.activity.kinds.SwipeBackKind;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zone on 2016/1/29.
 */
public class ViewDragStudyActivity2 extends BaseActivity {
    @BindView(R.id.tv_mMenuView)
    TextView tvMMenuView;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_moveEnable)
    TextView tvMoveEnable;
    @BindView(R.id.viewDragStudyFrame)
    ViewDragFrame viewDragStudyFrame;
    @BindView(R.id.tv_leftOrRight)
    TextView tvLeftOrRight;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_viewdragstudy2);
    }

    @Override
    public void updateKinds() {
        super.updateKinds();
        mKindControl.remove(SwipeBackKind.class);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                viewDragStudyFrame.toggle();
                break;
            case R.id.tv_moveEnable:
                if (viewDragStudyFrame.isEnableMove()) {
                    viewDragStudyFrame.setEnableMove(false);
                    tvMoveEnable.setText("不!可以滑动");
                } else {
                    viewDragStudyFrame.setEnableMove(true);
                    tvMoveEnable.setText("可以滑动");
                }
                break;
            case R.id.tv_mMenuView:
                ToastUtils.showLong(this, "没有tryCaptureView 可以点击");
                break;
            case R.id.tv_leftOrRight:
                if (viewDragStudyFrame.getMoveType() == ViewDragHelper.EDGE_RIGHT){
                    viewDragStudyFrame.setMoveType(ViewDragHelper.EDGE_LEFT);
                    tvLeftOrRight.setText("左滑");
                }
                else{
                    viewDragStudyFrame.setMoveType(ViewDragHelper.EDGE_RIGHT);
                    tvLeftOrRight.setText("右滑");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
