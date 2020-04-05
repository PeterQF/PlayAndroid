package com.df.playandroid.response.home

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

data class BannerResponse(val data: List<BannerData>? = null) : BaseResponse()

data class BannerData(
    val desc: String? = null,
    val id: Int = 0,
    val imagePath: String? = null,
    val isVisible: Int = 0,
    val order: Int = 0,
    val title: String? = null,
    val type: Int = 0,
    val url: String? = null
): Serializable