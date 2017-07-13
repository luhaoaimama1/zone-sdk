package com.example.mylib_test.activity.touch;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.touch.NestedView.NestedScrollingChildView;

import com.zone.lib.base.activity.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;


public class NestedScrollingActivity_Child extends BaseActivity {

    @Bind(R.id.recyclerView)
    NestedScrollingChildView nestedScrollingChildView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_nested_child);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_rc_textview, null);
//            view.setLayoutParams(lp);
            ( (TextView)view.findViewById(R.id.tv)).setText("Child Demo -> " + i);
//            nestedScrollingChildView.addView(view,i,new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,200
//            ));
            nestedScrollingChildView.addView(view,i);
        }
    }
    @Override
    public void setListener() {

    }


}
