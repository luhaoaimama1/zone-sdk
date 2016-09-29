package and.utils.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import and.utils.view.graphics.basic.DrawBind;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class AnimationUtils {


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void explode(View[] ivs, int scale) {
        ViewUtils.recurrenceClipChildren(ivs[0], false);
        float[] center =  DrawBind.bingView(ivs[0].getParent()).center();
        for (int i = 0; i < ivs.length; i++) {
            float[] centerTemp = DrawBind.bingView(ivs[i]).centerParent();
            ivs[i].animate().translationXBy((centerTemp[0] - center[0]) * scale)
                    .translationYBy((centerTemp[1] - center[1]) * scale).alpha(0)
                    .setDuration(3000).start();
        }
    }
}
