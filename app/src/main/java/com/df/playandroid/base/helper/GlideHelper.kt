package com.df.playandroid.base.helper

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.df.playandroid.R

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
object GlideHelper {

    private fun getOptions(holder: Int): RequestOptions {
        return RequestOptions().placeholder(holder).error(holder)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView, holder: Int = R.mipmap.image_lu_lu) {
//        val options = RequestOptions().placeholder(R.mipmap.image_lu_lu).error(R.mipmap.image_lu_lu)
        Glide
            .with(context)
            .load(url)
            .apply(getOptions(holder))
            .into(imageView)
    }

    fun loadUserCover(context: Context, url: String, imageView: ImageView, holder: Int = R.mipmap.image_lu_lu) {
        Glide
            .with(context)
            .load(url)
            .apply(getOptions(holder))
            .into(imageView)
    }

    fun loadUserIcon(context: Context, url: String, imageView: ImageView, holder: Int = R.mipmap.image_eyes) {
        Glide
            .with(context)
            .load(url)
            .apply(getOptions(holder))
            .into(imageView)
    }
}