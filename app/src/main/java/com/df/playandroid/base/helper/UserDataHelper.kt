package com.df.playandroid.base.helper

import com.df.playandroid.config.SpConstants
import com.df.playandroid.response.user.UserData
import com.df.playandroid.utils.SPUtil

/**
 * 作者：PeterWu
 * 时间：2020/5/4
 * 描述：
 */
object UserDataHelper {

    fun saveUser(data: UserData) {
        data.username?.let { SPUtil.setString(SpConstants.KEY_USER_NAME, it) }
        data.icon?.let { SPUtil.setString(SpConstants.KEY_USER_ICON, it) }
        data.cover?.let { SPUtil.setString(SpConstants.KEY_USER_COVER, it) }
        data.nickname?.let { SPUtil.setString(SpConstants.KEY_USER_NICKNAME, it) }
        data.signature?.let { SPUtil.setString(SpConstants.KEY_USER_SIGNATURE, it) }
    }


    fun getUserName(): String? {
        return SPUtil.getString(SpConstants.KEY_USER_NAME)
    }

    fun setUserIcon(value: String) {
        SPUtil.setString(SpConstants.KEY_USER_ICON, value)
    }

    fun getUserIcon(): String? {
        return SPUtil.getString(SpConstants.KEY_USER_ICON)
    }

    fun setUserCover(value: String) {
        SPUtil.setString(SpConstants.KEY_USER_COVER, value)
    }

    fun getUserCover(): String? {
        return SPUtil.getString(SpConstants.KEY_USER_COVER)
    }

    fun setUserNickname(value: String) {
        SPUtil.setString(SpConstants.KEY_USER_NICKNAME, value)
    }

    fun getUserNickname(): String? {
        return SPUtil.getString(SpConstants.KEY_USER_NICKNAME)
    }

    fun setUserSignature(value: String) {
        SPUtil.setString(SpConstants.KEY_USER_SIGNATURE, value)
    }

    fun getUserSignature(): String? {
        return SPUtil.getString(SpConstants.KEY_USER_SIGNATURE)
    }

}