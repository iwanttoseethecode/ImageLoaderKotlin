package com.example.ImageLoaderKt.cache

import android.graphics.Bitmap
import android.support.v4.util.LruCache
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class MemoryCache :BitmapCache {

    lateinit var mLruCache:LruCache<String,Bitmap>

    init {
        //缓存的最大值（可用内存的1/8）
        var maxSize:Long = Runtime.getRuntime().freeMemory() / 1024 / 8;
        mLruCache = object: LruCache<String,Bitmap>(maxSize.toInt()){
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.rowBytes * value.height / 1024
            }
        };
    }

    override fun put(request: BitmapRequest, bitmap: Bitmap) {
        mLruCache.put(request.imageMD5Url,bitmap);
    }

    override fun get(request: BitmapRequest): Bitmap?{
        return mLruCache.get(request.imageMD5Url)
    }

    override fun remove(request: BitmapRequest) {
        mLruCache.remove(request.imageMD5Url)
    }
}