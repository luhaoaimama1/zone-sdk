package and.features;

import java.util.ArrayList;
import java.util.List;

import and.abstractclass.BaseActvity;
import android.content.Intent;
import android.os.Bundle;
/**
 * <br>故：onResume以后才能用ExtraFeature　onPause就不能用了
 * <br>注意：onResume()　【从新初始化资源 例如Camera、sensor】
 * <br>onPause()【清除资源 避免浪费  例如Camera、sensor】
 * @author 123
 *
 */
public abstract class FeaturesActivity extends BaseActvity {
	public List<ExtraFeature> featureList= new ArrayList<ExtraFeature>();
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		init2AddFeature();
		for (ExtraFeature item : featureList) {
			item.init();
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		for (ExtraFeature item : featureList) {
			item.onActivityResult(requestCode, resultCode, intent);
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		for (ExtraFeature item : featureList) {
			item.destory();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	protected FeaturesActivity addFeature(ExtraFeature feature) {
		featureList.add(feature);
		return this;
	}
	protected abstract void init2AddFeature() ;
}
