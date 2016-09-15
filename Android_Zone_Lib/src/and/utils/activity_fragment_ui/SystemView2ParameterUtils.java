package and.utils.activity_fragment_ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/3/26.
 */
public class SystemView2ParameterUtils {
    public static View getActivityRootView2(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static FrameLayout getActivityRootView(Activity context) {
        final FrameLayout decorView = (FrameLayout) context.getWindow().getDecorView();
        //这么添加View
//        int w = decorView.getWidth();
//        int h = decorView.getHeight();
//        decorView.addView(view, w, h);
        return decorView;
    }

}
