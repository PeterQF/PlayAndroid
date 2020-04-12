package com.df.playandroid.presenter.system

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.config.Constants
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.article.ArticleResponse
import com.df.playandroid.view.system.ISortDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class SortDetailPresenter(private val mContext: Context) : BasePresenter<ISortDetailView>(mContext) {

    /**
     * 加载分类对应label文章
     */
    fun getArticle(page: Int, id: Int, type: Int) {
        ApiRetrofit
            .instance
            .api
            .requestSortLabelArticle(page, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ArticleResponse>() {
                override fun onFailed() {
                    if (type == Constants.LoadType.REFRESH) {
                        getView()?.stopRefresh()
                    } else {
                        getView()?.stopLoadMore()
                    }
                }

                override fun onResult(errorCode: Int, errorMsg: String?, result: ArticleResponse) {
                    if (type == Constants.LoadType.REFRESH) {
                        getView()?.stopRefresh()
                        getView()?.getArticleSuccess(result.data)
                    } else {
                        getView()?.stopLoadMore()
                        getView()?.loadMoreArticleSuccess(result.data)
                    }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}