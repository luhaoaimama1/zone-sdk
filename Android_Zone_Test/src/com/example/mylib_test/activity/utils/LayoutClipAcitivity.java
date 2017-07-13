package com.example.mylib_test.activity.utils;

import android.widget.ImageView;

import com.example.mylib_test.R;
import com.zone.view.FlowLayout;

import com.zone.lib.base.activity.BaseActivity;
import com.zone.lib.utils.view.AnimationUtils;
import com.zone.lib.utils.view.ViewUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class LayoutClipAcitivity extends BaseActivity {
    @Bind(R.id.iv_clip)
    ImageView iv_clip;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_layout_clip);
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

    @OnClick(R.id.ll_main)
    public void onClick() {
        FlowLayout parent;
        ImageView[] ivs = ViewUtils.clipView(iv_clip, parent=new FlowLayout(this), 3, 3);
        if(ivs==null)
            return ;
        parent.post(new Runnable() {
            @Override
            public void run() {
                AnimationUtils.explode(ivs, 3);//不加这个侧脸不出view 的宽高 就没办法计算了 其实能但是这个省事;
            }
        });
    }



}
