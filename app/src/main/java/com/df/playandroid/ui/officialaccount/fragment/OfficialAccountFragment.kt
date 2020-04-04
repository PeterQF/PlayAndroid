package com.df.playandroid.ui.officialaccount.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.ui.officialaccount.adapter.OfficialAccountRvAdapter
import com.df.playandroid.presenter.officialaccount.OfficialAccountPresenter
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import com.df.playandroid.view.officialaccount.IOfficialAccountView
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.tools.ScreenUtils
import kotlinx.android.synthetic.main.base_header.*
import kotlinx.android.synthetic.main.base_refresh.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：公众号Fragment
 */
class OfficialAccountFragment : BaseFragment<IOfficialAccountView, OfficialAccountPresenter>(),
    IOfficialAccountView {

    private var mOfficialAccountItem: MutableList<OfficialAccountResponse.PublicData> = ArrayList()
    private lateinit var mOfficialAccountAdapter: OfficialAccountRvAdapter

    override fun getLayoutId() = R.layout.fragment_official_account

    override fun setupPresenter() =
        OfficialAccountPresenter(requireContext())

    override fun initView() {
        base_header_title.text = getString(R.string.public_title)
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
        base_refresh_layout.autoRefresh()
    }

    override fun stopRefresh() {
        base_refresh_layout.finishRefresh(1000)
    }

    override fun getPublicItemSuccess(result: List<OfficialAccountResponse.PublicData>) {
        mOfficialAccountItem.clear()
        mOfficialAccountItem.addAll(result)
        mOfficialAccountAdapter.notifyDataSetChanged()
    }

    override fun isWithViewPager() = false
}