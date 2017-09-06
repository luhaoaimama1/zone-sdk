package com.zone.lib.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.notperfectlib.R;

/**
 * [2017] by Zone
 * 参考:http://blog.csdn.net/android_it/article/details/51161038
 */

public abstract class BaseDialog extends Dialog {

    protected final Activity activity;
    private View mMenuView;
    private int bgColor = 0xb0000000;
    private boolean bgVisibility=true;

    /**
     * 因为这个 style才能导致全屏并透明
     * 不然会有padding
     *
     * @param activity
     */
    public BaseDialog(Activity activity) {
        this(activity, R.style.dialog_custom);
    }

    public BaseDialog(Activity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    /**
     * @param layoutid
     * @param dismissViewId pop布局中控件id viewrect范围之外 点击 即可dissming
     */
    public void setDialogContentView(int layoutid, final int dismissViewId) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(mMenuView = inflater.inflate(layoutid, new FrameLayout(activity), false));

        ViewGroup.LayoutParams mMenuViewLp = mMenuView.getLayoutParams();
        getWindow().setLayout(mMenuViewLp.width, mMenuViewLp.height);
        if (bgVisibility) {
            ColorDrawable dw = new ColorDrawable(bgColor);
            getWindow().setBackgroundDrawable(dw);
        }

        //点击Dialog外部消失
        mMenuView.setFocusable(true);
        mMenuView.setFocusableInTouchMode(true);
        setCanceledOnTouchOutside(true);


        if (dismissViewId != -1) {
            // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            mMenuView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        View view = mMenuView.findViewById(dismissViewId);
                        int left = view.getLeft();
                        int right = view.getRight();
                        int top = view.getTop();
                        int bottom = view.getBottom();
                        Rect rect = new Rect(left, top, right, bottom);
                        if (!rect.contains(x, y)) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }
    }

    boolean skipOnCreate;

    @Override
    public void show() {
        //防止每次都初始化   提升性能相对于我
//        if (mMenuView == null)
//            initPop(layoutid, dismissViewId);
        if (!skipOnCreate)
            onCreate();
        setLocation(getWindow());
        super.show();
    }

    private void onCreate() {
        skipOnCreate = true;
        findView(mMenuView);
        initData();
        setListener();
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
     * 也可以加动画
     * Window window = getWindow();
     * window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
     * window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画
     */
    protected abstract void setLocation(Window window);

    /**
     * 例子：构造器中调用     super下面即可  默认可见性为true
     * setBgVisibility(false);
     *
     * @param bgVisibility
     */
    protected void setBgVisibility(boolean bgVisibility) {
        this.bgVisibility = bgVisibility;
    }
}
