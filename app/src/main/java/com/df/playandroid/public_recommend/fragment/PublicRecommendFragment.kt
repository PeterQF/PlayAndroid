package com.df.playandroid.public_recommend.fragment

import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.public_recommend.presenter.PublicRecommendPresenter
import com.df.playandroid.public_recommend.view.IPublicRecommendView
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：公众号Fragment
 */
class PublicRecommendFragment : BaseFragment<IPublicRecommendView, PublicRecommendPresenter>() {
    override fun getLayoutId() = R.layout.fragment_public_recommend

    override fun setupPresenter() = PublicRecommendPresenter(requireContext())

    override fun initView() {
        LogUtil.info("fragment ---> PublicRecommendFragment init view")
    }

    override fun initData() {
        LogUtil.info("fragment ---> PublicRecommendFragment init data")
    }

    override fun isWithViewPager() = false
}