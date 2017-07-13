package com.example.mylib_test.activity.animal;


import android.view.View;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.viewa.PathMeasureView;

import com.zone.lib.base.activity.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fuzhipeng on 2016/11/23.
 */

public class PathMeasureActivity extends BaseActivity {

    @Bind(R.id.pathMeasureView)
    PathMeasureView pathMeasureView;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_pathmeasure);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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


    @OnClick({R.id.bt_Constructor, R.id.bt_NextContour, R.id.bt_GetSegmentTrue, R.id.bt_GetSegmentFalse, R.id.bt_GetMatrix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_Constructor:
                pathMeasureView.setState(PathMeasureView.State.Constructor);
                break;
            case R.id.bt_NextContour:
                pathMeasureView.setState(PathMeasureView.State.NextContour);
                break;
            case R.id.bt_GetSegmentTrue:
                pathMeasureView.setState(PathMeasureView.State.GetSegmentTrue);
                break;
            case R.id.bt_GetSegmentFalse:
                pathMeasureView.setState(PathMeasureView.State.GetSegmentFalse);
                break;
            case R.id.bt_GetMatrix:
                pathMeasureView.setState(PathMeasureView.State.GetMatrix);
                break;
        }
    }
}
