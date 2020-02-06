package com.df.playandroid.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.df.playandroid.R
import com.df.playandroid.config.Constants

/**
 * 作者：PeterWu
 * 时间：2020/1/31
 * 描述：
 */
object IntentUtil {

    /**
     * 打开外部浏览器
     */
    fun openBroswer(context: Context, shareUrl: String) {
        Intent().run {
            action = "android.intent.action.VIEW"
            data = Uri.parse(shareUrl)
            context.startActivity(this)
        }
    }

    /**
     * 分享
     */
    fun shareTo(context: Context, shareTitle: String, shareUrl: String) {

        Intent().run {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                context.getString(
                    R.string.share_article_url,
                    context.getString(R.string.app_name),
                    shareTitle,
                    shareUrl
                )
            )
            type = Constants.CONTENT_SHARE_TYPE
            context.startActivity(Intent.createChooser(this, context.getString(R.string.share_title)))
        }
    }
}