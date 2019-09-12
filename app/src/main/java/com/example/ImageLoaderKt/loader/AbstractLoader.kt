package com.example.ImageLoaderKt.loader

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.ImageLoaderKt.config.DisplayConfig
import com.example.ImageLoaderKt.core.SimpleImageLoader
import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/10.
 * description:
 */

abstract class AbstractLoader : Loader{

    //缓存策略
    private var mCache = SimpleImageLoader.getObject().imageLoaderConfig.bitmapCache

    //显示配置
    private var mDisplayConfig:DisplayConfig = SimpleImageLoader.getObject().imageLoaderConfig.displayConfig

    override fun loadImage(request: BitmapRequest?) {
        var bitmap = mCache.get(request!!)
        if (bitmap == null){
            //加载前显示的图片
            showLoadingImage(request)

            //加载完成，再缓存
            //具体的加载方式，由子类决定
            bitmap = onLoad(request)
            cacheBitmap(request,bitmap)
        }
        deliveryToUIThread(request,bitmap)
    }

    /**
     * 加载前显示的图片
     * @param request
     */
    fun showLoadingImage(request:BitmapRequest){
        if (hasLoadingPlaceHolder()){
            var imageView = request.getImageView()
            imageView?.post(Runnable { imageView.setImageResource(mDisplayConfig.loadingImg) })
        }
    }

    protected fun hasLoadingPlaceHolder():Boolean{
        return (mDisplayConfig != null && mDisplayConfig.loadingImg > 0)
    }

    protected fun hasFailedPlaceHolder():Boolean{
        return (mDisplayConfig != null && mDisplayConfig.faildedImg > 0)
    }

    @Synchronized
    private fun cacheBitmap(request:BitmapRequest?,bitmap:Bitmap?){
        if (request != null && bitmap != null){
            mCache.put(request,bitmap)
        }
    }

    protected fun deliveryToUIThread(request: BitmapRequest?,bitmap: Bitmap?){
        var imageView:ImageView? = request?.getImageView();
        imageView?.post({updateImageView(request,bitmap)})
    }

    protected fun updateImageView(request: BitmapRequest?,bitmap: Bitmap?){
        var imageView:ImageView? = request?.getImageView()
        //加载正常
        if (bitmap != null && imageView?.getTag()!!.equals(request?.imageUrl)){
            imageView?.setImageResource(mDisplayConfig.faildedImg)
        }
        //有可能加载失败
        if (bitmap == null && hasFailedPlaceHolder()){
            imageView?.setImageResource(mDisplayConfig.faildedImg)
        }
        request?.imageListener?.onComplete(imageView,bitmap,request.imageUrl)
    }

    /**
     * 具体的加载实现
     * @param request
     * @return
     */
    protected abstract fun onLoad(request: BitmapRequest): Bitmap?

}