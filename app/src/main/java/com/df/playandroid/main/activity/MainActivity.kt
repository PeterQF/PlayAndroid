package com.df.playandroid.main.activity

import com.df.playandroid.R
import com.df.playandroid.article.presenter.ArticlePresenter
import com.df.playandroid.article.view.IArticleView
import com.df.playandroid.base.activity.BaseMvpActivity
import com.qmuiteam.qmui.widget.popup.QMUIBasePopup

class MainActivity : BaseMvpActivity<IArticleView, ArticlePresenter>() {


    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = ArticlePresenter(this)

    override fun initView() {
    }

    override fun initData() {
        mPresenter?.getArticles()
    }
}
