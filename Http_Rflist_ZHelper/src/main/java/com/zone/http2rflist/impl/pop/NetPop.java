package com.zone.http2rflist.impl.pop;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import com.zone.http2rflist.R;
import com.zone.http2rflist.utils.Pop_Zone;

/**
 * Created by Administrator on 2016/4/7.
 */
public class NetPop extends Pop_Zone {
    public NetPop(Activity activity) {
        super(activity);
        setPopContentView(R.layout.pop_net, Mode.Fill, -1);
//        setBgVisibility(false);
    }

    @Override
    protected void findView(View mMenuView) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setLocation(View view) {
        showAtLocation(view, Gravity.NO_GRAVITY,0,0);
    }
}
