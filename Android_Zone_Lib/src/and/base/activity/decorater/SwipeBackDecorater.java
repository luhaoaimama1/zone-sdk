package and.base.activity.decorater;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by Administrator on 2016/3/26.
 * 直接把 SwipeBackActivity 拿过来 这样就导致保持继承一个基类
 *
 *<uses-permission android:name="android.permission.VIBRATE"/>
 */
public abstract class SwipeBackDecorater extends FeaturesDecorater implements SwipeBackActivityBase,SwipeBackLayout.SwipeListener {
    private SwipeBackActivityHelper mHelper;
    private static final int VIBRATE_DURATION = 20;
    protected SwipeBackLayout mSwipeBackLayout;
    protected SwipeBack swipeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        mSwipeBackLayout= getSwipeBackLayout();
        setSwipeBackFlag(SwipeBack.LEFT);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public enum SwipeBack{
        LEFT,RIGHT,ALL,BOTTOM;

    }
    public void setSwipeBackFlag(SwipeBack swipeBack){
        this.swipeBack=swipeBack;
        int edgeFlag = SwipeBackLayout.EDGE_LEFT;
        switch (swipeBack) {
            case LEFT:
                //默认就是左边
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
        mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlag);
    }
    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        vibrate(VIBRATE_DURATION);
    }

    @Override
    public void onScrollOverThreshold() {
        vibrate(VIBRATE_DURATION);
    }
    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, duration};
        vibrator.vibrate(pattern, -1);
    }
}
