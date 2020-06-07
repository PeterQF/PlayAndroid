package com.df.playandroid.ui.officialaccount.fragment

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.df.playandroid.R
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.expansion.OnTextChange
import com.df.playandroid.expansion.hideKeyboard
import com.df.playandroid.presenter.officialaccount.OfficialAccountPresenter
import com.df.playandroid.response.category.CategoryData
import com.df.playandroid.ui.officialaccount.adapter.OfficialAccountPageAdapter
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.view.officialaccount.IOfficialAccountView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qmuiteam.qmui.util.QMUIKeyboardHelper.hideKeyboard
import kotlinx.android.synthetic.main.fragment_official_account.*
import org.greenrobot.eventbus.EventBus

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：公众号Fragment
 */
class OfficialAccountFragment : BaseFragment<IOfficialAccountView, OfficialAccountPresenter>(),
    IOfficialAccountView, TabLayout.OnTabSelectedListener {

    override fun getLayoutId() = R.layout.fragment_official_account

    override fun setupPresenter() = OfficialAccountPresenter(requireContext())

    override fun initView() {
        mHeaderTitle.text = getString(R.string.main_public_recommend)
        mTabLayout.addOnTabSelectedListener(this)
        initSearchEt()
    }

    private fun initSearchEt() {

        mSearchEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(mSearchEt)
                val mKeyword = mSearchEt.text.toString().trim()
                if (mKeyword.isNotEmpty()) {
                    // search
                    EventBus.getDefault().post(EventManager.SearchWxArticleEvent(mKeyword))
                }
            }
            false
        }

        mSearchEt.OnTextChange {
            setOnAfterTextChange {
                val text = mSearchEt.text
                if (text.isEmpty()) {
                    hideKeyboard(mSearchEt)
                    EventBus.getDefault().post(EventManager.CleanWxSearchArticleEvent())
                }
            }
        }
    }


    override fun initData() {
        LogUtil.info("init fragment public account")
        mPresenter?.getPublicItem()
    }

    override fun getPublicItemSuccess(result: List<CategoryData>) {
        val fragments: ArrayList<OfficialAccountArticleFragment> = ArrayList()
        for (i in result.indices) {
            val page = OfficialAccountArticleFragment.newInstance(result[i].id)
            fragments.add(page)
        }
        mViewPager.adapter = OfficialAccountPageAdapter(childFragmentManager, lifecycle, fragments)
        TabLayoutMediator(mTabLayout, mViewPager, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_tab_custom, null)
                val tabTv = tabView.findViewById<TextView>(R.id.mTabTv)
                tabTv.text = result[position].name
                tab.customView = tabView
            }).attach()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {}

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
}