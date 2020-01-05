package com.df.playandroid.http

import com.df.playandroid.BuildConfig
import com.df.playandroid.http.interceptor.AddCookiesInterceptor
import com.df.playandroid.http.interceptor.ReceivedCookiesInterceptor
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

    val client: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .addInterceptor(httpLog)
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .retryOnConnectionFailure(true)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }
}