package com.df.playandroid.base.presenter

import android.content.Context
import com.df.playandroid.R
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.Reference
import java.lang.ref.WeakReference

open class BasePresenter<V>(private val context: Context) {

    protected var mLoadingDialog: QMUITipDialog? = null

    init {
        initDialog()
    }

    private fun initDialog() {
        mLoadingDialog = QMUITipDialog.Builder(context)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .create()
    }

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
        if (mLoadingDialog != null) {
            mLoadingDialog = null
        }
    }

    fun getView() = mReference?.get()
}