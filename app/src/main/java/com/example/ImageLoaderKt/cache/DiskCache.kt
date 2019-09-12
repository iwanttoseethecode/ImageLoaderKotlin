package com.example.ImageLoaderKt.cache

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.example.ImageLoaderKt.disk.DiskLruCache
import com.example.ImageLoaderKt.disk.IOUtil
import com.example.ImageLoaderKt.request.BitmapRequest
import java.io.*
import java.lang.Exception

/**
 * Created by luoling on 2019/9/9.
 * description:
 */
class DiskCache private constructor(context: Context) : BitmapCache {

    private var mCacheDir = "image_cache";

    private lateinit var mDiskLruCache:DiskLruCache

    companion object{
        @Volatile
        private var mDiskCache:DiskCache? = null

        private val MB:Long = 1024 * 1024

        fun getInstance(context: Context):DiskCache{
            if (mDiskCache == null){
                @Synchronized
                if (mDiskCache == null){
                    mDiskCache = DiskCache(context);
                }
            }
            return mDiskCache!!
        }
    }

    init {
        initDiskCache(context)
    }

    /**
     * 初始化
     * @param context
     */
    fun initDiskCache(context:Context){
        var directory:File = getDiskCacheDir(mCacheDir,context)
        if (!directory.exists()){
            directory.mkdirs()
        }
        //初始化
        //1 每次缓存一个图片
        //50 MB 最大值
        try{
            mDiskLruCache = DiskLruCache.open(directory,getAppVersion(context),1,50 * MB)
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    fun getAppVersion(context: Context):Int{
        try {
            var pm:PackageManager = context.packageManager
            return pm.getPackageInfo(context.packageName,0).versionCode;
        }catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return 1
    }

    fun getDiskCacheDir(mCacheDir:String,context: Context):File{
        //相对路径
        var cachePath:String
        //是否有SDCard且具有读写SDCard的权限
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //外部存储
            cachePath = context.externalCacheDir.path
        }else{
            //内部存储
            cachePath = context.cacheDir.path
        }
        return File(cachePath + File.separatorChar + mCacheDir)
    }

    override fun put(request: BitmapRequest, bitmap: Bitmap) {
        var editor:DiskLruCache.Editor
        var os:OutputStream? = null;

        try{
            //开始编辑
            editor = mDiskLruCache.edit(request.imageMD5Url)
            os = editor.newOutputStream(0);
            //成功，或者失败
            if (persistBitmap2Disk(bitmap,os)){
                editor.commit()
            }else{
                editor.abort()
            }
            mDiskLruCache.flush()
        }catch(e:IOException){
            e.printStackTrace()
        }finally {
            IOUtil.closeQuietly(os)
        }
    }

    /**
     * 持久化Bitmap对象到Disk
     * @param bitmap
     * @param os
     * @return
     */
    fun persistBitmap2Disk(bitmap:Bitmap,os:OutputStream):Boolean{
        var bos:BufferedOutputStream? = null;
        try {
            bos = BufferedOutputStream(os)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos)
            //清空缓存
            bos.flush()
        }catch (e:Exception){
            e.printStackTrace()
            return false;
        }finally {
            IOUtil.closeQuietly(bos)
        }
        return true;
    }

    override fun get(request: BitmapRequest): Bitmap? {
        var ins:InputStream? = null;
        try {
            var snapshot: DiskLruCache.Snapshot? = mDiskLruCache.get(request.imageMD5Url)
            ins = snapshot?.getInputStream(0)
            return BitmapFactory.decodeStream(ins)
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            IOUtil.closeQuietly(ins)
        }
        return null
    }

    override fun remove(request: BitmapRequest) {
        try {
            mDiskLruCache.remove(request.imageMD5Url)
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}