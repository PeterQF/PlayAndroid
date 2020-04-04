package com.df.playandroid.presenter.officialaccount

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import com.df.playandroid.view.officialaccount.IOfficialAccountArticleView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：
 */
class OfficialAccountArticlePresenter(private val context: Context): BasePresenter<IOfficialAccountArticleView>(context) {
}