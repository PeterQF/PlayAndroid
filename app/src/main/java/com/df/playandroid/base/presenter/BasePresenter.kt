package com.df.playandroid.base.presenter

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.Reference
import java.lang.ref.WeakReference

open class BasePresenter<V>(private val context: Context) {

    // view的引用, 使用弱引用防止内存泄漏
    private var mReference: Reference<V>? = null
    // 收集观察者，一次性清除
    protected val mDisposables: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * 连接上view
     */
    fun attachView(view: V) {
        mReference = WeakReference<V>(view)
    }

    /**
     * 解除view
     */
    fun detachView() {
        mReference?.let {
            it.clear()
            mReference = null
        }
        mDisposables.dispose()
        mDisposables.clear()
    }

    fun getView() = mReference?.get()
}