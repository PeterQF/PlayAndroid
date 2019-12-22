package com.df.playandroid.base.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.df.playandroid.base.presenter.BasePresenter

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    
}