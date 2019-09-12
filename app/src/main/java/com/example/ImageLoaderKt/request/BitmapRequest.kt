package com.example.ImageLoaderKt.request

import android.widget.ImageView
import com.example.ImageLoaderKt.config.DisplayConfig
import com.example.ImageLoaderKt.core.SimpleImageLoader
import com.example.ImageLoaderKt.policy.LoadPolicy
import com.example.ImageLoaderKt.utils.MD5Utils
import java.lang.ref.SoftReference

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class BitmapRequest : Comparable<BitmapRequest>{

    var mSerialNo:Int = 0
    //图片路径
    lateinit var imageUrl:String
        private set

    //MD5的图片路径
    lateinit var imageMD5Url:String
        private set

    var loadPolicy: LoadPolicy = SimpleImageLoader.getObject().imageLoaderConfig.loadPolicy
        private set

    //图片控件
    //当系统内存不足时，把引用的对象进行回收
    var mImageViewRef:SoftReference<ImageView>? = null
        private set

    var imageListener:SimpleImageLoader.ImageLoaderListener? = null
        private set

    constructor(imageView: ImageView,url:String,imageListener:SimpleImageLoader.ImageLoaderListener?){

        if (url == null){
            throw UnsupportedOperationException("url 不能为空")
        }

        this.mImageViewRef = SoftReference<ImageView>(imageView);
        //设置可见的ImageView的tag为，要下载的图片路径
        imageView.setTag(url)
        this.imageUrl = url;
        this.imageMD5Url = MD5Utils.toMD5(imageUrl!!);
        this.imageListener = imageListener;
    }

    fun getImageView():ImageView?{
        return mImageViewRef?.get()
    }

    override fun compareTo(other: BitmapRequest): Int {
        return loadPolicy.compareTo(this,other)
    }

    override fun hashCode(): Int {
        val prime:Int = 31;
        var result:Int = 1;
        result = prime * result + ( if (loadPolicy == null) 0 else loadPolicy.hashCode());
        result = prime * result + mSerialNo;
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null) return false
        if (this::class.java != other::class.java) return false
        if (other !is BitmapRequest){
            return false
        }
        if (loadPolicy == null){
            if (other.loadPolicy != null){
                return false
            }
        }else if (!loadPolicy.equals(other.loadPolicy)){
            return false;
        }
        if (mSerialNo != other.mSerialNo) return false
        return true
    }

}