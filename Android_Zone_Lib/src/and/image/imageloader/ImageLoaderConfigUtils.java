package and.image.imageloader;

import java.io.File;

import and.utlis.ScreenUtils;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class ImageLoaderConfigUtils {
	public static void initImageLoader(Context context,boolean writeDebugLogs) {
		initImageLoader(context, null,writeDebugLogs);
	}
	/**
	 * 内存溢出的话 可以找笔记
	 * @param context
	 */
	public static void initImageLoader(Context context,DisplayImageOptions defaultDisplayImageOptions,boolean writeDebugLogs) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		
		//路径是：/data/data/com.example.mylib_test/cache 要加image
		File cacheDir =new File(context.getCacheDir(),"images");
		
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		/** ==========================线程方面 =========================*/		
		// 设置显示图片线程池大小，默认为3
		config.threadPoolSize(3);
		// 设定线程等级比普通低一点
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		//设置用于加载和显示图像的任务的队列处理类型。
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		/** ==========================内存缓存  =========================*/
		//图片解码期间  中bitmap的宽高 默认是  手机宽高  　那就默认了～
		config.memoryCacheExtraOptions(480, 800);
		/**
		 * memoryCache(...)和memoryCacheSize(...)这两个参数会互相覆盖，所以在ImageLoaderConfiguration中使用一个就好了
		 */
		// 内存缓存的最大值
		config.memoryCache(new LruMemoryCache(2 * 1024 * 1024));
		//缓存到内存的最大数据 
//		config.memoryCacheSize(2 * 1024 * 1024);
		// 设定只保存同一尺寸的图片在内存
		config.denyCacheImageMultipleSizesInMemory();
		/** ==========================文件缓存  =========================*/
		int[] screenParams=ScreenUtils.getScreenPixByContext(context);
		//下载图片后 compress保存到文件中的 宽高
		config.diskCacheExtraOptions(screenParams[0], screenParams[1], null);
		/**
		 * diskCacheSize(...)、diskCache(...)和diskCacheFileCount(...)这三个参数会互相覆盖，只使用一个
		 */
		// 设定缓存的SDcard目录，UnlimitDiscCache速度最快
		config.diskCache(new UnlimitedDiskCache(cacheDir));
		//缓存到文件的最大数据   50 MiB
		config.diskCacheSize(50 * 1024 * 1024); 
		//文件数量
		//config.diskCacheFileCount(1000); 
		
		// //将保存的时候的URI名称用MD5 加密
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator()); 
	
		//BaseImageDecoder  true:打印log  false:你懂的
		config.imageDecoder(new BaseImageDecoder(true));
		
		/** ==========================超时 与log打印  =========================*/
		// 设定网络连接超时 timeout: 10s 读取网络连接超时read timeout: 45s
		config.imageDownloader(new BaseImageDownloader(context, 10000, 45000));
	
		if (defaultDisplayImageOptions!=null) {
			//如果你的程序中使用displayImage（）方法时传入的参数经常是一样的，那么一个合理的解决方法是一下
			config.defaultDisplayImageOptions(defaultDisplayImageOptions);
		}
		if (writeDebugLogs) {
			//打印log
			config.writeDebugLogs(); // Remove for release app
		}
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
