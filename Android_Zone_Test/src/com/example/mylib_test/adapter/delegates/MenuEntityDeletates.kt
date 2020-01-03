package com.example.mylib_test.adapter.delegates

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.zone.adapter3kt.data.DataWarp
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

/**
 *[2018/7/10] by Zone
 */

class MenuEntityDeletates(val activity: Activity, val colorArry: IntArray, val menu: List<MenuEntity>) : ViewDelegatesDemo<MenuEntity>() {

    override val layoutId: Int = R.layout.item_menu


    override fun onBindViewHolder(position: Int, item: DataWarp<MenuEntity>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
        item.data?.let { entity ->
            baseHolder.setText(R.id.tv, entity.info)
                    .setBackgroundColor(R.id.tv, colorArry[baseHolder.adapterPosition % colorArry.size])
                    .setOnClickListener(View.OnClickListener{
                        val posi = menu.indexOf(entity)
                        if (posi != -1) {
                            val goClass = menu[posi].goClass
                            if (goClass != null) {
                                activity.startActivity(Intent(activity, goClass))
                            }
                        }
                    }, R.id.tv);
        }

    }
}