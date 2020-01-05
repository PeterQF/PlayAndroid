package com.df.playandroid.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import com.df.playandroid.application.MyApplication

/**
 * 作者：PeterWu
 * 时间：2020/1/5
 * 描述：屏幕工具类
 */
object DeviceUtil {

    /**
     * 获取屏幕的宽度
     */
    fun getScreenWidth(): Int {
        val windowManager =
            MyApplication.instance.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager == null) return -1
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(): Int {
        val windowManager =
            MyApplication.instance.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager == null) return -1
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    /**
     * dp转px,返回Int
     */
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px转dp, 返回Int
     */
    fun px2dp(pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp转px，返回Int型
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.density
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px转sp，返回Int型
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.density
        return (pxValue / fontScale + 0.5f).toInt()
    }


    /**
     * dp转px,返回Float
     */
    fun dp2pxF(dpValue: Float): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return dpValue * scale + 0.5f
    }

    /**
     * px转dp, 返回Float
     */
    fun px2dpF(pxValue: Float): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return pxValue / scale + 0.5f
    }

    /**
     * sp转px，返回Float型
     */
    fun sp2pxF(spValue: Float): Float {
        val fontScale = Resources.getSystem().displayMetrics.density
        return spValue * fontScale + 0.5f
    }

    /**
     * px转sp，返回Float型
     */
    fun px2spF(pxValue: Float): Float {
        val fontScale = Resources.getSystem().displayMetrics.density
        return pxValue / fontScale + 0.5f
    }

}