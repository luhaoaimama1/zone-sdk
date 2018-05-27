package com.example.mylib_test.delegates;

import com.example.mylib_test.R;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

/**
 * [2018] by Zone
 */

public class TextDelegates extends ViewDelegates<String> {

    @Override
    public void fillData(int i, String s, Holder holder) {
        holder.setText(R.id.id_num, s);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_recycler;
    }
}
