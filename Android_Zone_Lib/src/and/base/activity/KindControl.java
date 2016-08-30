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

    //在onCreate 之前调用不然为空;
    public void initKinds() {
        controlMap.put(CollectionActivityKind.class, new CollectionActivityKind());
        controlMap.put(FeaturesKind.class, new FeaturesKind());
        controlMap.put(ScreenSettingKind.class, new ScreenSettingKind());
        controlMap.put(SwipeBackKind.class, new SwipeBackKind());
    }

    public <T extends ActivityKinds> T get(Class<T> key) {
        return (T) controlMap.get(key);
    }

    public void put(Class key, ActivityKinds value) {
        controlMap.put(FeaturesKind.class, new FeaturesKind());
    }

    public void remove(Class key, ActivityKinds value) {
        controlMap.remove(FeaturesKind.class);
    }

    @Override
    public void onCreate(Bundle bundle, Activity context) {
        for (Map.Entry<Class, ActivityKinds> item : controlMap.entrySet())
            item.getValue().onCreate(bundle, context);
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
