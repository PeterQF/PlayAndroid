package com.df.playandroid.ui.project.fragment

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.config.Constants
import com.df.playandroid.presenter.project.ProjectListPresenter
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.ui.content.activity.ContentActivity
import com.df.playandroid.ui.project.adapter.ProjectListRvAdapter
import com.df.playandroid.view.project.IProjectListView
import kotlinx.android.synthetic.main.base_refresh.*
import kotlinx.android.synthetic.main.fragment_project_list.*

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：
 */
class ProjectListFragment : BaseFragment<IProjectListView, ProjectListPresenter>(),
    IProjectListView {

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

    override fun getLayoutId() = R.layout.fragment_project_list

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
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.rv_divider)
            ?.let { decoration.setDrawable(it) }
        mRecyclerView.addItemDecoration(decoration)
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                mItems[position].link?.let { url ->
                    mItems[position].title?.let { title ->
                        ContentActivity.openWeb(
                            requireContext(),
                            mItems[position].id,
                            url,
                            title
                        )
                    }
                }
            )
        }
    }

    override fun initData() {
        mId?.let { mPresenter?.getProjectList(1, it, Constants.LoadType.LOADING) }
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mProjectListLayout)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mProjectListLayout)
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