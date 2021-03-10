package com.df.playandroid.ui.splash

import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseActivity
import com.df.playandroid.ui.main.activity.MainActivity

class SplashActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_splash

    override fun initView() {
        launch<MainActivity>()
        finish()
    }

    override fun initData() {}
}