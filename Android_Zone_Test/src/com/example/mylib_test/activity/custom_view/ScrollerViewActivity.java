package com.example.mylib_test.activity.custom_view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.example.mylib_test.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * [2017] by Zone
 */

public class ScrollerViewActivity extends Activity {
    @Bind(R.id.sv)
    HorizontalScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_hero_scroller);
        ButterKnife.bind(this);
        sv.postDelayed(new Runnable() {
            @Override
            public void run() {
                sv.scrollTo(1000, 0);
                Log.e("ScrollerViewActivity",sv.getScrollX()+"");

            }
        },2000);

    }
}
