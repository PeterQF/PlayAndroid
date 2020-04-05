package com.df.playandroid.ui.home.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.home.HomeArticleResponse
import com.df.playandroid.response.officialaccount.OfficialAccountArticleInfo

/**
 * 作者：PeterWu
 * 时间：2020/1/6
 * 描述：首页文章Adapter
 */
class OfficialAccountArticleRvAdapter(data: MutableList<OfficialAccountArticleInfo>) :
    BaseQuickAdapter<OfficialAccountArticleInfo, BaseViewHolder>(R.layout.rv_home_article_item, data) {
    override fun convert(helper: BaseViewHolder, item: OfficialAccountArticleInfo) {
        helper
            .setText(R.id.item_article_title_tv, item.title)
            .setText(R.id.item_article_author_tv, item.author)
            .setText(R.id.item_article_time_tv, item.niceShareDate)
            .setText(R.id.item_article_sort_tv, context.getString(R.string.main_public_recommend))
        val authorTv = helper.getView<TextView>(R.id.item_article_author_tv)
        if (item.author == null) {
            authorTv.visibility = View.GONE
        } else {
            authorTv.visibility = View.VISIBLE
        }
    }

}