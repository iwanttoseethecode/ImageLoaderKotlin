package com.example.ImageLoaderKt.loader

import android.graphics.Bitmap
import android.util.Log
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/11.
 * description:
 */
class NullLoader : AbstractLoader() {

    override fun onLoad(request: BitmapRequest): Bitmap? {
        Log.e("luoling","图片无法加载")
        return null;
    }

}