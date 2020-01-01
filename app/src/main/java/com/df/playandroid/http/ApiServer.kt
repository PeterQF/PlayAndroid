package com.df.playandroid.http

import com.df.playandroid.article.response.ArticleResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiServer {

    /**
     * 公众号列表
     */
    @GET("/wxarticle/chapters/json")
    fun requestArticles(): Observable<Response<ArticleResponse>>
}