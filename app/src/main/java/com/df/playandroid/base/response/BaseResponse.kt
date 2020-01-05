package com.df.playandroid.base.response

import okhttp3.Response

/**
 * 作者：PeterWu
 * 时间：2020/1/5
 * 描述：BaseResponse
 */

open class BaseResponse(val errorCode: Int, val errorMsg: String)