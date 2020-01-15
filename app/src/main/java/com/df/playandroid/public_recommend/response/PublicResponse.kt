package com.df.playandroid.public_recommend.response

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

class PublicResponse(
    val data: List<PublicData>,
    errorCode: Int,
    errorMsg: String
) : BaseResponse(errorCode, errorMsg) {
    data class PublicData(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    ) : Serializable
}