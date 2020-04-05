package com.df.playandroid.response.home

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

data class SearchHotWordResponse(val data: List<SearchHotWordData>? = null) : BaseResponse()

data class SearchHotWordData(
    val id: Int = 0,
    val link: String? = null,
    val name: String? = null,
    val order: Int = 0,
    val visible: Int = 0
): Serializable