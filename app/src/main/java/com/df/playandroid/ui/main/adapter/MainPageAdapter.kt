package com.df.playandroid.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 作者：PeterWu
 * 时间：2020/5/12
 * 描述：
 */
class MainPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragments: ArrayList<Fragment>
) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}