package com.df.playandroid.content.presenter

import android.content.Context
import com.df.playandroid.base.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.content.response.CollectionResponse
import com.df.playandroid.content.view.ContentView
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.utils.ToastUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/2/1
 * 描述：
 */
class ContentPresenter(context: Context) : BasePresenter<ContentView>(context) {

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
                override fun onFailed() {

                }

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String,
                    result: CollectionResponse
                ) {
                    ToastUtil.showToast("收藏成功")
                }
            })
    }
}