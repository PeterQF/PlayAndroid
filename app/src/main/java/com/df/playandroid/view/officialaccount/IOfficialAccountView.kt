package com.df.playandroid.view.officialaccount

import com.df.playandroid.response.category.CategoryData

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
interface IOfficialAccountView {
    fun getPublicItemSuccess(result: List<CategoryData>)
}