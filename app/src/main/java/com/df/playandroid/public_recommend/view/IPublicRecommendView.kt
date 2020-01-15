package com.df.playandroid.public_recommend.view

import com.df.playandroid.public_recommend.response.PublicResponse

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
interface IPublicRecommendView {

    fun stopRefresh()
    fun getPublicItemSuccess(result: List<PublicResponse.PublicData>)
}