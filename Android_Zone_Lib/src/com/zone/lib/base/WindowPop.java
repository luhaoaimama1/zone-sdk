package com.zone.lib.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.zone.lib.utils.data.info.ScreenUtils;

/**
 * [2017] by Zone
 * 悬浮窗支持
 */
public abstract class WindowPop {

    private boolean isSlide;
    private WindowManager.LayoutParams wmParams;
    private View mFloatLayout;
    private WindowManager mWindowManager;
    protected Context context;
    private int layoutId;
    private SlideViewGroup slideViewGroup;

    private int windowWidth, windowHeight;//屏幕那个高度不会影响太多就不判断了

    /**
     * @param context 尽量用 Application activity和他的生命周期不同!
     * @param context
     */
    public WindowPop(Context context) {
        this(context, false);
    }

    public WindowPop(Context context, boolean isSlide) {
        this.context = context;
        this.isSlide = isSlide;
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
//        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//
//        https://blog.csdn.net/LoveDou0816/article/details/79172637 正常用第二个就行
        //https://developer.android.com/reference/android/view/WindowManager.LayoutParams#TYPE_TOAST
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
//        wmParams.flags =
////          LayoutParams.FLAG_NOT_TOUCH_MODAL |
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
////          LayoutParams.FLAG_NOT_TOUCHABLE
//        ;
        wmParams.flags =
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN
        ;

        LayoutInflater inflater = LayoutInflater.from(context);
        //获取浮动窗口视图所在布局
        mFloatLayout = inflater.inflate(layoutId, new FrameLayout(context), false);
        ViewGroup.LayoutParams lp = mFloatLayout.getLayoutParams();
        wmParams.width = lp.width;
        wmParams.height = lp.height;
        initScreen();
        slideViewGroup = new SlideViewGroup(context, new SlideViewGroup.Callback() {
            @Override
            public void update(int x, int y) {
                if (!isSlide)
                    return;
                updateLocation(wmParams.gravity, wmParams.x + x, wmParams.y + y);
                log("11:--->x:" + x + "\t y:" + y);
                log("12--->x:" + wmParams.x + "\t y:" + wmParams.y);
                wmParams.x -= x;
                wmParams.y -= y;
                log("13restore--->x:" + wmParams.x + "\t y:" + wmParams.y);
            }

            @Override
            public void upCancel(int x, int y) {
                if (!isSlide)
                    return;
                wmParams.x += x;
                if (wmParams.x >= windowWidth - slideViewGroup.getWidth())
                    wmParams.x = windowWidth - slideViewGroup.getWidth();
                else if (wmParams.x < 0)
                    wmParams.x = 0;

                wmParams.y += y;
                if (wmParams.y >= windowHeight - slideViewGroup.getHeight())
                    wmParams.y = windowHeight - slideViewGroup.getHeight();
                else if (wmParams.y < 0)
                    wmParams.y = 0;
            }
        });
        slideViewGroup.setLayoutParams(lp);
        slideViewGroup.addView(mFloatLayout);

    }

    protected void initScreen() {
        int[] pixs = ScreenUtils.getScreenPixByResources(context);
        windowWidth = pixs[0];
        windowHeight = pixs[1];
    }

    public void show() {
        findView(slideViewGroup);
        initData();
        setListener();
        setLocation();
    }

    public void remove() {
        if (slideViewGroup != null) {
            mWindowManager.removeView(slideViewGroup);
        }
    }


    private void updateParams(int gravity, int x, int y) {
        //设置悬浮窗口长宽数据
//        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams lp = slideViewGroup.getLayoutParams();
        wmParams.width = lp.width;
        wmParams.height = lp.height;

        //调整悬浮窗显示的停靠位置为左侧置顶
//        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.gravity = gravity| Gravity.DISPLAY_CLIP_VERTICAL;;
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
        mWindowManager.addView(slideViewGroup, wmParams);
    }

    public void updateLocation(int gravity, int x, int y) {
        updateParams(gravity, x, y);
        //添加mFloatLayout
        mWindowManager.updateViewLayout(slideViewGroup, wmParams);
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


    protected void log(String s) {
        if (1 == 1)
            Log.i("WindowPop", s);
    }


}
