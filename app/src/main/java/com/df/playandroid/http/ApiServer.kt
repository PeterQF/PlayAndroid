package com.df.playandroid.http

import com.df.playandroid.base.response.BaseResponse
import com.df.playandroid.response.category.CategoryResponse
import com.df.playandroid.response.article.ArticleResponse
import com.df.playandroid.response.content.CollectionResponse
import com.df.playandroid.response.home.BannerResponse
import com.df.playandroid.response.home.SearchHotWordResponse
import com.df.playandroid.response.navigation.NavigationResponse
import com.df.playandroid.response.user.UserResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

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
    fun requestHomeArticleList(@Path("page") page: Int): Observable<Response<ArticleResponse>>

    /**
     * 请求搜索热词
     */
    @GET("/hotkey/json")
    fun requestSearchHotWord(): Observable<Response<SearchHotWordResponse>>

    /**
     * 请求公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun requestPublicItem(): Observable<Response<CategoryResponse>>

    /**
     * 请求收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    fun requestCollectStationArticle(@Path("id") id: Int): Observable<Response<CollectionResponse>>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun requestUnCollectArticle(@Path("id") id: Int): Observable<Response<BaseResponse>>

    /**
     * 请求公众号文章
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun requestOfficialAccountArticleList(@Path("id") id: Int, @Path("page") page: Int): Observable<Response<ArticleResponse>>

    /**
     * 请求项目分类
     */
    @GET("/project/tree/json")
    fun requestProjectCategory(): Observable<Response<CategoryResponse>>

    /**
     * 请求项目列表
     */
    @GET("/project/list/{page}/json")
    fun requestProjectList(@Path("page") page: Int, @Query("cid") id: Int): Observable<Response<ArticleResponse>>

    /**
     * 请求体系分类
     */
    @GET("/tree/json")
    fun requestSortLabel(): Observable<Response<CategoryResponse>>

    /**
     * 请求导航分类
     */
    @GET("/navi/json")
    fun requestNavigationLabel(): Observable<Response<NavigationResponse>>

    /**
     * 请求分类label文章
     */
    @GET("/article/list/{page}/json")
    fun requestSortLabelArticle(@Path("page") page: Int, @Query("cid") id: Int): Observable<Response<ArticleResponse>>

    /**
     * 请求登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun requestLogin(@Field("username") username: String, @Field("password") password: String): Observable<Response<UserResponse>>

    /**
     * 请求注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun requestRegister(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): Observable<Response<UserResponse>>

    /**
     * 搜索
     */
    @POST("/article/query/{pageNum}/json")
    fun requestSearch(@Path("pageNum") pageNum: Int, @Query("k") k: String): Observable<Response<ArticleResponse>>

    /**
     * 搜索公众号内文章
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun requestSearchWxArticle(@Path("id") id: Int, @Path("page") page: Int, @Query("k") k: String): Observable<Response<ArticleResponse>>
}