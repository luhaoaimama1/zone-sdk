package com.zone.lib.utils.image.lruutils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

import com.zone.lib.utils.image.lruutils.official.LruCacheWrapper;

/**
 * Created by Administrator on 2016/5/25.
 * 一个app公用一个LruCache
 */
public class LruCacheUtils {
    private static volatile LruCacheUtils instance;

    private int MAXMEMONRY = (int) (Runtime.getRuntime().maxMemory() / 1024);

    private LruCacheWrapper<String, Bitmap> mMemoryCache;

    protected LruCacheUtils() {
        if (mMemoryCache == null)
            mMemoryCache = new LruCacheWrapper<String, Bitmap>(MAXMEMONRY / 8) {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
    }

    ;

    public final LruCacheWrapper<String, Bitmap> getInstance() {
        if (instance == null) {
            synchronized (LruCacheUtils.class) {
                if (instance == null)
                    instance = new LruCacheUtils();
            }
        }
        return instance.mMemoryCache;
    }
}
