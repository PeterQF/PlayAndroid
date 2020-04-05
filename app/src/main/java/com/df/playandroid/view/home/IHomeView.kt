package com.df.playandroid.view.home

import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.home.*

interface IHomeView {
    fun showLoadingView()
    fun hideLoadingView()
    fun getBannerSuccess(result: List<BannerData>)
    fun stopRefresh()
    fun getArticleSuccess(result: ArticleData)
    fun stopLoadMore()
    fun loadMoreArticleSuccess(result: ArticleData)
    fun getHotWordSuccess(result: List<SearchHotWordData>)
}