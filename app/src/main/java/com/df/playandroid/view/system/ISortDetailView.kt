package com.df.playandroid.view.system

import com.df.playandroid.response.article.ArticleData

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
interface ISortDetailView {
    fun stopRefresh()
    fun stopLoadMore()
    fun getArticleSuccess(result: ArticleData)
    fun loadMoreArticleSuccess(result: ArticleData)
}