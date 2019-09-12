package com.example.ImageLoaderKt.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.ImageLoaderKt.request.BitmapRequest
import com.example.ImageLoaderKt.utils.BitmapDecoder
import com.example.ImageLoaderKt.utils.ImageViewHelper

/**
 * Created by luoling on 2019/9/11.
 * description:
 */
class LocalLoader :AbstractLoader(){

    override fun onLoad(request: BitmapRequest): Bitmap? {
        Log.d("luoling","加载本地图片")
        val bitmapDecoder = object : BitmapDecoder() {
            override fun decodeBitmapWithOption(options: BitmapFactory.Options):Bitmap{
                return BitmapFactory.decodeFile(request.imageUrl,options)
            }
        }
        var imageView: ImageView? = request.getImageView()

        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(imageView),ImageViewHelper.getImageViewHeight(imageView))
    }

}

