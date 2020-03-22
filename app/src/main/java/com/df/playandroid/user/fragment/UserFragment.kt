package com.df.playandroid.user.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.user.presenter.UserPresenter
import com.df.playandroid.user.view.IUserView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：我的Fragment
 */
class UserFragment : BaseFragment<IUserView, UserPresenter>() {

    override fun getLayoutId() = R.layout.fragment_user

    override fun setupPresenter() = UserPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> UserFragment init view")
    }

    override fun initData() {
        LogUtil.info("fragment ---> UserFragment init data")
    }

    override fun isWithViewPager() = false
}