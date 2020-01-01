package com.df.playandroid.project_system.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.project_system.presenter.ProjectSystemPresenter
import com.df.playandroid.project_system.view.IProjectSystemView

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目体系Fragment
 */
class ProjectSystemFragment : BaseFragment<IProjectSystemView, ProjectSystemPresenter>() {
    override fun getLayoutId() = R.layout.fragment_project_system

    override fun setupPresenter(): ProjectSystemPresenter? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}