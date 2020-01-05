package com.df.playandroid.project_system.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.project_system.presenter.ProjectSystemPresenter
import com.df.playandroid.project_system.view.IProjectSystemView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目体系Fragment
 */
class ProjectSystemFragment : BaseFragment<IProjectSystemView, ProjectSystemPresenter>() {
    override fun getLayoutId() = R.layout.fragment_project_system

    override fun setupPresenter() = ProjectSystemPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> ProjectSystemFragment init view")
    }

    override fun initData() {
        LogUtil.info("fragment ---> ProjectSystemFragment init data")
    }

    override fun isWithViewPager() = false
}