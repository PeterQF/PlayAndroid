package com.df.playandroid.home.fragment

import com.df.playandroid.R
import com.df.playandroid.home.presenter.HomePresenter
import com.df.playandroid.home.view.IHomeView
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：首页文章Fragment
 */
class HomeFragment : BaseFragment<IHomeView, HomePresenter>(), IHomeView {

    override fun getLayoutId() = R.layout.fragment_home

    override fun setupPresenter() = HomePresenter(requireContext())

    override fun initView() {
        home_banner.setOnItemClickListener {

        }
    }

    override fun initData() {
        mPresenter?.getBanner()
    }

    override fun getBannerSuccess(result: List<BannerResponse.BannerData>) {
        home_banner.setData(result)
    }

    override fun isWithViewPager() = false
}