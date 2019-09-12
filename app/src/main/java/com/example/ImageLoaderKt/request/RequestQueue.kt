package com.example.ImageLoaderKt.request

import android.util.Log
import java.util.concurrent.BlockingDeque
import java.util.concurrent.BlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by luoling on 2019/9/10.
 * description:
 */

class RequestQueue(var dispatcherCount:Int){

    private var mRequestQueue: BlockingQueue<BitmapRequest> = PriorityBlockingQueue<BitmapRequest>()
    //一组转发器
    private var mDispachers:Array<RequestDispatcher?>? = null

    private var ai:AtomicInteger = AtomicInteger()

    private var mDispatcherCount:Int? = 0;

    init {
        mDispatcherCount = dispatcherCount
    }

    fun addRequest(request:BitmapRequest){
        if (!mRequestQueue.contains(request)){
            //给请求编号
            request.mSerialNo = ai.incrementAndGet()
            mRequestQueue.add(request)
            Log.d("luoling", "添加请求${request.mSerialNo}")
        }else{
            Log.d("luoling","请求已经存在${request.mSerialNo}")
        }
    }

    fun start(){
        stop()
        startDispatchers()
    }

    fun startDispatchers(){
        mDispachers = arrayOfNulls<RequestDispatcher>(dispatcherCount)
        for (i in 0 until dispatcherCount){
            var p = RequestDispatcher(mRequestQueue)
            mDispachers!![i] = p
            mDispachers!![i]?.run()
        }
    }

    fun stop(){
        if ( mDispachers != null && mDispachers?.size!! > 0){
            //打断
            for (i in 0 until mDispachers?.size!!){
                mDispachers!![i]?.cancel()
            }
        }
    }

}