package com.df.playandroid.article.presenter

import android.content.Context
import com.df.playandroid.article.view.IArticleView
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlePresenter(context: Context) : BasePresenter<IArticleView>(context){

    fun getArticles() {
        ApiRetrofit
            .instance
            .api
            .requestArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    if (it.body()!!.errorCode == 0) {
                        LogUtil.info("get articles success ---> ${it.body()!!.data.size}")
                    }
                }
            }, {
                LogUtil.info("get articles failed ---> ${it.stackTrace}")
            })
            .apply { mDisposables.add(this) }
    }
}