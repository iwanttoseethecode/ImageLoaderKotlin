package com.example.ImageLoaderKt.loader

/**
 * Created by luoling on 2019/9/11.
 * description:
 */

class LoadManager {
    companion object{
        private var map:HashMap<String,Loader> = hashMapOf()
        @Volatile
        private var mLoadManager:LoadManager? = null
        fun getInstance():LoadManager{
            if (mLoadManager == null){
                @Synchronized
                if (mLoadManager == null){
                    mLoadManager = LoadManager()
                }
            }
            return mLoadManager!!
        }
    }

    constructor(){}

    private var nullLoader:NullLoader = NullLoader()

    init {
        map.put("file",LocalLoader())
        map.put("https",UrlLoader())
        map.put("http",UrlLoader())
    }

    fun getLoader(scheme:String?):Loader?{
        if (scheme != null && map.containsKey(scheme)){
            return map.get(scheme);
        }
        return nullLoader
    }

}