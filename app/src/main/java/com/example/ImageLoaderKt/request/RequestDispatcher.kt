package com.example.ImageLoaderKt.request

import android.util.Log
import com.example.ImageLoaderKt.loader.LoadManager
import com.example.ImageLoaderKt.loader.Loader
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.BlockingDeque
import java.util.concurrent.BlockingQueue

/**
 * Created by luoling on 2019/9/10.
 * description:
 */

class RequestDispatcher(requestQueue: BlockingQueue<BitmapRequest>){

    private lateinit var mRequestQueue:BlockingQueue<BitmapRequest>;

    private var deferred:Deferred<Unit>? = null

    init {
        this.mRequestQueue = requestQueue
    }

    /**
     * 解析图片地址，获取schema
     * @param imageUri
     * @return
     */
    private fun parseSchema(imageUri:String):String?{
        if (imageUri.contains("://")){
            return imageUri.split("://")[0];
        }else{
            Log.e("jason","图片地址schema异常!");
        }
        return null;
    }

    fun run(){
        deferred = GlobalScope.async {
            while (true){
                work()
            }
        }
    }

    fun cancel(){
        deferred?.cancel()
    }

    fun work(){
        var request = mRequestQueue?.take()
        Log.d("luoling", "---处理请求${request.mSerialNo}")
        var schema:String? = parseSchema(request.imageUrl);
        var loader:Loader? = LoadManager.getInstance().getLoader(schema)
        loader?.loadImage(request)
    }

}



