package com.df.playandroid.view.project

import com.df.playandroid.response.article.ArticleData

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：
 */
interface IProjectListView {
    fun showLoadingView()
    fun hideLoadingView()
    fun getProjectSuccess(result: ArticleData)
    fun stopRefresh()
    fun stopLoadMore()
    fun loadMoreProjectSuccess(result: ArticleData)
}