package com.df.playandroid.utils

import com.df.playandroid.config.SpConstants

/**
 * 作者：PeterWu
 * 时间：2020/4/19
 * 描述：
 */
object AppUtils {

    fun isLogin() = SPUtil.getBoolean(SpConstants.KEY_IS_LOGIN, false)
}