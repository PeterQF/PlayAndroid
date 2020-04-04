package com.df.playandroid.response.home

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

data class BannerResponse(
    val data: List<BannerData>
) : BaseResponse(){
    data class BannerData(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
    ): Serializable
}