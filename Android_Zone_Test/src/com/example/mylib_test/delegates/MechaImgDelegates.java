package com.example.mylib_test.delegates;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

/**
 * [2018] by Zone
 */

public class MechaImgDelegates extends ViewDelegates<Integer> {

    @Override
    public void fillData(int i, Integer s, Holder holder) {
        ImageView ivD=(ImageView) holder.getView(R.id.iv);
        ivD.setImageBitmap(BitmapFactory.decodeResource(holder.getView().getResources(),s));
    }

    @Override
    public int getLayoutId() {
        return R.layout.imageitem;
    }
}
