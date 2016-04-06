package and.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/26.
 */
public class UiUtils {
    public static View getActivityRootView(Activity context){
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
