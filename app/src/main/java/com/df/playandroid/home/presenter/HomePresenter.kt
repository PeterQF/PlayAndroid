package com.df.playandroid.home.presenter

import android.content.Context
import com.df.playandroid.base.BaseReceiver
import com.df.playandroid.home.view.IHomeView
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.config.Constants
import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeArticleResponse
import com.df.playandroid.home.response.SearchHotWordResponse
import com.df.playandroid.http.ApiRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
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
            .subscribe(object : BaseReceiver<BannerResponse>() {
                override fun onFailed() {}

                override fun onResult(errorCode: Int, errorMsg: String, result: BannerResponse) {
                    result.data.takeIf { it.isNullOrEmpty().not() }?.let { getView()?.getBannerSuccess(it) }
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
            .subscribe(object : BaseReceiver<HomeArticleResponse>(){
                override fun onFailed() {
                    when(type) {
                        Constants.LoadType.REFRESH -> getView()?.stopRefresh()
                        Constants.LoadType.LOAD_MORE -> getView()?.stopLoadMore()
                    }
                }

                override fun onResult(errorCode: Int, errorMsg: String, result: HomeArticleResponse) {
                    when(type) {
                        Constants.LoadType.REFRESH -> {
                            getView()?.stopRefresh()
                            getView()?.getArticleSuccess(result.data)
                        }
                        Constants.LoadType.LOAD_MORE -> {
                            getView()?.stopLoadMore()
                            getView()?.loadMoreArticleSuccess(result.data)
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
            .subscribe(object : BaseReceiver<SearchHotWordResponse>() {
                override fun onFailed() {}

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String,
                    result: SearchHotWordResponse
                ) {
                    result.data.takeIf { it.isNullOrEmpty().not() }?.let { getView()?.getHotWordSuccess(it) }
                }

            })
    }
}