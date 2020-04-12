package com.df.playandroid.view.system

import com.df.playandroid.response.navigation.NavigationData

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
interface INavigationView {
    fun showLoadingView()
    fun hideLoadingView()
    fun getLabelSuccess(result: List<NavigationData>)
}