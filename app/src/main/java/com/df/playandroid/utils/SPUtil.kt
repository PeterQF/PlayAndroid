package com.df.playandroid.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Base64
import com.df.playandroid.application.MyApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import java.io.*
import java.lang.IllegalArgumentException

/**
 * 作者：PeterWu
 * 时间：2019/12/29
 * 描述：
 */
object SPUtil {

    private const val name = "App_Sp"
    private val prefs: SharedPreferences by lazy {
        MyApplication.instance.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return prefs.edit()
    }

    private fun getObtain(): SharedPreferences {
        return prefs
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return getObtain().getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        getEditor().putBoolean(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return getObtain().getString(key, defaultValue)
    }

    fun setString(key: String, value: String) {
        getEditor().putString(key, value).apply()
    }

    fun setInt(key: String, value: Int) {
        getEditor().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return getObtain().getInt(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        getEditor().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return getObtain().getLong(key, defaultValue)
    }

    fun setHashSet(key: String,value:HashSet<String>){
        getEditor().putStringSet(key, value).apply()
    }

    fun getHashSet(key: String): MutableSet<String>? {
        return getObtain().getStringSet(key, null)
    }

    fun removeKey(key: String): Boolean {
        return getEditor().remove(key).commit()
    }

    fun setObject(key: String, value: Any?) {
        if (value == null) {
            return
        }
        if (value !is Serializable) {
            return
        }
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val temp = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            getEditor().putString(key, temp).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (oos != null&&baos != null) {
                IOUtils.closeIO(oos, baos)
            }
        }

    }

    fun getObject(key: String): Any? {
        var `object`: Any? = null
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        val temp = getObtain().getString(key, "")
        if (!TextUtils.isEmpty(temp)) {
            try {
                bais = ByteArrayInputStream(Base64.decode(temp!!.toByteArray(), Base64.DEFAULT))
                ois = ObjectInputStream(bais)
                `object` = ois.readObject()
            } catch (ignored: Exception) {

            } finally {
                if (ois != null&&bais != null) {
                    IOUtils.closeIO(ois, bais)
                }
            }
        }
        return `object`
    }

    fun setCookies(key: String, value: List<Cookie>?) {
        if (value == null) return
        val gson = Gson()
        val gsonValue = gson.toJson(value)
        getEditor().putString(key, gsonValue).apply()
    }

    fun getCookies(key: String): List<Cookie>? {
        val listJson = getObtain().getString(key, "")
        return if (listJson != "") {
            val gson = Gson()
            val list: List<Cookie> = gson.fromJson(listJson, object : TypeToken<List<Cookie>>() {}.type)
            list
        } else {
            emptyList()
        }
    }
}