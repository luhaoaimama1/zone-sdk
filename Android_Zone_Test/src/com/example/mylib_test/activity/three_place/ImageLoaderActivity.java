package com.example.mylib_test.activity.three_place;

import com.example.mylib_test.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import and.image.lruutils.DiskLruUtils;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageLoaderActivity extends Activity{
	private ListView lv;
	private String[] imageThumbUrls=new String[]{};
	private DisplayImageOptions options;
	private DiskLruUtils diskLru;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageloader);
		lv=(ListView) findViewById(R.id.listView);
//		imageThumbUrls=Images.imageThumbUrls;
//		lv.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer,null));
		lv.setAdapter(new ImageBaseAdapter());
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		
//		options = new DisplayImageOptions.Builder().build();
		diskLru=DiskLruUtils.openLru(this);
		options = new DisplayImageOptions.Builder()
				// 设置图片下载期间显示的图片  可以不设置就是空白被
//				.showImageOnLoading(R.drawable.ic_stub)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty)
				// 设置图片加载或解码过程中发生错误显示的图片	
				.showImageOnFail(R.drawable.ic_error)
				// 设置是否将View在加载前复位
//				.resetViewBeforeLoading(true)
				//设置启动加载任务前的延迟时间。默认-无延迟。
//				.delayBeforeLoading(1000)
				/**
				   设置图片缩放方式
				 EXACTLY: 图像将完全按比例缩小的目标大小
				 EXACTLY_STRETCHED: 图片会缩放到目标大小
				 IN_SAMPLE_INT: 图像将被二次采样的整数倍
				 IN_SAMPLE_POWER_OF_2: 图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
				 NONE: 图片不会调整
				 */
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) 
				// 设置图片的编码格式为RGB_565，此格式比ARGB_8888快
				.bitmapConfig(Bitmap.Config.RGB_565) // default
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisk(true)
				//设置是否imageloader将JPEG图像的EXIF参数（旋转，翻转）
				.considerExifParams(true)
				//设置自定义显示器    可以设置圆角,不需要请删除
				.displayer(new RoundedBitmapDisplayer(20))
				.build();
	};
	
	private class ImageBaseAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return imageThumbUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return imageThumbUrls[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView==null){
				 vh=new ViewHolder();
				convertView=LayoutInflater.from(ImageLoaderActivity.this).inflate(R.layout.imageitem, null);
				ImageView iv=(ImageView) convertView.findViewById(R.id.iv);
				vh.iv=iv;
				convertView.setTag(vh);
			}else{
				vh=(ViewHolder) convertView.getTag();
			}
//			Bitmap bitMapTemp = diskLru.getBitmapByUrl(imageThumbUrls[position]);
//			if(bitMapTemp!=null){
//				vh.iv.setImageBitmap(bitMapTemp);
//			}else{
				ImageLoader.getInstance().displayImage(imageThumbUrls[position], vh.iv, options,new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
//						 fadeInDisplay((ImageView)view, loadedImage);
//						diskLru.addUrl(imageUri, loadedImage);
						((ImageView)view).setImageBitmap(loadedImage);
//						diskLru.addUrl(url, imageUri);
					}
				
					private void fadeInDisplay(ImageView view, Bitmap loadedImage) {
						final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
						final TransitionDrawable transitionDrawable =
				                new TransitionDrawable(new Drawable[]{
				                        TRANSPARENT_DRAWABLE,
				                        new BitmapDrawable(view.getResources(), loadedImage)
				                });
						view.setImageDrawable(transitionDrawable);
				        transitionDrawable.startTransition(500);
					}
				});
//			}
//			ImageLoader.getInstance().displayImage(imageThumbUrls[position], vh.iv);
			return convertView;
		}
		public class ViewHolder{
			ImageView iv;
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		System.err.println("大小bytes："+diskLru.size());
		diskLru.flush();
		diskLru.delete();
		diskLru.close();
	}
}
