package com.df.playandroid.ui.system.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.presenter.system.ProjectSystemPresenter
import com.df.playandroid.view.system.IProjectSystemView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目体系Fragment
 */
class ProjectSystemFragment : BaseFragment<IProjectSystemView, ProjectSystemPresenter>() {

    override fun getLayoutId() = R.layout.fragment_system

    override fun setupPresenter() =
        ProjectSystemPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> ProjectSystemFragment init view")
    }

    override fun initData() {
        LogUtil.info("fragment ---> ProjectSystemFragment init data")
    }

    override fun isWithViewPager() = false
}