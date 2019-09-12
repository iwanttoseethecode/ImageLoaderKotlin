package com.example.ImageLoaderKt.policy

import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class SerialPolicy :LoadPolicy{
    override fun compareTo(request1: BitmapRequest, request2: BitmapRequest): Int {
        return request1.mSerialNo - request2.mSerialNo;
    }
}