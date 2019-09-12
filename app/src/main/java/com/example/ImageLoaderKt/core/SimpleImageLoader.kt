package com.example.ImageLoaderKt.core

import android.graphics.Bitmap
import android.widget.ImageView
import com.example.ImageLoaderKt.config.DisplayConfig
import com.example.ImageLoaderKt.config.ImageLoaderConfig
import com.example.ImageLoaderKt.request.BitmapRequest
import com.example.ImageLoaderKt.request.RequestQueue

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class SimpleImageLoader (config:ImageLoaderConfig){

    companion object{

        var simpleImageLoader:SimpleImageLoader? = null

        fun getInstance(config:ImageLoaderConfig):SimpleImageLoader{
            if (simpleImageLoader == null){
                simpleImageLoader = SimpleImageLoader(config)
            }
            return simpleImageLoader!!
        }

        fun getObject():SimpleImageLoader{
            if (simpleImageLoader == null){
                throw UnsupportedOperationException("getInstance(ImageLoaderConfig config) 没有执行过！")
            }
            return simpleImageLoader!!
        }

    }

    lateinit var imageLoaderConfig:ImageLoaderConfig
        private set

    private lateinit var mRequestQueue:RequestQueue

    init {
        imageLoaderConfig = config
        //初始化请求队列
        mRequestQueue = RequestQueue(config.dispatcherCount)
        //开始，请求转发线程开始不断从队列中获取请求，进行转发处理
        mRequestQueue.start()
    }

    fun displayImage(imageView: ImageView,url:String,imageLoadListener:ImageLoaderListener? = null){
        //生成一个请求，添加到队列中
        var request:BitmapRequest = BitmapRequest(imageView,url,imageLoadListener)
        mRequestQueue.addRequest(request)
    }

    public interface ImageLoaderListener{
        fun onComplete(imageView: ImageView?,bitmap: Bitmap?,url:String)
    }

}