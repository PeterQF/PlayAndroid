package com.df.playandroid.home.fragment

import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.content.activity.WebViewActivity
import com.df.playandroid.home.presenter.HomePresenter
import com.df.playandroid.home.view.IHomeView
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.config.Constants
import com.df.playandroid.home.adapter.HomeArticleRvAdapter
import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeArticleResponse
import com.df.playandroid.home.response.SearchHotWordResponse
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：首页文章Fragment
 */
class HomeFragment : BaseFragment<IHomeView, HomePresenter>(), IHomeView {

    private lateinit var mArticleAdapter: HomeArticleRvAdapter
    private var mArticleItems: MutableList<HomeArticleResponse.ArticleData.ArticleInfo> = ArrayList()
    private var mPage = 0
    private var mArticleSize = 0

    override fun getLayoutId() = R.layout.fragment_home

    override fun setupPresenter() = HomePresenter(requireContext())

    override fun initView() {
        initAdapter()
        initRefreshAndLoadMore()
    }

    /**
     * banner点击事件
     */
    private fun initBannerClickListener(result: List<BannerResponse.BannerData>) {
        home_banner.setOnItemClickListener {
//            startActivity(WebViewActivity.openWeb(requireContext(), result[it].id, result[it].title, result[it].url))
            startActivity(WebViewActivity.openBanner(requireContext(), result[it], 1))
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
                WebViewActivity.openWeb(
                    requireContext(),
                    mArticleItems[position],
                    0
                )
            )
        }
    }

    override fun initData() {
        mPresenter?.getBanner()
        mPresenter?.getHotWord()
        home_refresh_layout.autoRefresh()
    }

    override fun stopRefresh() {
        home_refresh_layout.finishRefresh(1000)
    }

    override fun getArticleSuccess(result: HomeArticleResponse.ArticleData) {
        mArticleSize = result.size
        if (result.size != 0) {
            mArticleItems.clear()
            mArticleItems.addAll(result.datas)
            mArticleAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 得到搜索热词
     */
    override fun getHotWordSuccess(result: List<SearchHotWordResponse.SearchHotWordData>) {
        for (i in result.indices) {
            val hotTv = TextView(requireContext())
            hotTv.text = result[i].name
            hotTv.textSize = 14f
            hotTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_text_color))
            hotTv.gravity = Gravity.CENTER_VERTICAL
            home_view_flipper.addView(hotTv)
        }
    }

    override fun stopLoadMore() {
        if (mArticleSize == 0) {
            home_refresh_layout.finishLoadMoreWithNoMoreData()
        } else {
            home_refresh_layout.finishLoadMore()
        }
    }

    override fun loadMoreArticleSuccess(result: HomeArticleResponse.ArticleData) {
        mArticleSize = result.size
        if (result.size != 0) {
            mArticleItems.addAll(result.datas)
            mArticleAdapter.notifyDataSetChanged()
        }
    }

    override fun getBannerSuccess(result: List<BannerResponse.BannerData>) {
        initBannerClickListener(result)
        home_banner.setData(result)
    }

    override fun isWithViewPager() = false
}