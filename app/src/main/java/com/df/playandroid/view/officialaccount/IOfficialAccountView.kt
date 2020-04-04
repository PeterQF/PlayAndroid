package com.df.playandroid.view.officialaccount

import com.df.playandroid.response.officialaccount.OfficialAccountResponse

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：
 */
interface IOfficialAccountView {

    fun stopRefresh()
    fun getPublicItemSuccess(result: List<OfficialAccountResponse.PublicData>)
}