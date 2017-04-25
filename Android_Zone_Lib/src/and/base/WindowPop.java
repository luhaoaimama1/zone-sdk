package and.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * [2017] by Zone
 * 悬浮窗支持
 */
public abstract class WindowPop {

    private WindowManager.LayoutParams wmParams;
    private View mFloatLayout;
    private WindowManager mWindowManager;
    protected Context context;
    private int layoutId;

    /**
     * @param context 尽量用 Application activity和他的生命周期不同!
     * @param context
     */
    public WindowPop(Context context) {
        this.context = context;
    }

    /**
     * @param layoutId
     */
    public void setPopContentView(int layoutId) {
        this.layoutId = layoutId;
        wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        //设置window type  可以设置 屏幕外的层级!
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
        ;

        LayoutInflater inflater = LayoutInflater.from(context);
        //获取浮动窗口视图所在布局
        mFloatLayout = inflater.inflate(layoutId, new FrameLayout(context), false);

    }

    public void show() {
        findView(mFloatLayout);
        initData();
        setListener();
        setLocation();
    }

    public void remove() {
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
        }
    }



    private void updateParams(int gravity, int x, int y) {
        //设置悬浮窗口长宽数据
//        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams lp = mFloatLayout.getLayoutParams();
        wmParams.width = lp.width;
        wmParams.height = lp.height;

        //调整悬浮窗显示的停靠位置为左侧置顶
//        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.gravity = gravity;
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = x;
        wmParams.y = y;
        /* 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/
    }


    public void showAtLocation(int gravity, int x, int y) {
        updateParams(gravity, x, y);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
    }

    public void updateLocation(int gravity, int x, int y) {
        updateParams(gravity, x, y);
        //添加mFloatLayout
        mWindowManager.updateViewLayout(mFloatLayout, wmParams);
    }

    /**
     * 通过父类中的mMenuView找到pop内的控件
     * <br>例如：tv_pop_cancel=(TextView) mMenuView.findViewById(R.id.tv_pop_cancel);
     *
     * @param mMenuView
     */
    protected abstract void findView(View mMenuView);

    protected abstract void initData();

    protected abstract void setListener();

    /**
     * <br>也可以加动画 this.setAnimationStyle(R.style.PopSelectPicAnimation);
     * <br>例子：this.showAtLocation(activity.findViewById(R.id.main), Gravity.BOTTOM	| Gravity.CENTER_HORIZONTAL, 0, 0);
     * <br>并可以更改pop的其他设置
     */
    protected abstract void setLocation();

}
