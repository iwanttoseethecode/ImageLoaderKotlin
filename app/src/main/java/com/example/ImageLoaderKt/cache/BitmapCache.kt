package com.example.ImageLoaderKt.cache

import android.graphics.Bitmap
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
interface BitmapCache {

    /**
     * 缓存
     * @param request
     * @param bitmap
     */
    fun put(request:BitmapRequest,bitmap:Bitmap)

    /**
     * 获取缓存
     * @param request
     * @return
     */
    fun get(request: BitmapRequest):Bitmap?

    /**
     * 移除缓存
     * @param request
     */
    fun remove(request: BitmapRequest)

}