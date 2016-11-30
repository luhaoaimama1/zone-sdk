package and.utils.activity_fragment_ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by fuzhipeng on 2016/10/21.
 */

public class ActivityTopViewUtils {
    //判断SDK版本是否大于等于19，大于就让他显示，小于就要隐藏，不然低版本会多出来一个
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            UIhelper.setTranslucentStatus(this,true);
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     *
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    /**
     * DecorView(Activity 's Root[顶层]View){
     *   LinearLayout
     *      {
     *          title(ViewStub)
     *          content(FrameLayout
     *          注意 如果keyBoard弹出，
     *          并且模式是windowSoftInputMode="stateHidden|adjustResize，
     *          改变此控件高度){
     *              setContent(setContentView)
     *          }
     *      }
     * }
     * @param activity
     * @return
     */
    public static View getActivitySetContentView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * DecorView(Activity 's Root[顶层]View){
     *   LinearLayout
     *      {
     *          title(ViewStub)
     *          content(FrameLayout
     *          注意 如果keyBoard弹出，
     *          并且模式是windowSoftInputMode="stateHidden|adjustResize，
     *          改变此控件高度){
     *              setContent(setContentView)
     *          }
     *      }
     * }
     * @param activity
     * @return
     */
    public static View getActivityContentView(Activity activity) {
        return  activity.findViewById(android.R.id.content);
    }

    /**
     * DecorView(Activity 's Root[顶层]View){
     *   LinearLayout
     *      {
     *          title(ViewStub)
     *          content(FrameLayout
     *          注意 如果keyBoard弹出，
     *          并且模式是windowSoftInputMode="stateHidden|adjustResize，
     *          改变此控件高度){
     *              setContent(setContentView)
     *          }
     *      }
     * }
     * @param activity
     * @return
     */
    public static FrameLayout getActivityRootView(Activity activity) {
        final FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();
        //这么添加View
//        int w = decorView.getWidth();
//        int h = decorView.getHeight();
//        decorView.addView(view, w, h);
        return decorView;
    }

}
