package com.df.playandroid.home.response

import com.df.playandroid.base.response.BaseResponse

//data class HomeResponse(
//    val `data`: List<ArticleData>,
//    val errorCode: Int,
//    val errorMsg: String
//) : RxResponse() {
//    data class ArticleData(
//        val children: List<Any>,
//        val courseId: Int,
//        val id: Int,
//        val name: String,
//        val order: Int,
//        val parentChapterId: Int,
//        val userControlSetTop: Boolean,
//        val visible: Int
//    )
//}

data class HomeResponse(
    val code: Int,
    val msg: String,
    val data: List<HomeInfo>
) : BaseResponse(code, msg)

data class HomeInfo(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)