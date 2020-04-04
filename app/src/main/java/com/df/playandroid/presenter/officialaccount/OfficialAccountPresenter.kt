package com.df.playandroid.presenter.officialaccount

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import com.df.playandroid.view.officialaccount.IOfficialAccountView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
class OfficialAccountPresenter(context: Context) : BasePresenter<IOfficialAccountView>(context) {

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
            .subscribe(object : BaseObserver<OfficialAccountResponse>() {
                override fun onFailed() {
                    getView()?.stopRefresh()
                }

                override fun onResult(errorCode: Int, errorMsg: String, result: OfficialAccountResponse) {
                    getView()?.stopRefresh()
                    if (result.data.isNullOrEmpty().not()) {
                        getView()?.getPublicItemSuccess(result.data)
                    }
                }
            })
    }
}