package com.df.playandroid.http

import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiServer {

    /**
     * 公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun requestArticles(): Observable<Response<HomeResponse>>

    /**
     * 请求轮播图
     */
    @GET("/banner/json")
    fun requestBanner(): Observable<Response<BannerResponse>>
}