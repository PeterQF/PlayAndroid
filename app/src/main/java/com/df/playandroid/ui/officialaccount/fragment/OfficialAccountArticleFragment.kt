package com.df.playandroid.ui.officialaccount.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.config.Constants
import com.df.playandroid.presenter.officialaccount.OfficialAccountArticlePresenter
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.ui.content.activity.ContentActivity
import com.df.playandroid.ui.home.adapter.OfficialAccountArticleRvAdapter
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.view.officialaccount.IOfficialAccountArticleView
import kotlinx.android.synthetic.main.base_refresh.*
import kotlinx.android.synthetic.main.fragment_official_account_article.*

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：公众号文章fragment
 */
class OfficialAccountArticleFragment :
    BaseFragment<IOfficialAccountArticleView, OfficialAccountArticlePresenter>(),
    IOfficialAccountArticleView {

    private lateinit var mArticleAdapter: OfficialAccountArticleRvAdapter
    private var mArticleItems: MutableList<ArticleInfo> = ArrayList()
    private var mPage = 1
    private var mIsOver = false
    private var mLastPosition = -1

    companion object {
        fun newInstance(id: Int): OfficialAccountArticleFragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = OfficialAccountArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mId by lazy {
        arguments?.getInt("id")
    }

    override fun getLayoutId() = R.layout.fragment_official_account_article

    override fun setupPresenter() = OfficialAccountArticlePresenter(requireContext())

    override fun initView() {
        initAdapter()
        initRefreshAndLoadMore()
    }

    private fun initRefreshAndLoadMore() {
        mRefreshLayout.setOnRefreshListener {
            mId?.let { mPresenter?.getOfficialAccountArticle(it, 1, Constants.LoadType.REFRESH) }
        }
        mRefreshLayout.setOnLoadMoreListener {
            mId?.let { mPresenter?.getOfficialAccountArticle(it, ++mPage, Constants.LoadType.LOAD_MORE) }
        }
    }

    private fun initAdapter() {
        mArticleAdapter = OfficialAccountArticleRvAdapter(mArticleItems)
        val layoutManager = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mArticleAdapter
        mArticleAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                mArticleItems[position].link?.let { link ->
                    mArticleItems[position].title?.let { title ->
                        ContentActivity.openArticle(
                            requireContext(),
                            mArticleItems[position],
                            0
                        )
                    }
                }
            )
        }
    }

    override fun initData() {
        LogUtil.info("init fragment public article")
        mId?.let { mPresenter?.getOfficialAccountArticle(it, 1, Constants.LoadType.LOADING) }
    }

    override fun getArticleSuccess(result: ArticleData) {
        val list = result.datas
        mIsOver = result.over
        mPage = result.curPage
        if (list != null && list.isNotEmpty()) {
            mArticleItems.clear()
            mArticleItems.addAll(list)
            mArticleAdapter.notifyDataSetChanged()
            mLastPosition = mArticleItems.size
        }
    }

    override fun loadMoreArticleSuccess(result: ArticleData) {
        val list = result.datas
        mIsOver = result.over
        mPage = result.curPage
        if (list != null && list.isNotEmpty()) {
            mArticleItems.addAll(list)
            mArticleAdapter.notifyItemInserted(mLastPosition)
            mLastPosition = mArticleItems.size
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

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mOfficialAccountArticleLayout)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mOfficialAccountArticleLayout)
    }

    override fun onDestroy() {
        if (mOfficialAccountArticleLayout != null) {
            LoadingViewHelper.instance.dismiss(mOfficialAccountArticleLayout)
        }
        super.onDestroy()
    }
}