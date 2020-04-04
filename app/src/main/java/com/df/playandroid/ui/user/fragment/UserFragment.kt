package com.df.playandroid.ui.user.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.ui.setting.activity.SettingActivity
import com.df.playandroid.presenter.user.UserPresenter
import com.df.playandroid.view.user.IUserView
import com.df.playandroid.utils.LogUtil
import kotlinx.android.synthetic.main.layout_user_option.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：我的Fragment
 */
class UserFragment : BaseFragment<IUserView, UserPresenter>() {

    override fun getLayoutId() = R.layout.fragment_user

    override fun setupPresenter() =
        UserPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> UserFragment init view")
        mAccountFl.setOnClickListener {
        }
        mSettingFl.setOnClickListener {
            launch<SettingActivity>()
        }
    }

    override fun initData() {
        LogUtil.info("fragment ---> UserFragment init data")
    }

    override fun isWithViewPager() = false
}