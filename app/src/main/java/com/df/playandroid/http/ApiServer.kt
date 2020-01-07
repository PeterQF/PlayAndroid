package com.df.playandroid.http

import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeArticleResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServer {

    /**
     * 请求轮播图
     */
    @GET("/banner/json")
    fun requestBanner(): Observable<Response<BannerResponse>>

    /**
     * 请求首页文章列表
     */
    @GET("/article/list/{page}/json")
    fun requestHomeArticleList(@Path("page") page:Int): Observable<Response<HomeArticleResponse>>
}