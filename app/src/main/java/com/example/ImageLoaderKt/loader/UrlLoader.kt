package com.example.ImageLoaderKt.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.ImageLoaderKt.disk.IOUtil
import com.example.ImageLoaderKt.request.BitmapRequest
import com.example.ImageLoaderKt.utils.BitmapDecoder
import com.example.ImageLoaderKt.utils.ImageViewHelper
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by luoling on 2019/9/11.
 * description:
 */
class UrlLoader : AbstractLoader(){
    override fun onLoad(request: BitmapRequest): Bitmap? {
        Log.d("luoling","加载网络图片")
        var connection:HttpURLConnection? = null
        var inputStream:InputStream
        try{
            var url:URL = URL(request.imageUrl)
            connection = url.openConnection() as HttpURLConnection
            //mark与reset支持重复使用流，但是InputStream不支持
            inputStream = BufferedInputStream(connection.inputStream)
            //标记
            inputStream.mark(inputStream.available())
            var ins = inputStream

            var bitmapDecoder = object: BitmapDecoder(){
                override fun decodeBitmapWithOption(options: BitmapFactory.Options): Bitmap? {
                    var bitmap:Bitmap? = BitmapFactory.decodeStream(ins,null,options);
                    if (options.inJustDecodeBounds){
                        //重置
                        //则输入流总会在调用 mark 之后记住所有读取的字节，并且无论何时调用方法 reset ，都会准备再次提供那些相同的字节
                        //但是，如果在调用 reset 之前可以从流中读取多于 readlimit 的字节，则根本不需要该流记住任何数据。
                        try{
                            ins.reset()
                        }catch (e:IOException){
                            e.printStackTrace()
                        }
                    }else{
                        IOUtil.closeQuietly(ins)
                    }
                    return bitmap
                }
            }

            var imageView: ImageView? = request.getImageView()
            return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(imageView),ImageViewHelper.getImageViewHeight(imageView))
        }catch (e:MalformedURLException){
            e.printStackTrace()
        }catch (e:IOException){
            e.printStackTrace()
        }finally {
            if (connection != null){
                connection.disconnect()
            }
        }
        return null;
    }

}