package com.example.mylib_test.activity.animal.helper;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.animal.XfermodeActivity;
import com.example.mylib_test.activity.animal.viewa.XfermodeView;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;

import java.util.ArrayList;
import java.util.List;

import and.abstractclass.Pop_Zone;

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
     * @param showAtLocationViewId
     */
    public PopXfermode(Activity activity, int showAtLocationViewId, XfermodeView xfermodeView, Button bt_pop) {
        super(activity, showAtLocationViewId);
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
        lv.setAdapter(new Adapter_Zone<String>(activity,listData) {
            @Override
            public void setData(ViewHolder_Zone holder, final String data, int position) {
                TextView tv = (TextView) holder.findViewById(R.id.tv);
                tv.setText(data);
                holder.findViewById(R.id.rl_main).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt_pop.setText(data);
                        xfermodeView.setXferMode(PorterDuff.Mode.valueOf(data));
                        xfermodeView.postInvalidate();
                        dismiss();
                    }
                });

            }

            @Override
            public int setLayoutID() {
                return R.layout.item_textview_only;
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
