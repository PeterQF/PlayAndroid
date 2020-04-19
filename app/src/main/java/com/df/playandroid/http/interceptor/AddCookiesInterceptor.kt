package com.df.playandroid.http.interceptor

import com.df.playandroid.config.SpConstants
import com.df.playandroid.utils.SPUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 作者：PeterWu
 * 时间：2019/12/29
 * 描述：添加本地Cookie进行网络访问的拦截器
 */
class AddCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = SPUtil.getHashSet(SpConstants.KEY_COOKIE) as HashSet<String>?
        if (cookies != null) {
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }

//    override fun intercept(chain: Interceptor.Chain): Response {
//        val builder = chain.request().newBuilder()
//        //  从Sharepreference中读取Cookie并添加到头部
//        val cookies = SPUtil.getObtain().getStringSet(SpConstants.KEY_COOKIE, null)
//        cookies?.forEach {
//            builder.addHeader("Cookie", it)
//        }
//        return chain.proceed(builder.build())
//    }
}