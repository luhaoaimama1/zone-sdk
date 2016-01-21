package and.image.lruutils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import and.Constant;
import and.abstractclass.adapter.Adapter_MultiLayout_Zone;
import and.image.Compress_Sample_Utils;
import and.image.lruutils.official.DiskLruCache;
import and.log.Logger_Zone;
import and.sd.FileUtils_SD;
import and.utlis.AppUtils;
import and.utlis.IOUtils;
import and.utlis.MD5Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DiskLruUtils {
	private static DiskLruUtils diskLru = new DiskLruUtils();
	private static DiskLruCache mDiskLruCache = null;

	/**
	 * 因为一个应用应该就用一个而不是多个 所以我就final了 想改自己改就ok了
	 */
	private static final String DirName = "bitmap";
	private static final long CacheMax=10 * 1024 * 1024;
	
	private static final String TAG="DiskLruUtils";
	private static boolean writeLog=true;
	private static Logger_Zone logger;
	private static final String encoded="utf-8";
	static{
		logger= new  Logger_Zone(Adapter_MultiLayout_Zone.class,Constant.Logger_Config);
		logger.closeLog();
	}

	private DiskLruUtils() {
	}

	/**
	 * 版本号改变 则自动清除
	 * @param context
	 * @return
	 */
	public  static   DiskLruUtils  openLru(Context context) {
		try {
			/**
			 * open()方法接收四个参数，第一个参数指定的是数据的缓存地址，
			 * 第二个参数指定当前应用程序的版本号，
			 * 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，第四个参数指定最多可以缓存多少字节的数据。
			 */
//			File cacheDir = SdSituation.getDiskCacheDir(context, DirName);
			File cacheDir = FileUtils_SD.getFile("Love",DirName);
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			mDiskLruCache = DiskLruCache.open(cacheDir,AppUtils.getAppVersion(context), 1, CacheMax);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return diskLru;
	}

	
	
	/**
	 * 所以只有成功才调用这个方法
	 * @param url
	 */
	public void addUrl_Bitmap(String url,String path) {
		addUrl_Bitmap(url, Compress_Sample_Utils.getRawBitmap(path));
	}
	/**
	 * 所以只有成功才调用这个方法
	 * @param url
	 */
	public void addUrl_Bitmap(String url, Bitmap bm) {
		// if (downloadUrlToStream(imageUrl, outputStream)) {
		// editor.commit();
		// } else {
		// editor.abort(); //这个就是不提交了~
		// }
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			OutputStream outputStream = editor.newOutputStream(0);  
			if(readToOutStream(bm, outputStream)){
				editor.commit();
				logger.log("addUrl:"+url);
			}else {  
				editor.abort();  
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 所以只有成功才调用这个方法
	 * 
	 * @param url
	 */
	public void addUrl_String(String url,String gsonStr) {
		// if (downloadUrlToStream(imageUrl, outputStream)) {
		// editor.commit();
		// } else {
		// editor.abort(); //这个就是不提交了~
		// }
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			OutputStream outputStream = editor.newOutputStream(0);  
			if(readToOutStream(gsonStr, outputStream)){
				editor.commit();
				logger.log("addUrl:"+url);
			}else {  
				editor.abort();  
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean removeUrl(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			boolean temp= mDiskLruCache.remove(key);
			logger.log("addUrl:"+url+(temp==true?"成功":"失败"));
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Bitmap getBitmapByUrl(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		Bitmap bitmap = null;
		try {
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			if(snapShot != null){
				bitmap = BitmapFactory.decodeStream(snapShot.getInputStream(0));
				logger.log("getBitmapByUrl:"+url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public String getStringByUrl(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		String gsonStr = "";
		try {
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			if(snapShot != null){
				gsonStr=IOUtils.read(snapShot.getInputStream(0), encoded);
				logger.log("getBitmapByUrl:"+url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gsonStr;
	}

	/**
	 * 比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
	 */
	public void flush() {
		try {
			mDiskLruCache.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，
	 * 通常只应该在Activity的onDestroy()方法中去调用close()方法。
	 */
	public void close() {
		try {
			if (!mDiskLruCache.isClosed()) {
				mDiskLruCache.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 这个方法用于将所有的缓存数据全部删除，比如说网易新闻中的那个手动清理缓存功能，其实只需要调用一下DiskLruCache的delete()方法就可以实现了。
	 */
	public void delete() {
		try {
			if (!mDiskLruCache.isClosed()) {
				mDiskLruCache.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 这个方法会返回当前缓存路径下所有缓存数据的总字节数，以byte为单位，
		如果应用程序中需要在界面上显示当前缓存数据的总大小，就可以通过调用这个方法计算出来。比如网易新闻中就有这样一个功能，如下图所示：
	 * @return 
	 */
	public long size(){
		return 	mDiskLruCache.size();
	}
	
	
	private static ByteArrayInputStream bitmapToOs(Bitmap bt){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bt.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		return isBm;
	}
	
	private static boolean readToOutStream(Bitmap bt,OutputStream os){
		InputStream in=bitmapToOs(bt);
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = in.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
		} finally {
			try {
				in.close();
				os.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	private static boolean readToOutStream(String gsonStr,OutputStream os){
		InputStream in = null;
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			in = new ByteArrayInputStream(gsonStr.getBytes(encoded));
			while ((len = in.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
		} finally {
			try {
				in.close();
				os.close();
			} catch (IOException e) {
			}
		}
		return true;
	}
}
