package com.df.playandroid.base.activity

import com.df.playandroid.base.presenter.BasePresenter

abstract class BaseMvpActivity<V, P: BasePresenter<V>> : BaseActivity() {

    private var mPresenter: P? = null

    override fun initPresenter() {
        mPresenter = setupPresenter()
        @Suppress("UNCHECKED_CAST")
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    abstract fun setupPresenter(): P?
}