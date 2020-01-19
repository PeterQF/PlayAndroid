package com.df.playandroid.http.exception

/**
 * 作者：PeterWu
 * 时间：2020/1/19
 * 描述：自定义错误码
 */
object ErrorCode {

    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    const val NETWORK_ERROR = 1002

    /**
     * 协议出错
     */
    const val HTTP_ERROR = 1003

    /**
     * 服务器出错
     */
    const val SERVER_ERROR = 1004
}