package com.df.playandroid.base.activity

import android.os.Bundle
import com.df.playandroid.base.presenter.BasePresenter

abstract class BaseMvpActivity<V, P: BasePresenter<V>> : BaseActivity() {

    private var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setupPresenter()
        @Suppress("UNCHECKED_CAST")
        mPresenter?.attachView(this as V)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    abstract fun getLayoutId(): Int
    abstract fun setupPresenter(): P?
    abstract fun initView()
    abstract fun initData()

}