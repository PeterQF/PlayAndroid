package com.df.playandroid.base.event

import com.df.playandroid.response.user.UserData

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
object EventManager {

    class UpdateUserInfo(val userInfo: UserData) : BaseEvent()

}