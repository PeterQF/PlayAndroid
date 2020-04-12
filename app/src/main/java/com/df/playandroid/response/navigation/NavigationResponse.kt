package com.df.playandroid.response.navigation

import com.df.playandroid.base.response.BaseResponse
import com.df.playandroid.response.article.ArticleInfo
import java.io.Serializable

data class NavigationResponse(val data: List<NavigationData>? = null) : BaseResponse()

data class NavigationData(
    val articles: List<ArticleInfo>? = null,
    val cid: Int = 0,
    val name: String? = null
): Serializable