package com.df.playandroid.home.presenter

import android.content.Context
import com.df.playandroid.base.BaseReceiver
import com.df.playandroid.home.view.IHomeView
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeResponse
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.utils.LogUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(context: Context) : BasePresenter<IHomeView>(context) {

//    fun getArticles() {
//        val subscribe = ApiRetrofit
//            .instance
//            .api
//            .requestArticles()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.isSuccessful) {
//                    if (it.body()!!.errorCode == 0) {
//                        LogUtil.info("get articles success ---> ${it.body()!!.data.size}")
//                    }
//                }
//            }, {
//                LogUtil.info("get articles failed ---> ${it.stackTrace}")
//            })
//    }


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
                override fun onResult(errorCode: Int, errorMsg: String, result: BannerResponse) {
                    result.data.takeIf { it.isNullOrEmpty().not() }?.let { getView()?.getBannerSuccess(it) }
                }
            })
    }

    fun getArticles() {
        ApiRetrofit
            .instance
            .api
            .requestArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseReceiver<HomeResponse>(){
                override fun onResult(errorCode: Int, errorMsg: String, result: HomeResponse) {
                    LogUtil.info("get articles success ---> $errorCode, $errorMsg, ${result.data.size}")
                }
            })
    }
}