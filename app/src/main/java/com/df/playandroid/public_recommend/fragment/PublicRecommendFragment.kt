package com.df.playandroid.public_recommend.fragment

import android.support.v7.widget.GridLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.public_recommend.adapter.PublicRvAdapter
import com.df.playandroid.public_recommend.presenter.PublicRecommendPresenter
import com.df.playandroid.public_recommend.response.PublicResponse
import com.df.playandroid.public_recommend.view.IPublicRecommendView
import kotlinx.android.synthetic.main.base_refresh.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：公众号Fragment
 */
class PublicRecommendFragment : BaseFragment<IPublicRecommendView, PublicRecommendPresenter>(), IPublicRecommendView {

    private var mPublicItem: MutableList<PublicResponse.PublicData> = ArrayList()
    private lateinit var mPublicAdapter: PublicRvAdapter

    override fun getLayoutId() = R.layout.fragment_public_recommend

    override fun setupPresenter() = PublicRecommendPresenter(requireContext())

    override fun initView() {
        initAdapter()
        initRefresh()
    }

    private fun initRefresh() {
        base_refresh_layout.setEnableLoadMore(false)
        base_refresh_layout.setOnRefreshListener {
            mPresenter?.getPublicItem()
        }
    }

    private fun initAdapter() {
        mPublicAdapter = PublicRvAdapter(mPublicItem)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        base_rv.layoutManager = layoutManager
        base_rv.adapter = mPublicAdapter
    }

    override fun initData() {
        base_refresh_layout.autoRefresh()
    }

    override fun stopRefresh() {
        base_refresh_layout.finishRefresh(1000)
    }

    override fun getPublicItemSuccess(result: List<PublicResponse.PublicData>) {
        mPublicItem.clear()
        mPublicItem.addAll(result)
        mPublicAdapter.notifyDataSetChanged()
    }

    override fun isWithViewPager() = false
}