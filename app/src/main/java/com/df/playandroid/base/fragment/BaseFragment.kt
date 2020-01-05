package com.df.playandroid.base.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.df.playandroid.base.presenter.BasePresenter


/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
abstract class BaseFragment<V, P : BasePresenter<V>> : Fragment() {

    private var isViewCreated = false // 界面是否已创建完成
    private var isVisibleToUser = false // fragment是否对用户可见
    private var isDataLoaded = false // 数据是否已请求
    protected var mPresenter: P? = null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        lazyLoad()
    }

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
        userVisibleHint = !isWithViewPager()
        isViewCreated = true
        initView()
        lazyLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    private fun lazyLoad() {
        if (isViewCreated && isVisibleToUser && !isDataLoaded) {
            initData()
            isDataLoaded = true
        }
    }

    inline fun <reified T> launch() {
        startActivity(Intent(requireContext(), T::class.java))
    }

    abstract fun getLayoutId(): Int
    abstract fun setupPresenter(): P?
    abstract fun initView()
    abstract fun initData()
    open fun isWithViewPager() = true
}