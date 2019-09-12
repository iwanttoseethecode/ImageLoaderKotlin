package com.example.ImageLoaderKt.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by luoling on 2019/9/9.
 * description:
 */

object MD5Utils {
    var digest:MessageDigest? = null;

    init {
        try {
            digest = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun toMD5(key:String):String{
        if (digest == null){
            return key.hashCode().toString()
        }
        //更新字节
        digest?.update(key.toByteArray())
        //获取最终的摘要
        return convert2HexString(digest!!.digest())
    }

    /**
     * 转为16进制字符串
     * @param bytes
     * @return
     */
    fun convert2HexString(bytes:ByteArray):String{
        var sb :StringBuffer = StringBuffer()
        var it = bytes.iterator()
        while (it.hasNext()){
            var bt:Byte= it.nextByte()
            var hex = Integer.toHexString(0xFF and bt.toInt())

            //->8->08
            if (hex.length == 1){
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}


