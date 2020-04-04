package com.df.playandroid.response.home

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

class SearchHotWordResponse(val data: List<SearchHotWordData>) : BaseResponse() {
    data class SearchHotWordData(
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
    ): Serializable
}