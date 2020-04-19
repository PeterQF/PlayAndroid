package com.df.playandroid.view.login

import com.df.playandroid.response.user.UserData

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
interface ILoginView {

    fun loginSuccess(result: UserData)
}