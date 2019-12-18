package com.df.playandroid.http

import com.df.playandroid.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

class BaseRetrofit {

    // 日志拦截器
    private val httpLog: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        }
    }
}