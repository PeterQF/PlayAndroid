package com.df.playandroid.view.register

import com.df.playandroid.response.user.UserData

/**
 * 作者：PeterWu
 * 时间：2020/5/2
 * 描述：
 */
interface IRegisterView {

    fun registerSuccess(result: UserData, username: String, pwd: String, rePwd: String)
}