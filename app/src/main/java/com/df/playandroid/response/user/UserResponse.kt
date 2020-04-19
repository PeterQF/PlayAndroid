package com.df.playandroid.response.user

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
data class UserResponse(val data: UserData? = null) : BaseResponse()

data class UserData(
    val username: String? = null
): Serializable