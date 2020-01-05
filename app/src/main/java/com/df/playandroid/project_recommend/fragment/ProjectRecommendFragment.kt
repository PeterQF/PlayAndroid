package com.df.playandroid.project_recommend.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.project_recommend.presenter.ProjectRecommendPresenter
import com.df.playandroid.project_recommend.view.IProjectRecommendView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：项目Fragment
 */
class ProjectRecommendFragment : BaseFragment<IProjectRecommendView, ProjectRecommendPresenter>() {

    override fun getLayoutId() = R.layout.fragment_project_recommend

    override fun setupPresenter() = ProjectRecommendPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> ProjectRecommendFragment init view")
    }
    override fun initData() {
        LogUtil.info("fragment ---> ProjectRecommendFragment init data")
    }

    override fun isWithViewPager() = false
}