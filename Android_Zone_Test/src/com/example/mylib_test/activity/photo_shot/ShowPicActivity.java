package com.example.mylib_test.activity.photo_shot;
import com.example.mylib_test.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import and.utils.image.compress2sample.SampleUtils;

public class ShowPicActivity extends Activity {
	private ImageView iv_uri,iv_url,iv_provider,iv_assets,iv_drawables;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_show);
		iv_uri = (ImageView) findViewById(R.id.iv_uri);
		iv_url = (ImageView) findViewById(R.id.iv_url);
		iv_provider = (ImageView) findViewById(R.id.iv_provider);
		iv_assets = (ImageView) findViewById(R.id.iv_assets);
		iv_drawables = (ImageView) findViewById(R.id.iv_drawables);
		Uri uri = getIntent().getData();
//		Bitmap bt = Compress_Sample_Utils.getSampleBitmap(uri.toString(), 600, null);
		Bitmap bt = SampleUtils.load(uri.toString()).overrideW(600).bitmap();
		iv_provider.setImageBitmap(null);
		if (uri != null) {
			ImageLoader.getInstance().displayImage("file://"+uri.toString(), iv_uri);
			ImageLoader.getInstance().displayImage("file://"+uri.getPath(), iv_url);
		}
		ImageLoader.getInstance().displayImage("assets://"+"abcd.jpg", iv_assets);
		ImageLoader.getInstance().displayImage("drawable://"+R.drawable.abcd, iv_drawables);
	}

}
