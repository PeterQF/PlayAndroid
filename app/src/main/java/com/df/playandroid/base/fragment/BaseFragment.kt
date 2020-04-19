package com.df.playandroid.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.df.playandroid.base.event.BaseEvent
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.presenter.BasePresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
abstract class BaseFragment<V, P : BasePresenter<V>> : Fragment() {

    private var isDataLoaded = false // 数据是否已请求
    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = setupPresenter()
        @Suppress("UNCHECKED_CAST")
        mPresenter?.attachView(this as V)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEvent()
        initView()
    }

    private fun initEvent() {
        if (hadEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        if (hadEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isDataLoaded.not()) {
            isDataLoaded = true
            lazyLoad()
        }
    }

    private fun lazyLoad() {
        initData()
    }

    inline fun <reified T> launch() {
        startActivity(Intent(requireContext(), T::class.java))
    }

    abstract fun getLayoutId(): Int
    abstract fun setupPresenter(): P?
    abstract fun initView()
    abstract fun initData()
    open fun hadEventBus() = false
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: BaseEvent) {}
}