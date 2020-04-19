package com.df.playandroid.http.interceptor

import com.df.playandroid.config.SpConstants
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ReceivedCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.headers("set-cookie").isNotEmpty()) {
            val cookies = HashSet(response.headers("set-cookie"))
            LogUtil.info("get cookie ---> $cookies")
            SPUtil.setHashSet(SpConstants.KEY_COOKIE, cookies)
        }
        return response
    }

//    override fun intercept(chain: Interceptor.Chain): Response {
//        //拦截的cookie保存在originalResponse中
//        val originalResponse = chain.proceed(chain.request())
//        // 这里获取请求返回的cookie
//        if (originalResponse.header("Set-Cookie") != null) {
//            if (originalResponse.header("Set-Cookie")!!.isNotEmpty()) {
//                val cookies: HashSet<String> = HashSet()
//                LogUtil.info("receiver cookie ---> ${originalResponse.header("Set-Cookie").toString()}")
//                originalResponse.header("Set-Cookie")!!.forEach {
//                    cookies.add(it.toString())
//                }
//                SPUtil.getEditor().putStringSet(SpConstants.KEY_COOKIE, cookies).apply()
//            }
//        }
//        return originalResponse
//    }
}