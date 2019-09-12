package com.example.ImageLoaderKt.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by luoling on 2019/9/11.
 * description:
 */

abstract class BitmapDecoder{

    abstract fun decodeBitmapWithOption(options: BitmapFactory.Options): Bitmap?;

    fun decodeBitmap(reqWidth:Int,reqHeight:Int):Bitmap?{
        var options:BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        options.inPreferredConfig = Bitmap.Config.RGB_565
        decodeBitmapWithOption(options)
        calculateSampleSizeWidthOption(reqWidth,reqHeight,options)

        return decodeBitmapWithOption(options)
    }

    private fun calculateSampleSizeWidthOption(reqWidth: Int,reqHeight: Int,options: BitmapFactory.Options){
        var width:Int = options.outWidth
        var height:Int = options.outHeight
        options.inSampleSize = 1;
        if ((width > reqWidth) || (height > reqHeight)){
            var widthRatio:Int = Math.round(width / reqWidth + 0.5f)
            var heightRatio:Int = Math.round(height / reqHeight + 0.5f)

            options.inSampleSize = Math.max(widthRatio,heightRatio)
        }
        options.inJustDecodeBounds = false;
    }

}