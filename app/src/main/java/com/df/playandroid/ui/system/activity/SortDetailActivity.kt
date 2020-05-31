package com.df.playandroid.ui.system.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.config.Constants
import com.df.playandroid.presenter.system.SortDetailPresenter
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.ui.content.activity.ContentActivity
import com.df.playandroid.ui.home.adapter.HomeArticleRvAdapter
import com.df.playandroid.view.system.ISortDetailView
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.base_header.*
import kotlinx.android.synthetic.main.base_refresh.*

class SortDetailActivity : BaseMvpActivity<ISortDetailView, SortDetailPresenter>(),
    ISortDetailView {

    private lateinit var mAdapter: HomeArticleRvAdapter
    private var mItems: MutableList<ArticleInfo> = ArrayList()
    private var mPage = 0
    private var mIsOver = false
    private var mLastPosition = -1

    companion object {
        fun open(context: Context, cId: Int, title: String): Intent {
            return Intent(context, SortDetailActivity::class.java)
                .putExtra("cId", cId)
                .putExtra("title", title)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    private val mCId by lazy {
        intent.getIntExtra("cId", -1)
    }

    private val mTitle by lazy {
        intent.getStringExtra("title")
    }

    override fun setupPresenter() = SortDetailPresenter(this)

    override fun getLayoutId() = R.layout.activity_sort_detail

    override fun initView() {
        initAdapter()
        initRefreshAndLoadMore()
        mHeaderBackIv.setOnClickListener { finish() }
        mHeaderTitle.text = mTitle
    }

    private fun initRefreshAndLoadMore() {
        mRefreshLayout.setOnRefreshListener {
            mPresenter?.getArticle(0, mCId, Constants.LoadType.REFRESH)
        }
        mRefreshLayout.setOnLoadMoreListener {
            mPresenter?.getArticle(++mPage, mCId, Constants.LoadType.LOAD_MORE)
        }
    }

    private fun initAdapter() {
        mAdapter = HomeArticleRvAdapter(mItems)
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                ContentActivity.openArticle(
                    this,
                    mItems[position],
                    0
                )
            )
        }
    }

    override fun initData() {
        mRefreshLayout.autoRefresh()
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

    override fun getArticleSuccess(result: ArticleData) {
        mPage = result.curPage
        mIsOver = result.over
        val items = result.datas
        if (items != null && items.isNotEmpty()) {
            mItems.clear()
            mItems.addAll(items)
            mAdapter.notifyDataSetChanged()
            mLastPosition = mItems.size
        }
    }

    override fun loadMoreArticleSuccess(result: ArticleData) {
        mPage = result.curPage
        mIsOver = result.over
        val items = result.datas
        if (items != null && items.isNotEmpty()) {
            mItems.addAll(items)
            mAdapter.notifyItemInserted(mLastPosition)
            mLastPosition = mItems.size
        }
    }

    override fun initStatusBar() {
        ImmersionBar
            .with(this)
            .statusBarColor(R.color.mainColor)
            .keyboardEnable(true)
            .navigationBarColor(R.color.white)
            .autoNavigationBarDarkModeEnable(true )
            .init()
    }
}
