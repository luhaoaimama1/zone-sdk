package com.example.mylib_test.activity.animal.helper;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.viewa.XfermodeView;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;

import java.util.ArrayList;
import java.util.List;

import and.base.Pop_Zone;

/**
 * Created by Administrator on 2016/3/21.
 */
public class PopXfermode extends Pop_Zone {
    private  Button bt_pop;
    private  XfermodeView xfermodeView;
    private ListView lv;
    List<String> listData=new ArrayList<>();
    /**
     * 仅仅调用show()即可
     * <br>默认颜色　　是浅黑色
     *
     * @param activity             在那个activity 弹出pop
     */
    public PopXfermode(Activity activity,  XfermodeView xfermodeView, Button bt_pop) {
        super(activity);
        this.xfermodeView=xfermodeView;
        this.bt_pop=bt_pop;
        setPopContentView(R.layout.pop_list, Mode.Fill, -1);
        setBgVisibility(false);
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            listData.add(mode.name());
            System.out.println("mode.name():"+mode.name());
        }

    }


    @Override
    protected void findView(View mMenuView) {
        lv= (ListView) mMenuView.findViewById(R.id.lv);
    }

    @Override
    protected void initData() {
        lv.setAdapter(new QuickAdapter<String>(activity,listData) {
            @Override
            public void convert(Helper helper, final String item, boolean itemChanged, int layoutId) {
                helper.setText(R.id.tv,item).setOnClickListener(R.id.rl_main,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_pop.setText(item);
                        xfermodeView.setXferMode(PorterDuff.Mode.valueOf(item));
                        xfermodeView.postInvalidate();
                        dismiss();
                    }
                });
            }

            @Override
            public int getItemLayoutId(String s, int position) {
                return  R.layout.item_textview_only;
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setLocation(View view) {
        showAtLocation(view, Gravity.NO_GRAVITY,0,0);
    }
}
