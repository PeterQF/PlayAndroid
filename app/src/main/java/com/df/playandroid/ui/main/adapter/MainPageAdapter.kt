package com.df.playandroid.ui.main.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.df.playandroid.ui.home.fragment.HomeFragment
import com.df.playandroid.ui.officialaccount.fragment.OfficialAccountFragment
import com.df.playandroid.ui.project.fragment.ProjectFragment
import com.df.playandroid.ui.system.fragment.ProjectSystemFragment
import com.df.playandroid.ui.user.fragment.UserFragment

/**
 * 作者：PeterWu
 * 时间：2020/5/12
 * 描述：
 */
class MainPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 5

    override fun createFragment(position: Int) = when(position) {
        0 -> HomeFragment()
        1 -> ProjectFragment()
        2 -> OfficialAccountFragment()
        3 -> ProjectSystemFragment()
        else -> UserFragment()
    }
}