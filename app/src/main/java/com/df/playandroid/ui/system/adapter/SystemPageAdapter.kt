package com.df.playandroid.ui.system.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.df.playandroid.ui.system.fragment.NavigationFragment
import com.df.playandroid.ui.system.fragment.SortFragment

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class SystemPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SortFragment()
            else -> NavigationFragment()
        }
    }
}