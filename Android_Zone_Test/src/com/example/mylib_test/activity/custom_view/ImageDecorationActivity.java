package com.example.mylib_test.activity.custom_view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.Images;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zone.adapter.recycler.AdapterRecycler_Zone;
import com.zone.adapter.recycler.core.RecyclerHolder_Zone;

import java.util.ArrayList;
import java.util.List;

import and.abstractclass.BaseActvity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zone on 2016/2/22.
 */
public class ImageDecorationActivity extends BaseActvity {
    @Bind(R.id.view)
    RecyclerView view;
    private List<String> data=new ArrayList<String>();

    @Override
    public void setContentView() {
        setContentView(R.layout.a_imagedecration);
        ButterKnife.bind(this);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            data.add(Images.imageThumbUrls[i]);
        }
        view.setLayoutManager(new GridLayoutManager(this,3));
        view.setAdapter(new AdapterRecycler_Zone<String>(this,data) {
            @Override
            public void setData(RecyclerHolder_Zone holder, String data, int position) {
                ImageView image =(ImageView) holder.findViewById(R.id.tv);
                ImageLoader.getInstance().displayImage(data, image);
            }

            @Override
            public int setLayoutID() {
                return R.layout.item_imageview;
            }
        });
    }

    @Override
    public void setListener() {

    }

}
