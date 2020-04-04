package com.df.playandroid.ui.officialaccount.fragment

import android.graphics.Typeface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.ui.officialaccount.adapter.OfficialAccountRvAdapter
import com.df.playandroid.presenter.officialaccount.OfficialAccountPresenter
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import com.df.playandroid.ui.officialaccount.adapter.OfficialAccountPageAdapter
import com.df.playandroid.view.officialaccount.IOfficialAccountView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.tools.ScreenUtils
import kotlinx.android.synthetic.main.base_header.*
import kotlinx.android.synthetic.main.base_refresh.*
import kotlinx.android.synthetic.main.base_search_header.*
import kotlinx.android.synthetic.main.fragment_official_account.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：公众号Fragment
 */
class OfficialAccountFragment : BaseFragment<IOfficialAccountView, OfficialAccountPresenter>(),
    IOfficialAccountView, TabLayout.OnTabSelectedListener {

    private var mOfficialAccountItem: MutableList<OfficialAccountResponse.PublicData> = ArrayList()
    private lateinit var mOfficialAccountAdapter: OfficialAccountRvAdapter

    override fun getLayoutId() = R.layout.fragment_official_account

    override fun setupPresenter() = OfficialAccountPresenter(requireContext())

    override fun initView() {
        mHeaderTitle.text = getString(R.string.main_public_recommend)
        mTabLayout.addOnTabSelectedListener(this)
//        initAdapter()
//        initRefresh()
    }

    private fun initRefresh() {
        base_refresh_layout.setEnableLoadMore(false)
        base_refresh_layout.setOnRefreshListener {
            mPresenter?.getPublicItem()
        }
    }

    private fun initAdapter() {
        mOfficialAccountAdapter = OfficialAccountRvAdapter(mOfficialAccountItem)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        base_rv.layoutManager = layoutManager
        layoutManager.orientation = GridLayoutManager.VERTICAL
        val decoration =
            GridSpacingItemDecoration(2, ScreenUtils.dip2px(requireContext(), 20.0f), false)
        base_rv.addItemDecoration(decoration)
        base_rv.adapter = mOfficialAccountAdapter
    }

    override fun initData() {
        mPresenter?.getPublicItem()
//        base_refresh_layout.autoRefresh()
    }

    override fun stopRefresh() {
        base_refresh_layout.finishRefresh(1000)
    }

    override fun getPublicItemSuccess(result: List<OfficialAccountResponse.PublicData>) {
//        mOfficialAccountItem.clear()
//        mOfficialAccountItem.addAll(result)
//        mOfficialAccountAdapter.notifyDataSetChanged()
        val fragments: ArrayList<OfficialAccountArticleFragment> = ArrayList()
        for (i in result.indices) {
            val page = OfficialAccountArticleFragment.newInstance(result[i].id)
            fragments.add(page)
        }
        mViewPager.adapter = OfficialAccountPageAdapter(requireActivity(), fragments)
        mViewPager.offscreenPageLimit = 100
        TabLayoutMediator(mTabLayout, mViewPager, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_tab_custom, null)
                val tabTv = tabView.findViewById<TextView>(R.id.mTabTv)
                tabTv.text = result[position].name
                tab.customView = tabView
            }).attach()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val title = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        title?.run {
            textSize = 14.0f
            typeface = Typeface.DEFAULT
            setTextColor(ContextCompat.getColor(requireContext(), R.color.color_main_sub_text))
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val title = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        title?.run {
            textSize = 18.0f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mainColor))
        }
    }

    override fun isWithViewPager() = false
}