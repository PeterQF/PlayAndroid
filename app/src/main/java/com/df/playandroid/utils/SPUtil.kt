package com.df.playandroid.utils

import android.content.Context
import android.content.SharedPreferences
import com.df.playandroid.application.MyApplication
import java.lang.IllegalArgumentException

/**
 * 作者：PeterWu
 * 时间：2019/12/29
 * 描述：
 */
object SPUtil {
    private val name = "App_Sp"
    private val prefs: SharedPreferences by lazy {
        MyApplication.instance.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    fun getObtain(): SharedPreferences {
        return prefs
    }

    /**
     * 获取存放数据
     */
    fun get(key: String, default: Any): Any = with(prefs) {
        return when(default) {
            is Int -> SPUtil.getInt(key, default)
            is String -> SPUtil.getString(key, default)
            is Long -> SPUtil.getLong(key, default)
            is Float -> SPUtil.getFloat(key, default)
            is Boolean -> SPUtil.getBoolean(key, default)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }
    }

    fun getString(key: String, default: String = ""): String {
        return get(key, default) as String
    }

    fun getInt(key: String, default: Int = 0): Int {
        return get(key, default) as Int
    }

    fun getLong(key: String, default: Long = 0): Long {
        return get(key, default) as Long
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        return get(key, default) as Boolean
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return get(key, default) as Float
    }

    /**
     * 存放数据
     */
    fun put(key: String, value: Any) = with(prefs.edit()) {
        when(value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("SharedPreferences 类型错误")
        }.apply()
    }

    /**
     * 清除
     */
    fun clear() {
        prefs.edit().clear().apply()
    }

    /**
     * 删除某Key的值
     */
    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
}