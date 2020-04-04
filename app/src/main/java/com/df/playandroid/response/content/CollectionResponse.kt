package com.df.playandroid.response.content

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

/**
 * 作者：PeterWu
 * 时间：2020/2/1
 * 描述：
 */
class CollectionResponse(
    errorCode: Int,
    errorMsg: String,
    var isCollected: Boolean = false
) : BaseResponse(errorCode, errorMsg), Serializable