package com.example.mylib_test.activity.touch;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mylib_test.R;

import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.delegates.TextDelegates;
import com.example.mylib_test.delegates.TextType2Delegates;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.lib.base.activity.BaseActivity;

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

        new QuickRcvAdapter<String>(this, mDatas)
                .addViewHolder(new TextType2Delegates())
                .relatedList(recyclerView);
    }

    @Override
    public void setListener() {

    }

}
