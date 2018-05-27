package com.example.mylib_test.delegates;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.mylib_test.MainActivity2;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.db.entity.MenuEntity;
import com.example.mylib_test.activity.three_place.FrescoActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

import java.util.List;

/**
 * [2018] by Zone
 */

public class MenuEntityDeletates extends ViewDelegates<MenuEntity> {

    private final int[] colorArry;
    private final List<MenuEntity> menu;
    private final Activity activity;

    public MenuEntityDeletates(Activity activity,int[] colorArry, List<MenuEntity> menu) {
        this.menu=menu;
        this.activity=activity;
        this.colorArry=colorArry;
    }



    @Override
    public int getLayoutId() {
        return R.layout.item_menu;
    }

    @Override
    public void fillData(int posi, MenuEntity entity, Holder holder) {
        holder.setText(R.id.tv,entity.info).setBackgroundColor(R.id.tv
                ,colorArry[holder.getLayoutPosition()%colorArry.length])
                .setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=holder.getLayoutPosition()- MenuEntityDeletates.this.adapter.getHeaderViewsCount();
                        System.out.println("position:" +position);
                        Class<?> goClass  =menu.get(position).goClass;
                        if (goClass != null) {
                            activity.startActivity(new Intent(activity, goClass));
                        }
                    }
                },R.id.tv);
    }


}
