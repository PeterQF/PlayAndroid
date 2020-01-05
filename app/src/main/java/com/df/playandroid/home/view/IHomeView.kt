package com.df.playandroid.home.view

import com.df.playandroid.home.response.BannerResponse

interface IHomeView {
    fun getBannerSuccess(result: List<BannerResponse.BannerData>)
}