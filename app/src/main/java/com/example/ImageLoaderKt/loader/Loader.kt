package com.example.ImageLoaderKt.loader

import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/10.
 * description:
 */
interface Loader {

    /**
     * 加载图片
     * @param request
     */
    fun loadImage(request:BitmapRequest?)

}