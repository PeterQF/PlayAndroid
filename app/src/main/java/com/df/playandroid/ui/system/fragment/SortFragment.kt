package com.df.playandroid.ui.system.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.presenter.system.SortPresenter
import com.df.playandroid.response.category.CategoryData
import com.df.playandroid.ui.system.adapter.SortLabelRvAdapter
import com.df.playandroid.view.system.ISortView
import kotlinx.android.synthetic.main.fragment_sort.*

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：体系列表
 */
class SortFragment : BaseFragment<ISortView, SortPresenter>(), ISortView {

    private lateinit var mAdapter: SortLabelRvAdapter
    private var mItems: MutableList<CategoryData> = ArrayList()

    override fun getLayoutId() = R.layout.fragment_sort

    override fun setupPresenter() = SortPresenter(requireContext())

    override fun initView() {
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = SortLabelRvAdapter(mItems)
        val layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        mPresenter?.getSortLabel()
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mSortLayout)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mSortLayout)
    }

    override fun getLabelSuccess(result: List<CategoryData>) {
        mItems.addAll(result)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        mSortLayout?.let { LoadingViewHelper.instance.dismiss(it) }
        super.onDestroy()
    }
}