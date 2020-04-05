package com.df.playandroid.http

import com.df.playandroid.response.content.CollectionResponse
import com.df.playandroid.response.home.BannerResponse
import com.df.playandroid.response.home.HomeArticleResponse
import com.df.playandroid.response.home.SearchHotWordResponse
import com.df.playandroid.response.officialaccount.OfficialAccountArticleResponse
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
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

    /**
     * 请求搜索热词
     */
    @GET("/hotkey/json")
    fun requestSearchHotWord(): Observable<Response<SearchHotWordResponse>>

    /**
     * 请求公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun requestPublicItem(): Observable<Response<OfficialAccountResponse>>

    /**
     * 请求收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    fun requestCollectStationArticle(@Path("id") id: Int): Observable<Response<CollectionResponse>>

    /**
     * 请求公众号文章
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun requestOfficialAccountArticleList(@Path("id") id: Int, @Path("page") page:Int): Observable<Response<OfficialAccountArticleResponse>>

}