package com.df.playandroid.ui.project.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.df.playandroid.ui.project.fragment.ProjectListFragment

/**
 * 作者：PeterWu
 * 时间：2020/4/4
 * 描述：
 */
class ProjectPageAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: ArrayList<ProjectListFragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}