package and.base.activity.kinds.callback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by fuzhipeng on 16/8/30.
 */
public class ActivityKinds {

    public Activity activity;

    public ActivityKinds(Activity activity) {
        this.activity = activity;
    }

    /*
         * @param bundle
         * @param activity 唯一有差别的地方
         */
    public void onCreate(Bundle bundle) {
    }

    public void onPostCreate(@Nullable Bundle savedInstanceState) {
    }

    public void onResume() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    public void onPause() {

    }

    public void onDestroy() {

    }

}
