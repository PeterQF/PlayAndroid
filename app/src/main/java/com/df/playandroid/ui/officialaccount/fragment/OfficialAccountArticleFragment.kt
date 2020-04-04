package com.df.playandroid.ui.officialaccount.fragment

import android.os.Bundle
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.presenter.officialaccount.OfficialAccountArticlePresenter
import com.df.playandroid.view.officialaccount.IOfficialAccountArticleView

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：公众号文章fragment
 */
class OfficialAccountArticleFragment : BaseFragment<IOfficialAccountArticleView, OfficialAccountArticlePresenter>() {

    companion object {
        fun newInstance(id: Int): OfficialAccountArticleFragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = OfficialAccountArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId() = R.layout.fragment_official_account_article

    override fun setupPresenter() = OfficialAccountArticlePresenter(requireContext())

    override fun initView() {
    }

    override fun initData() {
    }
}