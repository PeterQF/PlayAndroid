package com.df.playandroid.presenter.system

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.navigation.NavigationResponse
import com.df.playandroid.view.system.INavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class NavigationPresenter(private val mContext: Context) : BasePresenter<INavigationView>(mContext) {

    /**
     * 分类标签
     */
    fun getNavigationLabel() {
        ApiRetrofit
            .instance
            .api
            .requestNavigationLabel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { getView()?.showLoadingView() }
            .subscribe(object : BaseObserver<NavigationResponse>() {
                override fun onFailed() {
                    getView()?.hideLoadingView()
                }

                override fun onResult(errorCode: Int, errorMsg: String?, result: NavigationResponse) {
                    getView()?.hideLoadingView()
                    result.data?.let { getView()?.getLabelSuccess(it) }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}