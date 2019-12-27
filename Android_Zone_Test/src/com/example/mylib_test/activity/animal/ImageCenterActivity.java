package com.example.mylib_test.activity.animal;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.zone.lib.base.activity.BaseActivity;
import com.zone.lib.utils.activity_fragment_ui.MeasureUtils;
import com.zone.lib.utils.image.compress2sample.SampleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.ImageCenter;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ImageCenterActivity extends BaseActivity {

    @Override
    public void setContentView() {
        ImageCenter view = new ImageCenter(this);
        setContentView(view);
        Bitmap bt = SampleUtils.load(this, R.drawable.aaaaaaaaaaaab)
                .bitmap();
        view.setBitmap(bt);
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
    }
}
