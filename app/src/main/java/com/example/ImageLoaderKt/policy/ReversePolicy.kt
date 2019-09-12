package com.example.ImageLoaderKt.policy

import com.example.ImageLoaderKt.request.BitmapRequest

/**
 * Created by luoling on 2019/9/9.
 * description:
 */

class ReversePolicy :LoadPolicy{



    override fun compareTo(request1: BitmapRequest, request2: BitmapRequest):Int {
        return request2.mSerialNo - request1.mSerialNo;
    }


}
