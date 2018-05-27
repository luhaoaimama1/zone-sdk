package com.example.mylib_test.activity.animal.helper;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.viewa.XfermodeView;
import java.util.ArrayList;
import java.util.List;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.lib.base.BasePopWindow;

/**
 * Created by Administrator on 2016/3/21.
 */
public class PopXfermode extends BasePopWindow {
    private  Button bt_pop;
    private  XfermodeView xfermodeView;
    private RecyclerView lv;
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
        setPopContentView(R.layout.pop_list, -1);
        setBgVisibility(false);
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            listData.add(mode.name());
            System.out.println("mode.name():"+mode.name());
        }

    }


    @Override
    protected void findView(View mMenuView) {
        lv= (RecyclerView) mMenuView.findViewById(R.id.lv);
        lv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        ViewDelegates<String> delegates = new ViewDelegates<String>() {

            @Override
            public void fillData(int i, String s, Holder holder) {
                holder.setText(R.id.tv, s).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_pop.setText(s);
                        xfermodeView.setXferMode(PorterDuff.Mode.valueOf(s));
                        xfermodeView.postInvalidate();
                        dismiss();
                    }
                }, R.id.rl_main);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_textview_only;
            }
        };
        new QuickRcvAdapter<String>(activity,listData)
                .addViewHolder(delegates)
                .relatedList(lv);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setLocation(View view) {
        showAtLocation(view, Gravity.NO_GRAVITY,0,0);
    }
}
