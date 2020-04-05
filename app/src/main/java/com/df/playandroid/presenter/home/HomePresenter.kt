package com.df.playandroid.presenter.home

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.config.Constants
import com.df.playandroid.response.home.BannerResponse
import com.df.playandroid.response.home.SearchHotWordResponse
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.article.ArticleResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(context: Context) : BasePresenter<IHomeView>(context) {

    /**
     * 获取轮播图
     */
    fun getBanner() {
        ApiRetrofit
            .instance
            .api
            .requestBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<BannerResponse>() {
                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }

                override fun onFailed() {}

                override fun onResult(errorCode: Int, errorMsg: String?, result: BannerResponse) {
                    result.data.takeIf { it.isNullOrEmpty().not() }
                        ?.let { getView()?.getBannerSuccess(it) }
                }
            })
    }

    /**
     * 获取首页文章
     */
    fun getArticles(page: Int, type: Int) {
        ApiRetrofit
            .instance
            .api
            .requestHomeArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (type == Constants.LoadType.LOADING)
                    getView()?.showLoadingView()
            }
            .subscribe(object : BaseObserver<ArticleResponse>() {
                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }

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
                    result: ArticleResponse
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
            })
    }

    /**
     * 获取搜索热词
     */
    fun getHotWord() {
        ApiRetrofit
            .instance
            .api
            .requestSearchHotWord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<SearchHotWordResponse>() {
                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }

                override fun onFailed() {}

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String?,
                    result: SearchHotWordResponse
                ) {
                    result.data.takeIf { it.isNullOrEmpty().not() }
                        ?.let { getView()?.getHotWordSuccess(it) }
                }

            })
    }
}