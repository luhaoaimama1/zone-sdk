package com.example.mylib_test.activity.touch;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mylib_test.R;
import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;
import java.util.ArrayList;
import java.util.List;

import and.base.activity.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;


public class NestedScrollingActivity_hongParent extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_nested_parent_hong);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < 50; i++) {
            mDatas.add("Parent Demo -> " + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new QuickRcvAdapter<String>(this, mDatas) {
            @Override
            public void fillData(Helper<String> helper, String item, boolean itemChanged, int layoutId) {
                helper.setText(R.id.tv, item);
            }

            @Override
            public int getItemLayoutId(String s, int position) {
                return R.layout.item_rc_textview;
            }
        });
    }

    @Override
    public void setListener() {

    }

}
