package and.base.activity.kinds;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import and.base.activity.kinds.callback.ActivityKinds;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Administrator on 2016/3/26.
 * 直接把 SwipeBackActivity 拿过来 这样就导致保持继承一个基类
 *因为我吧 震动也集成了所以  需要加上此权限
 *<uses-permission android:name="android.permission.VIBRATE"/>
 */
public  class SwipeBackKind extends ActivityKinds implements SwipeBackActivityBase,SwipeBackLayout.SwipeListener {
    private static final int VIBRATE_DURATION = 20;
    private SwipeBackActivityHelper mHelper;
    protected SwipeBackLayout mSwipeBackLayout;
    protected SwipeBack swipeBack;

    public SwipeBackKind(Activity activity) {
        super(activity);
    }


    public void onCreate(Bundle savedInstanceState, Activity activity) {
        mHelper = new SwipeBackActivityHelper(activity);
        mHelper.onActivityCreate();

        mSwipeBackLayout= getSwipeBackLayout();
        setSwipeBackFlag(SwipeBack.LEFT);
    }

  
    public void onPostCreate(Bundle savedInstanceState) {
        mHelper.onPostCreate();
    }

  
    public View findViewById(int id) {
        View v = activity.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

  
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

  
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

  
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(activity);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public enum SwipeBack{
        NONE,LEFT,RIGHT,ALL,BOTTOM;

    }
    public void setSwipeBackFlag(SwipeBack swipeBack){
        this.swipeBack=swipeBack;
        int edgeFlag = -1;
        switch (swipeBack) {
            case NONE:
                //默认就是左边
                break;
            case LEFT:
                //默认就是左边
                edgeFlag = SwipeBackLayout.EDGE_LEFT;
                break;
            case RIGHT:
                edgeFlag= SwipeBackLayout.EDGE_RIGHT;
                break;
            case ALL:
                edgeFlag= SwipeBackLayout.EDGE_ALL;
                break;
            case BOTTOM:
                edgeFlag= SwipeBackLayout.EDGE_BOTTOM;
                break;
        }
        if (edgeFlag != -1)
            mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
    }
  
    public void onScrollStateChange(int state, float scrollPercent) {

    }

  
    public void onEdgeTouch(int edgeFlag) {
        vibrate(VIBRATE_DURATION);
    }

  
    public void onScrollOverThreshold() {
        vibrate(VIBRATE_DURATION);
    }
    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, duration};
        vibrator.vibrate(pattern, -1);
    }
}
