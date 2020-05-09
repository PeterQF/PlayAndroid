package com.df.playandroid.presenter.content

import android.content.Context
import com.df.playandroid.R
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.base.response.BaseResponse
import com.df.playandroid.response.content.CollectionResponse
import com.df.playandroid.view.content.IContentView
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/2/1
 * 描述：
 */
class ContentPresenter(private val context: Context) : BasePresenter<IContentView>(context) {

    /**
     * 收藏站内文章
     */
    fun collectStationArticle(id: Int) {
        ApiRetrofit
            .instance
            .api
            .requestCollectStationArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: BaseObserver<CollectionResponse>() {
                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }

                override fun onFailed() {}

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String?,
                    result: CollectionResponse
                ) {
                    getView()?.collectSuccess()
                }
            })
    }

    /**
     * 取消收藏
     */
    fun unCollectArticle(id: Int) {
        ApiRetrofit
            .instance
            .api
            .requestUnCollectArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: BaseObserver<BaseResponse>() {
                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }

                override fun onFailed() {}

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String?,
                    result: BaseResponse
                ) {
                    getView()?.unCollectSuccess()
                }
            })
    }
}