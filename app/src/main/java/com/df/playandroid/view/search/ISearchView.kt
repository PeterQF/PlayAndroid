package com.df.playandroid.view.search

import com.df.playandroid.response.article.ArticleData

/**
 * 作者：PeterWu
 * 时间：2020/5/24
 * 描述：
 */
interface ISearchView {
    fun showLoadingView()
    fun hideLoadingView()
    fun stopLoadMore()
    fun getSearchResult(result: ArticleData, isLoadMore: Boolean, keyword: String)
}