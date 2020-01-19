package com.df.playandroid.public_recommend.presenter

import android.content.Context
import com.df.playandroid.base.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.public_recommend.response.PublicResponse
import com.df.playandroid.public_recommend.view.IPublicRecommendView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
class PublicRecommendPresenter(context: Context) : BasePresenter<IPublicRecommendView>(context) {

    /**
     * 获取公众号列表
     */
    fun getPublicItem() {
        ApiRetrofit
            .instance
            .api
            .requestPublicItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<PublicResponse>() {
                override fun onFailed() {
                    getView()?.stopRefresh()
                }

                override fun onResult(errorCode: Int, errorMsg: String, result: PublicResponse) {
                    getView()?.stopRefresh()
                    if (result.data.isNullOrEmpty().not()) {
                        getView()?.getPublicItemSuccess(result.data)
                    }
                }
            })
    }
}