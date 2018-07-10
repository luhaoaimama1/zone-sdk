package com.example.mylib_test.activity.photo_shot;
import com.bumptech.glide.Glide;
import com.example.mylib_test.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.zone.lib.utils.image.compress2sample.SampleUtils;

import java.io.IOException;

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
		iv_provider.setImageBitmap(null);
		if (uri != null) {
			Glide.with(this).load("file://"+uri.toString()).into(iv_uri);
			Glide.with(this).load("file://"+uri.getPath()).into(iv_url);
		}
		Glide.with(this).load(R.drawable.abcd).into(iv_drawables);

//glide加载 asset		https://github.com/bumptech/glide/issues/155
//		Glide.with(this).load("file:///androd_asset/abcd.jpg").into(iv_assets);
		try {
			iv_assets.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("abcd.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		iv_assets.setImageURI(Uri.parse("file:///android_asset/abcd.jpg"));

	}

}
