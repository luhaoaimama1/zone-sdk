package and.image.lruutils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
//一个app公用一个LruCache
public class LruCacheUtils {
	private static LruCacheUtils instance=new LruCacheUtils();
	private static final String TAG = "LruCacheUtils";
	private LruCache<String, Bitmap> mMemoryCache;
	private int MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);
	public static LruCacheUtils getInstance(){
		return instance;
	}
	private LruCacheUtils() {
	        if (mMemoryCache == null)
	            mMemoryCache = new LruCache<String, Bitmap>( MAXMEMONRY / 8) {
	                @Override
	                protected int sizeOf(String key, Bitmap bitmap) {
	                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
	                    return bitmap.getByteCount()/ 1024;
	                }
	            };
	    }
	public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                Log.d(TAG, "mMemoryCache.size() " + mMemoryCache.size());
                mMemoryCache.evictAll();
                Log.d(TAG, "mMemoryCache.size()" + mMemoryCache.size());
            }
            mMemoryCache = null;
        }
    }

    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null)
                mMemoryCache.put(key, bitmap);
        } else
            Log.w(TAG, "the res is aready exits");
    }

    public synchronized Bitmap getBitmapFromMemCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        if (key != null) {
            return bm;
        }
        return null;
    }

    /**
     * 移除缓存
     * 
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }
}
