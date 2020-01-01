package com.df.playandroid.http.interceptor

import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //拦截的cookie保存在originalResponse中
        val originalResponse = chain.proceed(chain.request())
        // 这里获取请求返回的cookie
        if (originalResponse.header("Set-Cookie") != null) {
            if (originalResponse.header("Set-Cookie")!!.isNotEmpty()) {
                val cookies: HashSet<String> = HashSet()
                originalResponse.header("Set-Cookie")!!.forEach {
                    LogUtil.info("receiver cookie ---> $it")
                    cookies.add(it.toString())
                }
                SPUtil.getEditor().putStringSet("cookie", cookies).apply()
            }
        }
        return originalResponse
    }
}