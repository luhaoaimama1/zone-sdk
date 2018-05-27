package com.example.mylib_test;

import com.example.mylib_test.activity.db.entity.MenuEntity;
import com.example.mylib_test.delegates.MenuEntityDeletates;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.adapter3.loadmore.OnScrollRcvListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends Activity implements Handler.Callback {
    private RecyclerView listView1;
    private int positonId = -1;
    private AlertDialog alert;
    private IAdapter<MenuEntity> adapter2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_menu);

        createDialog();
        listView1 = (RecyclerView) findViewById(R.id.listView1);
        final int[] colorArry = {Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN};
        adapter2 = new QuickRcvAdapter<MenuEntity>(this, MainMenu.menu)
                .addViewHolder(new MenuEntityDeletates(this, colorArry, MainMenu.menu))
                .addHeaderHolder(R.layout.header_simple)
                .addFooterHolder(R.layout.footer_simple)
                .relatedList(listView1)
                .setOnItemLongClickListener(new IAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(ViewGroup viewGroup, View view, int i) {
                        positonId = i;
                        alert.show();
                        return false;
                    }
                })
                .addOnScrollListener(new OnScrollRcvListener() {
                    boolean refesh = true;

                    @Override
                    protected void loadMore(RecyclerView recyclerView) {
                        super.loadMore(recyclerView);
                        final List<MenuEntity> mDatasa = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            mDatasa.add(new MenuEntity("insert " + i, null));
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (refesh) {
                                    adapter2.loadMoreComplete();
                                    adapter2.getData().addAll(mDatasa);
                                    adapter2.notifyDataSetChanged();
                                } else {
                                    adapter2.loadMoreFail();
                                }
                                refesh = !refesh;
                            }
                        }, 1000);
                    }
                });


        //通过加载XML动画设置文件来创建一个Animation对象；
//		Animation animation= AnimationUtils.loadAnimation(this, R.anim.scale_in);   //得到一个LayoutAnimationController对象；
//		LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
//		controller.setOrder(LayoutAnimationController.ORDER_RANDOM);   //设置控件显示间隔时间；
//		controller.setDelay(0.3F);   //为ListView设置LayoutAnimationController属性；
//		listView1.setLayoutAnimation(controller);
//		listView1.startLayoutAnimation();


//		CustomLayoutAnimationController controller2 = new CustomLayoutAnimationController(animation);   //设置控件显示的顺序；
//		controller2.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
//		controller2.setDelay(2F);   //为ListView设置LayoutAnimationController属性；
//		listView1.setLayoutAnimation(controller2);
//		listView1.startLayoutAnimation();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to remove?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (positonId != -1) {
                            MainMenu.menu.remove(positonId);
                            positonId = -1;
                            adapter2.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
