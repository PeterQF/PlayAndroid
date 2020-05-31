package com.df.playandroid.presenter.home

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.base.response.BaseResponse
import com.df.playandroid.config.Constants
import com.df.playandroid.response.home.BannerResponse
import com.df.playandroid.response.home.SearchHotWordResponse
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.http.exception.ExceptionUtils
import com.df.playandroid.response.article.ArticleResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class HomePresenter(context: Context) : BasePresenter<IHomeView>(context) {

    fun getMainInfo() {

        val bannerObserver = ApiRetrofit.instance.api.requestBanner().onErrorResumeNext(Function {
            Observable.never()
        })

        val articleObserver = ApiRetrofit.instance.api.requestHomeArticleList(0).onErrorResumeNext(Function {
            Observable.never()
        })
        val hotWordObserver = ApiRetrofit.instance.api.requestSearchHotWord().onErrorResumeNext(Function {
            Observable.never()
        })

        Observable
            .merge(bannerObserver, articleObserver, hotWordObserver)
            .timeout(15, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getView()?.showLoadingView() }
            .doFinally { getView()?.hideLoadingView() }
            .subscribe (object : Observer<Response<out BaseResponse>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    mDisposables.add(d)
                }

                override fun onNext(t: Response<out BaseResponse>) {
                    if (t.isSuccessful) {
                        val body = t.body()
                        if (t.body()?.errorCode == 0) {
                            if (body is BannerResponse) {
                                body.data?.let { banners -> getView()?.getBannerSuccess(banners) }
                            } else if (body is ArticleResponse) {
                                getView()?.getArticleSuccess(body.data)
                            } else if (body is SearchHotWordResponse) {
                                body.data?.let { hotWords -> getView()?.getHotWordSuccess(hotWords) }
                            }
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    ExceptionUtils.handleException(e)
                }
            })

    }

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
                    result.data.takeIf { it.isNullOrEmpty().not() }?.let { getView()?.getHotWordSuccess(it) }
                }

            })
    }
}