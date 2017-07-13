package com.zone.lib.base.activity.kinds;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import com.zone.lib.base.activity.kinds.features.core.ExtraFeature;
import com.zone.lib.base.activity.kinds.callback.ActivityKinds;

/**
 * <br>故：onResume以后才能用ExtraFeature　onPause就不能用了
 * <br>注意：onResume()　【从新初始化资源 例如Camera、sensor】
 * <br>onPause()【清除资源 避免浪费  例如Camera、sensor】
 *
 * @author 123
 */
public class FeaturesKind extends ActivityKinds {
    public List<ExtraFeature> featureList = new ArrayList<ExtraFeature>();
    private boolean isInit;

    public FeaturesKind(Activity activity) {
        super(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isInit) {
            initFeature();
            isInit = true;
        }
        for (ExtraFeature item : featureList) {
            item.init();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        for (ExtraFeature item : featureList) {
            item.onActivityResult(requestCode, resultCode, intent);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        for (ExtraFeature item : featureList) {
            item.destory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public FeaturesKind addFeature(ExtraFeature feature) {
        featureList.add(feature);
        return this;
    }

    public void initFeature() {

    }

    ;
}
