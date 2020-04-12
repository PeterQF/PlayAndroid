package com.df.playandroid.view.system

import com.df.playandroid.response.category.CategoryData

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
interface ISortView {
    fun showLoadingView()
    fun hideLoadingView()
    fun getLabelSuccess(result: List<CategoryData>)
}