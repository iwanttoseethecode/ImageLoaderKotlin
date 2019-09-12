package com.example.ImageLoaderKt.utils

import android.view.ViewGroup
import android.widget.ImageView

/**
 * Created by luoling on 2019/9/11.
 * description:
 */

object ImageViewHelper{

    var DEFAULT_WIDTH:Int = 200
    var DEFAULT_HEIGHT:Int = 200

    public fun getImageViewWidth(imageView:ImageView?):Int{
        if (imageView != null){
            var layoutParams: ViewGroup.LayoutParams = imageView.layoutParams
            var width = 0
            if (layoutParams != null && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT){
              width = imageView.width
            }
            if (width <= 0 && layoutParams != null){
                width = layoutParams.width
            }
            if (width < 0){
                width = getImageViewFieldValue(imageView,"mMaxWidth")
            }
            return width
        }
        return DEFAULT_WIDTH
    }

    public fun getImageViewHeight(imageView: ImageView?):Int{
        if (imageView != null){
            var layoutParams:ViewGroup.LayoutParams = imageView.layoutParams
            var height:Int = 0;
            if (layoutParams != null && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT){
              height =imageView.height
            }
            if (height <= 0 && layoutParams != null){
                height = layoutParams.height
            }
            if (height < 0){
                height = getImageViewFieldValue(imageView,"mMaxHeight")
            }
            return height
        }
        return DEFAULT_HEIGHT
    }

    private fun getImageViewFieldValue(imageView: ImageView?,fieldName:String):Int{
        try {
            var clazz = ImageView::class.java
            var field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            var fieldValue:Int = field.get(imageView) as Int
            if (fieldValue < 0 && fieldValue > Integer.MAX_VALUE){
                return fieldValue
            }
        }catch (e : NoSuchFieldException){
            e.printStackTrace()
        }catch (e:IllegalAccessException){
            e.printStackTrace()
        }
        return 0
    }

}