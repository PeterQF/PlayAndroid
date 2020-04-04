package com.df.playandroid.view.home

import com.df.playandroid.response.home.BannerResponse
import com.df.playandroid.response.home.HomeArticleResponse
import com.df.playandroid.response.home.SearchHotWordResponse

interface IHomeView {
    fun getBannerSuccess(result: List<BannerResponse.BannerData>)
    fun stopRefresh()
    fun getArticleSuccess(result: HomeArticleResponse.ArticleData)
    fun stopLoadMore()
    fun loadMoreArticleSuccess(result: HomeArticleResponse.ArticleData)
    fun getHotWordSuccess(result: List<SearchHotWordResponse.SearchHotWordData>)
}