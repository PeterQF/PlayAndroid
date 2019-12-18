package com.df.playandroid.utils

import android.util.Log
import com.df.playandroid.BuildConfig
import com.df.playandroid.application.MyApplication

object LogUtil {
    private val DEBUG_MODE by lazy {
        BuildConfig.DEBUG
    }

    fun error(msg: String) {
        if (DEBUG_MODE){
            Log.e(MyApplication.instance.packageName, msg)
        }
    }

    fun info(msg: String) {
        if (DEBUG_MODE){
            Log.i(MyApplication.instance.packageName, msg)
        }
    }
    
    fun debug(msg: String) {
        if (DEBUG_MODE) {
            Log.d(MyApplication.instance.packageName, msg)
        }
    }

    fun verbose(msg: String) {
        if (DEBUG_MODE){
            Log.v(MyApplication.instance.packageName, msg)
        }
    }
}