package com.df.playandroid.presenter.login

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.user.UserResponse
import com.df.playandroid.view.login.ILoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
class LoginPresenter(private val mContext: Context) : BasePresenter<ILoginView>(mContext) {

    fun login(username: String, password: String) {
        ApiRetrofit
            .instance
            .api
            .requestLogin(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<UserResponse>() {
                override fun onFailed() {

                }

                override fun onResult(errorCode: Int, errorMsg: String?, result: UserResponse) {
                    result.data?.let { getView()?.loginSuccess(it) }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}