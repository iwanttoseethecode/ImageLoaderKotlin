package com.example.ImageLoaderKt.policy

import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
interface LoadPolicy{

    /**
     * 两个BitmapRequest进行比较
     * @param request1
     * @param request2
     * @return 小于0，request1 < request2，大于0，request1 > request2，等于
     */
    fun compareTo(request1:BitmapRequest,request2:BitmapRequest):Int;

}
