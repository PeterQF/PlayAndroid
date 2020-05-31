package com.df.playandroid.presenter.search

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.article.ArticleResponse
import com.df.playandroid.view.search.ISearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/5/24
 * 描述：
 */
class SearchPresenter(private var mContext: Context) : BasePresenter<ISearchView>(mContext) {

    /**
     * 搜索
     */
    fun goSearch(pageNum: Int, keyword: String, isLoadMore: Boolean = false) {
        ApiRetrofit
            .instance
            .api
            .requestSearch(pageNum, keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (isLoadMore.not()) getView()?.showLoadingView()
            }
            .doFinally {
                if (isLoadMore) {
                    getView()?.stopLoadMore()
                } else {
                    getView()?.hideLoadingView()
                }
            }
            .subscribe(object : BaseObserver<ArticleResponse>() {
                override fun onFailed() {}

                override fun onResult(errorCode: Int, errorMsg: String?, result: ArticleResponse) {
                    getView()?.getSearchResult(result.data, isLoadMore, keyword)
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}