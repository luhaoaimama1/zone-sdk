package com.example.mylib_test.app.imageloader;
import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//记得设置加载中图片 不然下拉加载http的时候慢了 会显示复用之前的背景就会造成 从复用过来的图变成 加载后的图！！！
public class ImageLoaderOptionsUtils {
	private static int imageOnLoading=-1;
	private static int imageForEmptyUri=-1;
	private static int imageOnFail=-1;
	private static boolean initShowImage_Used=false;
	public static void initShowImage(int imageOnLoading,int imageForEmptyUri,int imageOnFail){
		initShowImage_Used=true;
		ImageLoaderOptionsUtils.imageOnLoading=imageOnLoading;
		ImageLoaderOptionsUtils.imageForEmptyUri=imageForEmptyUri;
		ImageLoaderOptionsUtils.imageOnFail=imageOnFail;
	}
	
	public static Builder getNormalOption＿NotBuild(){
		if(!initShowImage_Used){
			throw new IllegalStateException("please first use initShowImage method !");
		}
		Builder options = new DisplayImageOptions.Builder();
		if (imageOnLoading!=-1) {
			options.showImageOnLoading(imageOnLoading);
		}
		if (imageForEmptyUri!=-1) {
			options.showImageForEmptyUri(imageForEmptyUri);
		}
		if (imageOnFail!=-1) {
			options.showImageOnFail(imageOnFail);
		}
		options.imageScaleType(ImageScaleType.IN_SAMPLE_INT);
		options.cacheInMemory(true);
		options.cacheOnDisk(true);
		options.bitmapConfig(Bitmap.Config.RGB_565); // 设置图片的解码类型
		
//		------------------------可扩展的东西-------------------------------
//		options.considerExifParams(true);  //是否考虑JPEG图像EXIF参数（旋转，翻转）
//	    options.decodingOptions(BitmapFactory.Options decodingOptions);//设置图片的解码配置  
//		options.delayBeforeLoading(0);//int delayInMillis为你设置的下载前的延迟时间
//		options.resetViewBeforeLoading(true);//设置图片在下载前是否重置，复位  
//		options.displayer(new RoundedBitmapDisplayer(20))//不推荐用 因为会重新生成　ARGB8888　的图片！！！！是否设置为圆角，弧度为多少  
//	    options.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间，可能会出现闪动
		return options;
	}

}
