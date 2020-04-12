package com.df.playandroid.response.category

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：目录Tab response
 */

data class CategoryResponse(val data: List<CategoryData>? = null) : BaseResponse()

data class CategoryData(
    val children: List<Children>? = null,
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String? = null,
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
): Serializable

data class Children(
    val children: List<Any>? = null,
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String? = null,
    val order: Int = 0,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean = false,
    val visible: Int = 0
): Serializable