package com.df.playandroid.base

import com.df.playandroid.base.response.BaseResponse
import com.df.playandroid.http.exception.ExceptionUtils
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.ToastUtil
import io.reactivex.observers.DisposableObserver
import retrofit2.Response

/**
 * 作者：PeterWu
 * 时间：2020/1/5
 * 描述：
 */
abstract class BaseObserver<T: BaseResponse>: DisposableObserver<Response<T>>(){

    override fun onNext(t: Response<T>) {
        if (t.isSuccessful) {
            t.body()?.run {
                if (this.errorCode == 0) {
                    // errorCode为0表示成功，不为0均为错误
                    onResult(this.errorCode, this.errorMsg, this)
                } else {
                    onFailed()
                    handleErrorCode(this.errorMsg)
                }
            }
        } else {
            onFailed()
        }
    }

    private fun handleErrorCode(errorMsg: String) {
        if (errorMsg.isNotEmpty()) {
            ToastUtil.showToast(errorMsg)
        }
    }

    abstract fun onFailed()

    abstract fun onResult(errorCode: Int, errorMsg: String, result: T)

    override fun onComplete() {
        dispose()
        LogUtil.info("on complete dispose")
    }

    override fun onError(e: Throwable) {
        LogUtil.error("get error ----> ${e.message}")
        onFailed()
        ExceptionUtils.handleException(e)
    }
}