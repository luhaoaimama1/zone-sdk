package com.zone.lib.utils.image.lruutils.official;

import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2016/5/25.
 */
public abstract class LruCacheWrapper<K, V>  extends LruCache<K, V>  {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruCacheWrapper(int maxSize) {
        super(maxSize);
    }
}
