package com.df.playandroid.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.df.playandroid.R
import com.df.playandroid.application.AppManager
import com.df.playandroid.base.event.BaseEvent
import com.df.playandroid.base.event.EventManager
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    override fun onStart() {
        super.onStart()
        if (hadEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    open fun initPresenter() {}

    open fun hadEventBus() = false

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: BaseEvent) {}

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

    override fun onStop() {
        super.onStop()
        if (hadEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }
}