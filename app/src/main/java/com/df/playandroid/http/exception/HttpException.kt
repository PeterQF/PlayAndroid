package com.df.playandroid.http.exception

/**
 * 作者：PeterWu
 * 时间：2020/1/19
 * 描述：网络异常
 */

class HttpException(throwable: Throwable, val code: Int) : Exception(throwable) {
    var displayMessage: String? = null
}