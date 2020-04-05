package com.df.playandroid.ui.project.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.presenter.project.ProjectPresenter
import com.df.playandroid.view.project.IProjectView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目Fragment
 */
class ProjectFragment : BaseFragment<IProjectView, ProjectPresenter>() {

    override fun getLayoutId() = R.layout.fragment_project

    override fun setupPresenter() =
        ProjectPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> ProjectFragment init view")
    }
    override fun initData() {
        LogUtil.info("init fragment recommend")
    }

}