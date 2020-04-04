package com.df.playandroid.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.df.playandroid.R
import com.df.playandroid.application.AppManager
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        initPresenter()
        setContentView(getLayoutId())
        initView()
        initData()
        init()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    open fun initPresenter() {}

    private fun init() {
        initStatusBar()
    }

    open fun initStatusBar() {
        ImmersionBar
            .with(this)
            .statusBarColor(R.color.mainColor)
            .keyboardEnable(true)
            .navigationBarColor(R.color.white)
            .init()
    }

    inline fun<reified T> launch() {
        startActivity(Intent(this, T::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }
}