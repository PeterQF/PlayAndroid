package com.df.playandroid.ui.system.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.presenter.system.NavigationPresenter
import com.df.playandroid.response.navigation.NavigationData
import com.df.playandroid.ui.system.adapter.NavigationLabelRvAdapter
import com.df.playandroid.view.system.INavigationView
import kotlinx.android.synthetic.main.fragment_navigation.*

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class NavigationFragment : BaseFragment<INavigationView, NavigationPresenter>(), INavigationView {

    private lateinit var mAdapter: NavigationLabelRvAdapter
    private var mItems: MutableList<NavigationData> = ArrayList()

    override fun getLayoutId() = R.layout.fragment_navigation

    override fun setupPresenter() = NavigationPresenter(requireContext())

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = NavigationLabelRvAdapter(mItems)
        val layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        mPresenter?.getNavigationLabel()
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mNavigationLayout)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mNavigationLayout)
    }

    override fun getLabelSuccess(result: List<NavigationData>) {
        mItems.addAll(result)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        LoadingViewHelper.instance.dismiss(mNavigationLayout)
        super.onDestroy()
    }
}