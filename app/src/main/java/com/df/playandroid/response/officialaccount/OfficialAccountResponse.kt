package com.df.playandroid.response.officialaccount

import com.df.playandroid.base.response.BaseResponse
import java.io.Serializable

class OfficialAccountResponse(val data: List<PublicData>) : BaseResponse() {
    data class PublicData(
        val children: List<Any>? = null,
        val courseId: Int = 0,
        val id: Int = 0,
        val name: String? = null,
        val order: Int = 0,
        val parentChapterId: Int = 0,
        val userControlSetTop: Boolean = false,
        val visible: Int = 0
    ) : Serializable
}