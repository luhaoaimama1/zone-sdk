package com.example.mylib_test.activity.three_place;

import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;

import and.image.lruutils.DiskLruUtils;
import and.log.ToastUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

@SuppressLint("NewApi")
public class ImageLoaderGridActivity extends Activity implements OnClickListener{
	private GridView gridView1;
	private String[] imageThumbUrls;
	private DiskLruUtils diskLru;
	private ScrollView sl;
	private List<String> temp=new ArrayList<String>();
	private Adapter adapter;
//	private DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LruCache<String, Bitmap> a = new LruCache<String, Bitmap>(100){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return super.sizeOf(key, value);
			}
		};
		diskLru=DiskLruUtils.openLru(this);
		setContentView(R.layout.a_thirdparty_imageloader_grid);
		gridView1=(GridView) findViewById(R.id.gridView1);

		sl=(ScrollView) findViewById(R.id.sl);
//		gridView1.setFocusable(false);//这个也 可以解决ScrollView起始位置不是最顶部的解决办法
		sl.smoothScrollTo(0,0);//这个也 可以解决ScrollView起始位置不是最顶部的解决办法
	
//		Images.imageThumbUrls
		for (int i = 0; i < Images.imageThumbUrls.length; i++) {
			temp.add(Images.imageThumbUrls[i]);
		}
		gridView1.setAdapter(adapter=new Adapter(this, temp));
//		gridView1.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));
		
//		options = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.ic_stub)
//		.showImageForEmptyUri(R.drawable.ic_empty)
//		.showImageOnFail(R.drawable.ic_error)
//		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//		.cacheInMemory(true)
//		.cacheOnDisk(true)
//		.bitmapConfig(Bitmap.Config.RGB_565)	 //设置图片的解码类型
//		.build();
	}
	public class Adapter extends Adapter_Zone<String> {

		public Adapter(Context context, List<String> data) {
			super(context, data);
		}

		@Override
		public int setLayoutID() {
			// TODO Auto-generated method stub
			return  R.layout.imageitem;
		}

		@Override
		public void setData(ViewHolder_Zone holder, String data, int position) {
			 ImageView iv=(ImageView) holder.findViewById(R.id.iv);
//				ImageLoader.getInstance().displayImage(data, iv,options);
				ImageLoader.getInstance().displayImage(data, iv);
				iv.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ToastUtils.showLong(ImageLoaderGridActivity.this, "你干啥");
					}
				});
//				Bitmap bm = diskLru.getBitmapByUrl(data);
//				if (bm==null) {
//					ImageLoader.getInstance().displayImage(data, iv,new SimpleImageLoadingListener(){
//						@Override
//						public void onLoadingComplete(String imageUri, View view,
//								Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							diskLru.addUrl(imageUri, loadedImage);
//						}
//					});
//				} else
//					iv.setImageBitmap(bm);
		}
		
	} 
	@Override
	protected void onDestroy() {
		diskLru.flush();
		diskLru.delete();
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv:
			temp.add(temp.get(0));
			temp.add(temp.get(0));
			temp.add(temp.get(0));
			temp.add(temp.get(0));
			adapter.notifyDataSetChanged();
			break;
		case R.id.tv1:
			temp.add(temp.get(1));
			temp.add(temp.get(1));
			temp.add(temp.get(1));
			temp.add(temp.get(1));
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		
	}

}
