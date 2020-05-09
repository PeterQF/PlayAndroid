package com.df.playandroid.presenter.register

import android.content.Context
import com.df.playandroid.R
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.user.UserResponse
import com.df.playandroid.view.register.IRegisterView
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：PeterWu
 * 时间：2020/5/2
 * 描述：
 */
class RegisterPresenter(private val mContext: Context) : BasePresenter<IRegisterView>(mContext) {

    /**
     * 注册
     */
    fun register(username: String, pwd: String, rePwd: String) {
        ApiRetrofit
            .instance
            .api
            .requestRegister(username, pwd, rePwd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mLoadingDialog?.show()
            }
            .subscribe(object : BaseObserver<UserResponse>() {
                override fun onFailed() {
                    mLoadingDialog?.dismiss()
                }

                override fun onResult(errorCode: Int, errorMsg: String?, result: UserResponse) {
                    mLoadingDialog?.dismiss()
                    result.data?.let { getView()?.registerSuccess(it, username, pwd, rePwd) }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}