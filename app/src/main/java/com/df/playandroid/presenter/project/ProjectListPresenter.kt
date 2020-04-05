package com.df.playandroid.presenter.project

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.config.Constants
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.article.ArticleResponse
import com.df.playandroid.view.project.IProjectListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：
 */
class ProjectListPresenter(private val context: Context) : BasePresenter<IProjectListView>(context) {

    /**
     * 项目列表
     */
    fun getProjectList(page: Int, id: Int, type: Int) {
        ApiRetrofit
            .instance
            .api
            .requestProjectList(page, id)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (type == Constants.LoadType.LOADING)
                    getView()?.showLoadingView()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ArticleResponse>() {
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
                            getView()?.getProjectSuccess(result.data)
                        }
                        Constants.LoadType.LOAD_MORE -> {
                            getView()?.stopLoadMore()
                            getView()?.loadMoreProjectSuccess(result.data)
                        }
                        Constants.LoadType.LOADING -> {
                            getView()?.hideLoadingView()
                            getView()?.getProjectSuccess(result.data)
                        }
                    }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}