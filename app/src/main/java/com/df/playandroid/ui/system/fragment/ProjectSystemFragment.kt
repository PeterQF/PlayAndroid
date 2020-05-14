package com.df.playandroid.ui.system.fragment

import android.graphics.Typeface
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.df.playandroid.R
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.presenter.system.ProjectSystemPresenter
import com.df.playandroid.ui.system.adapter.SystemPageAdapter
import com.df.playandroid.view.system.IProjectSystemView
import com.df.playandroid.utils.LogUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.base_search_header.*
import kotlinx.android.synthetic.main.fragment_system.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：体系Fragment
 */
class ProjectSystemFragment : BaseFragment<IProjectSystemView, ProjectSystemPresenter>(), TabLayout.OnTabSelectedListener {

    override fun getLayoutId() = R.layout.fragment_system

    override fun setupPresenter() = ProjectSystemPresenter(requireContext())

    override fun initView() {
        mHeaderTitle.text = getString(R.string.main_project_system)
        mTabLayout.addOnTabSelectedListener(this)
        initTab()
    }

    private fun initTab() {
        mViewPager.adapter = SystemPageAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(mTabLayout, mViewPager, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_tab_custom, null)
                val tabTv = tabView.findViewById<TextView>(R.id.mTabTv)
                tabTv.text = when(position) {
                    0 -> "分类"
                    else -> "导航"
                }
                tab.customView = tabView
            }).attach()
    }

    override fun initData() {
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