package com.df.playandroid.response.article

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：文章data和info
 */

data class ArticleResponse(val data: ArticleData) : BaseResponse()

data class ArticleData(
    val curPage: Int = 0,
    val datas: List<ArticleInfo>? = null,
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
): Serializable

data class ArticleInfo(
    val apkLink: String?= null,
    val audit: Int = 0,
    val author: String? = null,
    val chapterId: Int = 0,
    val chapterName: String? = null,
    val collect: Boolean = false,
    val courseId: Int = 0,
    val desc: String? = null,
    val envelopePic: String? = null,
    val fresh: Boolean = false,
    val id: Int = 0,
    val link: String? = null,
    val niceDate: String? = null,
    val niceShareDate: String? = null,
    val origin: String? = null,
    val prefix: String? = null,
    val projectLink: String? = null,
    val publishTime: Long = 0L,
    val selfVisible: Int = 0,
    val shareDate: Long = 0L,
    val shareUser: String? = null,
    val superChapterId: Int = 0,
    val superChapterName: String? = null,
    val tags: List<Tag>? = null,
    val title: String? = null,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0
): Serializable

data class Tag(
    val name: String? = null,
    val url: String? = null
): Serializable