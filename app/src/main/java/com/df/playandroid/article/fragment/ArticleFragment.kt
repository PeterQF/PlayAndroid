package com.df.playandroid.article.fragment

import com.df.playandroid.R
import com.df.playandroid.article.presenter.ArticlePresenter
import com.df.playandroid.article.view.IArticleView
import com.df.playandroid.base.fragment.BaseFragment

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：首页文章Fragment
 */
class ArticleFragment : BaseFragment<IArticleView, ArticlePresenter>() {

    override fun getLayoutId() = R.layout.fragment_article

    override fun setupPresenter() = ArticlePresenter(requireContext())

    override fun initView() {
    }

    override fun initData() {
    }
}