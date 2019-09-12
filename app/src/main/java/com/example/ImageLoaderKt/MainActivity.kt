package com.example.ImageLoaderKt

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.ImageLoaderKt.cache.DiskCache
import com.example.ImageLoaderKt.cache.DoubleCache
import com.example.ImageLoaderKt.config.DisplayConfig
import com.example.ImageLoaderKt.config.ImageLoaderConfig
import com.example.ImageLoaderKt.core.SimpleImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun myClick(view: View){
        var displayConfig = DisplayConfig()
        displayConfig.loadingImg = R.drawable.img_loading
        displayConfig.faildedImg = R.drawable.img_failed

        var imageLoaderConfig:ImageLoaderConfig = ImageLoaderConfig.Builder()
            .setBitmapCache(DoubleCache(this.applicationContext))
            .setDisplayConfig(displayConfig)
            .setDispatcherCount(Runtime.getRuntime().availableProcessors())
            .build()

        var url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1568205072170&di=c6d743379d04eb9956cf05427d5793cd&imgtype=0&src=http%3A%2F%2Fpic40.nipic.com%2F20140405%2F11394704_103014202000_2.jpg"
        SimpleImageLoader.getInstance(imageLoaderConfig).displayImage(IV,url,
            object : SimpleImageLoader.ImageLoaderListener{
                override fun onComplete(imageView: ImageView?, bitmap: Bitmap?, url: String) {
                    Log.d("luoling","图片加载完成")
                    imageView?.setImageBitmap(bitmap)
                }
            });
    }

}
