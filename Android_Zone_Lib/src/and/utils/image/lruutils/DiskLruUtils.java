package and.utils.image.lruutils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import and.utils.image.compress2sample.SampleUtils;
import and.utils.image.lruutils.official.DiskLruCache;
import and.LogUtil;
import and.utils.data.file2io2data.IOUtils;
import and.utils.data.convert.de2encode.MD5Utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DiskLruUtils {
	private static final String DirName = "DiskLruUtils";
	private static final long CacheMax=10 * 1024 * 1024;
	private static final String TAG="DiskLruUtils";
	private static final String encoded="utf-8";
	private static File saveFolder;
	private static DiskLruUtils diskLru = new DiskLruUtils();
	private static DiskLruCache mDiskLruCache = null;
	/**
	 * 因为一个应用应该就用一个而不是多个 所以我就final了 想改自己改就ok了
	 */

	private DiskLruUtils() {
	}
	public static void  setSaveFolder(File saveFolder){
		DiskLruUtils.saveFolder=saveFolder;
	}
	public static File  getSaveFolder(){
		return DiskLruUtils.saveFolder;
	}
	/**
	 * 版本号改变 则自动清除
	 * @param context
	 * @return
	 */
	public  static   DiskLruUtils  openLru(Context context) {
		try {
			File cacheDir = null;
			if (saveFolder==null) {
				String cachePath = context.getCacheDir().getPath();
				cacheDir = new File(cachePath + File.separator + DirName);
			}else{
				cacheDir=saveFolder;
			}
			/*
			 *activity.getCacheDir().getPath()
			 *但是通常情况下多数应用程序都会将缓存的位置选择为 /sdcard/Android/data/<application package>/cache 这个路径
 			 * 选择在这个位置有两点好处：第一，这是存储在SD卡上的，因此即使缓存再多的数据也不会对手机的内置存
			 * 储空间有任何影响，只要SD卡空间足够就行。第二，这个路径被Android系统认定为应用程序的缓存路径，
			 * 当程序被卸载的时候，这里的数据也会一起被清除掉，这样就不会出现删除程序之后手机上还有很多残留数据的问题。
			 *
 			 */
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
//			mDiskLruCache = DiskLruCache.open(cacheDir,AppUtils.getAppVersion(activity), 1, CacheMax);
			/**
			 * open()方法接收四个参数，第一个参数指定的是数据的缓存地址，
			 * 第二个参数指定当前应用程序的版本号， 考虑版本号改变 缓存文件也是有效的。。。
			 * 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，第四个参数指定最多可以缓存多少字节的数据。
			 */
			mDiskLruCache = DiskLruCache.open(cacheDir,1, 1, CacheMax);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return diskLru;
	}

	
	
	/**
	 * 所以只有成功才调用这个方法
	 * @param url
	 */
	public void addBitmap(String url, String path) {
		addBitmap(url, SampleUtils.load(path).bitmap());
	}
	/**
	 * 所以只有成功才调用这个方法
	 * @param url
	 */
	public void addBitmap(String url, Bitmap bm) {
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			OutputStream outputStream = editor.newOutputStream(0);  
			if(readToOutStream(bm, outputStream)){
				editor.commit();
				LogUtil.d("addBitmap:" + url);
			}else {  
				editor.abort();
				LogUtil.e("addBitmap Fail:" + url);
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
	public void addString(String url, String contentStr) {
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			if (editor!=null) {
				OutputStream outputStream = editor.newOutputStream(0);
				if(readToOutStream(contentStr, outputStream)){
                    editor.commit();
                    LogUtil.d("addString  :" + url);
                }else {
                    editor.abort();
					LogUtil.e("addString Fail:" + url);
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean remove(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		try {
			boolean temp= mDiskLruCache.remove(key);
			LogUtil.d("addUrl:" + url + (temp == true ? "成功" : "失败"));
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Bitmap getBitmap(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		Bitmap bitmap = null;
		try {
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			if(snapShot != null){
				bitmap = BitmapFactory.decodeStream(snapShot.getInputStream(0));
				LogUtil.d("getBitmap:" + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public String getString(String url) {
		String key = MD5Utils.hashKeyForDisk(url);
		String contentStr = "";
		try {
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			if(snapShot != null){
				contentStr =IOUtils.read(snapShot.getInputStream(0), encoded);
				LogUtil.d("getString key:" + url);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentStr;
	}

	/**
	 * 比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
	 *
	 * 解释：
	 * 这个方法用于将内存中的操作记录同步到日志文件（也就是journal文件）当中。这个方法非常重要，
	 * 因为DiskLruCache能够正常工作的前提就是要依赖于journal文件中的内容。前面在讲解写入缓存操作的时候我有调用过一次这个方法，
	 * 但其实并不是每次写入缓存都要调用一次flush()方法的，频繁地调用并不会带来任何好处，
	 * 只会额外增加同步journal文件的时间。比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
	 *
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
			if (!mDiskLruCache.isClosed())
				mDiskLruCache.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 这个方法用于将所有的缓存数据全部删除，比如说网易新闻中的那个手动清理缓存功能，其实只需要调用一下DiskLruCache的delete()方法就可以实现了。
	 */
	public void delete() {
		try {
			if (!mDiskLruCache.isClosed())
				mDiskLruCache.delete();
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
		int len;
		try {
			while ((len = in.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
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
			System.err.println(e.getMessage());
			return false;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
		}
		return true;
	}



}
