package com.example.mylib_test.delegates;

import android.net.Uri;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.FrescoActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

/**
 * [2018] by Zone
 */

public class FrescoDeletates extends ViewDelegates<FrescoActivity.Entity> {
    @Override
    public int getLayoutId() {
        return R.layout.a_fresco_item;
    }

    @Override
    public void fillData(int posi, FrescoActivity.Entity entity, Holder holder) {
        holder.setText(R.id.tv,entity.introduce);

        SimpleDraweeView sdv= (SimpleDraweeView) holder.getView(R.id.sdv);
        Uri uri = Uri.parse(entity.uri);
        sdv.setImageURI(uri);
    }
}
