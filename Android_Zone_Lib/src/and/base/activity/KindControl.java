package and.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import and.base.activity.kinds.CollectionActivityKind;
import and.base.activity.kinds.FeaturesKind;
import and.base.activity.kinds.ScreenSettingKind;
import and.base.activity.kinds.SwipeBackKind;
import and.base.activity.kinds.callback.ActivityKinds;

/**
 * Created by fuzhipeng on 16/8/30.
 */
public class KindControl extends ActivityKinds {
    public Map<Class, ActivityKinds> controlMap = new HashMap<>();

    public KindControl(Activity activity) {
        super(activity);
    }

    //在onCreate 之前调用不然为空;
    public void initKinds(Activity context) {
        controlMap.put(CollectionActivityKind.class, new CollectionActivityKind(context));
        controlMap.put(FeaturesKind.class, new FeaturesKind(context));
        controlMap.put(ScreenSettingKind.class, new ScreenSettingKind(context));
        controlMap.put(SwipeBackKind.class, new SwipeBackKind(context));
    }

    public <T extends ActivityKinds> T get(Class<T> key) {
        return (T) controlMap.get(key);
    }

    public void put(ActivityKinds value) {
        controlMap.put(value.getClass(), value);
    }

    public void remove(Class key) {
        controlMap.remove(key);
    }

    @Override
    public void onCreate(Bundle bundle) {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onCreate(bundle);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onPostCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onPause() {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onPause();
    }

    @Override
    public void onDestroy() {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onDestroy();
    }
}
