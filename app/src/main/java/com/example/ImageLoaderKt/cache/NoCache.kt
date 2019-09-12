package com.example.ImageLoaderKt.cache

import android.graphics.Bitmap
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class NoCache : BitmapCache {
    override fun put(request: BitmapRequest, bitmap: Bitmap) {

    }

    override fun get(request: BitmapRequest): Bitmap?{
        return null;
    }

    override fun remove(request: BitmapRequest) {

    }
}