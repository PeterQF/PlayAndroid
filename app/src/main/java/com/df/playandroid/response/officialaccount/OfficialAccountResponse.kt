package com.df.playandroid.response.officialaccount

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

class OfficialAccountResponse(val data: List<PublicData>) : BaseResponse() {
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