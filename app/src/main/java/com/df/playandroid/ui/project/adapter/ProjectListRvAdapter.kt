package com.df.playandroid.ui.project.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.article.ArticleInfo
import com.qmuiteam.qmui.widget.QMUIRadiusImageView

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：
 */
class ProjectListRvAdapter(data: MutableList<ArticleInfo>) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(
        R.layout.rv_project_item, data
    ) {
    override fun convert(helper: BaseViewHolder, item: ArticleInfo) {
        helper
            .setText(R.id.mTitleTv, item.title)
            .setText(R.id.mAuthorTv, item.author)
            .setText(R.id.mDescTv, item.desc)
            .setText(R.id.mTimeTv, item.niceShareDate)
        val cover = helper.getView<QMUIRadiusImageView>(R.id.mCoverIv)
        Glide.with(context).load(item.envelopePic).into(cover)
    }
}