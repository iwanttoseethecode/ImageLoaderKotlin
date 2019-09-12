package com.example.ImageLoaderKt.cache

import android.content.Context
import android.graphics.Bitmap
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class DoubleCache(context: Context) :BitmapCache{

    //内存缓存
    private var mMemoryCache:MemoryCache = MemoryCache()

    //硬盘缓存
    private lateinit var mDiskCache:DiskCache

    init {
        mDiskCache = DiskCache.getInstance(context)
    }

    override fun put(request: BitmapRequest, bitmap: Bitmap) {
        mMemoryCache.put(request,bitmap)
        mDiskCache.put(request,bitmap)
    }

    override fun get(request: BitmapRequest): Bitmap? {
       var bitmap:Bitmap? = mMemoryCache.get(request)
        if (bitmap == null){
            bitmap = mDiskCache.get(request)
            if (bitmap != null){
                mMemoryCache.put(request,bitmap)
            }
        }
        return bitmap
    }

    override fun remove(request: BitmapRequest) {
        mMemoryCache.remove(request)
        mDiskCache.remove(request)
    }
}