package com.df.playandroid.http

import com.df.playandroid.BuildConfig
import com.df.playandroid.application.MyApplication
import com.df.playandroid.config.SpConstants
import com.df.playandroid.http.interceptor.AddCookiesInterceptor
import com.df.playandroid.http.interceptor.ReceivedCookiesInterceptor
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class BaseRetrofit {

    // 日志拦截器
    private val httpLog: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        }
    }


    // TODO 配置缓存


    /**
     * 配置Cookie
     */
//    private val cookieJar = object : CookieJar {
//
//        override fun loadForRequest(url: HttpUrl): List<Cookie> {
//            val cookies = SPUtil.getCookies(SpConstants.KEY_COOKIE)
//            LogUtil.info("okhttp cookiejar load ----> $cookies")
//            return cookies ?: emptyList()
//        }
//
//        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
//            if (cookies.isNullOrEmpty().not()) {
//                LogUtil.info("okhttp cookiejar save ----> $cookies")
//                SPUtil.setCookies(SpConstants.KEY_COOKIE, cookies)
//            }
//        }
//
//    }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.instance.applicationContext))
    }

    val client: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(httpLog)
//            .addInterceptor(ReceivedCookiesInterceptor())
//            .addInterceptor(AddCookiesInterceptor())
            .cookieJar(cookieJar)
            .retryOnConnectionFailure(true)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
}