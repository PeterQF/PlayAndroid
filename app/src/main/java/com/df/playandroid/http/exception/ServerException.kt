package com.df.playandroid.http.exception

/**
 * 作者：PeterWu
 * 时间：2020/1/19
 * 描述：服务异常
 */
class ServerException(var code: Int, var errorMessage: String) : RuntimeException()