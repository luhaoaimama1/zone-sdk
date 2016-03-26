package com.example.mylib_test.activity.photo_shot;
import com.example.mylib_test.R;
import and.base.activity.BaseActvity;
import and.base.activity.decorater.features.Feature_Pic;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

public class Photo_Shot_MainActivity extends BaseActvity implements OnClickListener{
	private Feature_Pic feature_Pic;
	@Override
	public void setContentView() {
		System.err.println("Photo_Shot_MainActivity setContentView");
		setContentView(R.layout.a_photo_shot);
	}
	@Override
	public void findIDs() {
		
	}
	@Override
	public void initData() {
		
	}
	@Override
	public void setListener() {
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shot:
			feature_Pic.openCamera();
			break;
		case R.id.photo:
			feature_Pic.openPhotos();
			break;
		case R.id.clip:
			startActivity(new Intent(this,ClipTest.class));
			break;
		default:
			break;
		}
	}

	@Override
	protected void initFeature() {
		feature_Pic = new Feature_Pic(this) {
			@Override
			protected void getReturnedPicPath(String path) {
				System.out.println(path);
				Intent intent = new Intent(Photo_Shot_MainActivity.this,ShowPicActivity.class);
				Uri uri = Uri.parse(path);
				intent.setData(uri);
				startActivity(intent);
			}
		};
		addFeature(feature_Pic);
	}
}
