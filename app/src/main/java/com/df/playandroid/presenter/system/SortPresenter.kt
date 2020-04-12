package com.df.playandroid.presenter.system

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.category.CategoryResponse
import com.df.playandroid.view.system.ISortView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class SortPresenter(private val mContext: Context) : BasePresenter<ISortView>(mContext) {

    /**
     * 分类标签
     */
    fun getSortLabel() {
        ApiRetrofit
            .instance
            .api
            .requestSortLabel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getView()?.showLoadingView() }
            .subscribe(object : BaseObserver<CategoryResponse>() {
                override fun onFailed() {
                    getView()?.hideLoadingView()
                }

                override fun onResult(errorCode: Int, errorMsg: String?, result: CategoryResponse) {
                    getView()?.hideLoadingView()
                    result.data?.let { getView()?.getLabelSuccess(it) }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}