package com.df.playandroid.ui.home.fragment

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.ui.content.activity.ContentActivity
import com.df.playandroid.presenter.home.HomePresenter
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.config.Constants
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.response.home.*
import com.df.playandroid.ui.home.adapter.HomeArticleRvAdapter
import com.df.playandroid.utils.LogUtil
import kotlinx.android.synthetic.main.base_search_header.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：首页文章Fragment
 */
class HomeFragment : BaseFragment<IHomeView, HomePresenter>(),
    IHomeView {

    private lateinit var mArticleAdapter: HomeArticleRvAdapter
    private var mArticleItems: MutableList<ArticleInfo> = ArrayList()
    private var mPage = 0
    private var mIsOver = false
    private var mLastPosition = -1

    override fun getLayoutId() = R.layout.fragment_home

    override fun setupPresenter() =
        HomePresenter(requireContext())

    override fun initView() {
        initAdapter()
        initRefreshAndLoadMore()
        mHeaderTitle.text = getString(R.string.home)
    }

    /**
     * banner点击事件
     */
    private fun initBannerClickListener(result: List<BannerData>) {
        home_banner.setOnItemClickListener {
            startActivity(
                result[it].url?.let { url ->
                    result[it].title?.let { title ->
                        ContentActivity.openWeb(
                            requireContext(),
                            result[it].id,
                            url,
                            title
                        )
                    }
                }
            )
//            startActivity(ContentActivity.openBanner(requireContext(), result[it], 1))
        }
    }

    private fun initRefreshAndLoadMore() {
        home_refresh_layout.setOnRefreshListener {
            mPresenter?.getArticles(0, Constants.LoadType.REFRESH)
        }
        home_refresh_layout.setOnLoadMoreListener {
            mPresenter?.getArticles(++mPage, Constants.LoadType.LOAD_MORE)
        }
    }

    private fun initAdapter() {
        mArticleAdapter = HomeArticleRvAdapter(mArticleItems)
        val layoutManager = LinearLayoutManager(requireContext())
        home_article_rv.layoutManager = layoutManager
        home_article_rv.adapter = mArticleAdapter
        mArticleAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
//                ContentActivity.openWeb(
//                    requireContext(),
//                    mArticleItems[position],
//                    0
//                )
                mArticleItems[position].link?.let { link ->
                    mArticleItems[position].title?.let { title ->
                        ContentActivity.openWeb(
                            requireContext(),
                            mArticleItems[position].id,
                            link,
                            title
                        )
                    }
                }
            )
        }
    }

    override fun initData() {
        LogUtil.info("init fragment home")
        mPresenter?.getBanner()
        mPresenter?.getHotWord()
        mPresenter?.getArticles(0, Constants.LoadType.LOADING)
    }

    override fun stopRefresh() {
        home_refresh_layout.finishRefresh(1000)
    }


    override fun getArticleSuccess(result: ArticleData) {
        mPage = result.curPage
        mIsOver = result.over
        val items = result.datas
        if (items != null && items.isNotEmpty()) {
            mArticleItems.clear()
            mArticleItems.addAll(items)
            mArticleAdapter.notifyDataSetChanged()
            mLastPosition = mArticleItems.size
        }
    }

    override fun loadMoreArticleSuccess(result: ArticleData) {
        mPage = result.curPage
        mIsOver = result.over
        val items = result.datas
        if (items != null && items.isNotEmpty()) {
            mArticleItems.addAll(items)
            mArticleAdapter.notifyItemInserted(mLastPosition)
            mLastPosition = mArticleItems.size
        }
    }

    /**
     * 得到搜索热词
     */
    override fun getHotWordSuccess(result: List<SearchHotWordData>) {
        for (i in result.indices) {
            val hotTv = TextView(requireContext())
            hotTv.text = result[i].name
            hotTv.textSize = 14f
            hotTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_main_sub_text
                )
            )
            hotTv.gravity = Gravity.CENTER_VERTICAL
            mFlipper.addView(hotTv)
        }
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mHomeLayout)
    }

    override fun hideLoadingView() {
        mHomeLayout.postDelayed({
            LoadingViewHelper.instance.dismiss(mHomeLayout)
        }, 1500)

    }

    override fun stopLoadMore() {
        if (mIsOver) {
            home_refresh_layout.finishLoadMoreWithNoMoreData()
        } else {
            home_refresh_layout.finishLoadMore()
        }
    }

    override fun getBannerSuccess(result: List<BannerData>) {
        home_banner.visibility = View.VISIBLE
        initBannerClickListener(result)
        home_banner.setData(result)
    }

    override fun onDestroy() {
        LoadingViewHelper.instance.dismiss(mHomeLayout)
        super.onDestroy()
    }
}