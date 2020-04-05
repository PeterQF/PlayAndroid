package com.df.playandroid.presenter.officialaccount

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.config.Constants
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.officialaccount.OfficialAccountArticleResponse
import com.df.playandroid.view.officialaccount.IOfficialAccountArticleView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：
 */
class OfficialAccountArticlePresenter(private val context: Context): BasePresenter<IOfficialAccountArticleView>(context) {

    /**
     * 公众号文章
     */
    fun getOfficialAccountArticle(id: Int, page: Int, type: Int) {
        ApiRetrofit
            .instance
            .api
            .requestOfficialAccountArticleList(id, page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (type == Constants.LoadType.LOADING)
                getView()?.showLoadingView()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<OfficialAccountArticleResponse>() {
                override fun onFailed() {
                    when (type) {
                        Constants.LoadType.REFRESH -> getView()?.stopRefresh()
                        Constants.LoadType.LOAD_MORE -> getView()?.stopLoadMore()
                        Constants.LoadType.LOADING -> getView()?.hideLoadingView()
                    }
                }

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String?,
                    result: OfficialAccountArticleResponse
                ) {
                    when (type) {
                        Constants.LoadType.REFRESH -> {
                            getView()?.stopRefresh()
                            getView()?.getArticleSuccess(result.data)
                        }
                        Constants.LoadType.LOAD_MORE -> {
                            getView()?.stopLoadMore()
                            getView()?.loadMoreArticleSuccess(result.data)
                        }
                        Constants.LoadType.LOADING -> {
                            getView()?.hideLoadingView()
                            getView()?.getArticleSuccess(result.data)
                        }
                    }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}