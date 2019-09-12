package com.example.ImageLoaderKt.config

import com.example.ImageLoaderKt.cache.BitmapCache
import com.example.ImageLoaderKt.cache.NoCache
import com.example.ImageLoaderKt.policy.LoadPolicy
import com.example.ImageLoaderKt.policy.SerialPolicy

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class ImageLoaderConfig {
    //图片记载的显示配置
    var displayConfig: DisplayConfig = DisplayConfig()
        private set
    //加载策略（honesty is the best policy）
    var loadPolicy:LoadPolicy = SerialPolicy()

    //缓存策略
    var bitmapCache: BitmapCache = NoCache()
        private set
    //分发器个数
    var dispatcherCount: Int = Runtime.getRuntime().availableProcessors()
        private set

    private constructor(builder: Builder){
        if (builder.displayConfig != null){
            displayConfig = builder.displayConfig!!
        }
        if (builder.loadPolicy != null){
            loadPolicy = builder.loadPolicy!!
        }
        if (builder.bitmapCache != null){
            bitmapCache = builder.bitmapCache!!
        }
        if (builder.dispatcherCount > 0){
            dispatcherCount = builder.dispatcherCount
        }
    }

    public class Builder{
        var displayConfig:DisplayConfig? = null
            private set
        var loadPolicy:LoadPolicy? = null
            private set
        var bitmapCache:BitmapCache? = null
            private set
        var dispatcherCount:Int = 0
            private set

        fun setDisplayConfig(config:DisplayConfig):Builder{
            this.displayConfig = config;
            return this
        }

        fun setLoadPolicy(loadPolicy: LoadPolicy):Builder{
            this.loadPolicy = loadPolicy
            return this
        }

        fun setBitmapCache(bitmapCache: BitmapCache):Builder{
            this.bitmapCache = bitmapCache;
            return this;
        }

        fun setDispatcherCount(threadCount:Int):Builder{
            this.dispatcherCount = threadCount;
            return this;
        }

        fun build():ImageLoaderConfig {
            var imageLoaderConfig:ImageLoaderConfig = ImageLoaderConfig(this)
            return imageLoaderConfig;
        }

    }

}