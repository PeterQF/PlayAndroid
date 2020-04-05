package com.df.playandroid.ui.project.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.config.Constants
import com.df.playandroid.presenter.project.ProjectListPresenter
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.ui.project.adapter.ProjectListRvAdapter
import com.df.playandroid.view.project.IProjectListView
import kotlinx.android.synthetic.main.base_refresh.*

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：
 */
class ProjectListFragment : BaseFragment<IProjectListView, ProjectListPresenter>(), IProjectListView {

    private lateinit var mAdapter: ProjectListRvAdapter
    private var mItems: MutableList<ArticleInfo> = ArrayList()
    private var mPage = 1
    private var mIsOver = false
    private var mLastPosition = -1

    companion object {
        fun newInstance(id: Int): ProjectListFragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = ProjectListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mId by lazy {
        arguments?.getInt("id")
    }

    override fun getLayoutId() = R.layout.base_refresh

    override fun setupPresenter() = ProjectListPresenter(requireContext())

    override fun initView() {
        initAdapter()
        initRefreshAndLoadMore()
    }

    private fun initRefreshAndLoadMore() {
        mRefreshLayout.setOnRefreshListener {
            mId?.let { mPresenter?.getProjectList(1, it, Constants.LoadType.REFRESH) }
        }
        mRefreshLayout.setOnLoadMoreListener {
            mId?.let { mPresenter?.getProjectList(++mPage, it, Constants.LoadType.LOAD_MORE) }
        }
    }

    private fun initAdapter() {
        mAdapter = ProjectListRvAdapter(mItems)
        val layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        mId?.let { mPresenter?.getProjectList(1, it, Constants.LoadType.LOADING) }
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mRefreshLayout)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mRefreshLayout)
    }

    override fun getProjectSuccess(result: ArticleData) {
        val list = result.datas
        mIsOver = result.over
        mPage = result.curPage
        if (list != null && list.isNotEmpty()) {
            mItems.clear()
            mItems.addAll(list)
            mAdapter.notifyDataSetChanged()
            mLastPosition = mItems.size
        }
    }

    override fun loadMoreProjectSuccess(result: ArticleData) {
        val list = result.datas
        mIsOver = result.over
        mPage = result.curPage
        if (list != null && list.isNotEmpty()) {
            mItems.addAll(list)
            mAdapter.notifyItemInserted(mLastPosition)
            mLastPosition = mItems.size
        }
    }

    override fun stopRefresh() {
        mRefreshLayout.finishRefresh(1000)
    }

    override fun stopLoadMore() {
        if (mIsOver) {
            mRefreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            mRefreshLayout.finishLoadMore()
        }
    }

    override fun onDestroy() {
        if (mRefreshLayout != null) {
            LoadingViewHelper.instance.dismiss(mRefreshLayout)
        }
        super.onDestroy()
    }
}