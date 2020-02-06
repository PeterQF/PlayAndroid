package com.df.playandroid.home.response

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

class HomeArticleResponse(
    val data: ArticleData,
    errorCode: Int,
    errorMsg: String
) : BaseResponse(errorCode, errorMsg){
    data class ArticleData(
        val curPage: Int,
        val datas: List<ArticleInfo>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    ) {
        data class ArticleInfo(
            val apkLink: String,
            val audit: Int,
            val author: String,
            val chapterId: Int,
            val chapterName: String,
            val collect: Boolean,
            val courseId: Int,
            val desc: String,
            val envelopePic: String,
            val fresh: Boolean,
            val id: Int,
            val link: String,
            val niceDate: String,
            val niceShareDate: String,
            val origin: String,
            val prefix: String,
            val projectLink: String,
            val publishTime: Long,
            val selfVisible: Int,
            val shareDate: Long,
            val shareUser: String,
            val superChapterId: Int,
            val superChapterName: String,
            val tags: List<Any>,
            val title: String,
            val type: Int,
            val userId: Int,
            val visible: Int,
            val zan: Int
        ): Serializable
    }
}