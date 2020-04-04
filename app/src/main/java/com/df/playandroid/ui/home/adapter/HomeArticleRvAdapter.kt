package com.df.playandroid.ui.home.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.home.HomeArticleResponse

/**
 * 作者：PeterWu
 * 时间：2020/1/6
 * 描述：首页文章Adapter
 */
class HomeArticleRvAdapter(data: MutableList<HomeArticleResponse.ArticleData.ArticleInfo>) :
    BaseQuickAdapter<HomeArticleResponse.ArticleData.ArticleInfo, BaseViewHolder>(R.layout.rv_home_article_item, data) {
    override fun convert(
        helper: BaseViewHolder,
        item: HomeArticleResponse.ArticleData.ArticleInfo
    ) {
        item?.let {
            helper
                .setText(R.id.item_article_title_tv, it.title)
                .setText(R.id.item_article_author_tv, it.author)
                .setText(R.id.item_article_time_tv, it.niceShareDate)
                .setText(R.id.item_article_sort_tv, it.chapterName)
            val authorTv = helper.getView<TextView>(R.id.item_article_author_tv)
            if (it.author.isNullOrEmpty()) {
                authorTv.visibility = View.GONE
            } else {
                authorTv.visibility = View.VISIBLE
            }
        }
    }

}