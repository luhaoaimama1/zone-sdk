package com.example.mylib_test.delegates

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates

/**
 *[2018/7/10] by Zone
 */

class MenuEntityDeletates(val activity: Activity, val colorArry: IntArray,val menu: List<MenuEntity>) : ViewDelegates<MenuEntity>() {
    override fun getLayoutId(): Int = R.layout.item_menu

    override fun fillData(posi: Int, entity: MenuEntity?, holder: Holder<out Holder<*>>?) {
        holder!!.setText(R.id.tv,entity?.info)
                .setBackgroundColor(R.id.tv,colorArry[holder.getLayoutPosition()%colorArry.size])
                .setOnClickListener( View.OnClickListener() {
                        val position=holder.getLayoutPosition() - this@MenuEntityDeletates.adapter.getHeaderViewsCount();
                        System.out.println("position:" +position);
                        val goClass  =menu[position].goClass;
                        if (goClass != null) {
                            activity.startActivity( Intent(activity, goClass));
                    }
                },R.id.tv);
    }

}