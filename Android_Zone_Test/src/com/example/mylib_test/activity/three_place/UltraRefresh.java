package com.example.mylib_test.activity.three_place;
import android.os.Handler;
import android.widget.ImageView;
import com.example.mylib_test.R;
import com.zone.zrflist.UltraControl;
import com.zone.zrflist.headercustom.HeaderCustomDrawable;

import and.base.activity.BaseActvity;
import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/31.
 */
public class UltraRefresh extends BaseActvity {
    @Bind(R.id.iv_main)
    ImageView ivMain;
    @Bind(R.id.ptr_main)
    PtrFrameLayout ptrMain;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_ultrarefresh);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {
    }

    @Override
    public void initData() {
        ivMain.setImageDrawable(new HeaderCustomDrawable(UltraRefresh.this, ivMain));

        UltraControl.init(this, ptrMain, new UltraControl.OnRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
//                        ivMain.setImageDrawable(new DrawableCustom(UltraRefresh.this,ivMain));
                        ivMain.setImageBitmap(null);
                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivMain.setImageResource(R.drawable.aaaaaaaaaaaab);
                        ptrMain.refreshComplete();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void setListener() {

    }

}
