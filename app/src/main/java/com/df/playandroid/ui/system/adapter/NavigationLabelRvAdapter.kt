package com.df.playandroid.ui.system.adapter

import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.navigation.NavigationData
import com.df.playandroid.ui.content.activity.ContentActivity
import com.qmuiteam.qmui.widget.QMUIFloatLayout

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class NavigationLabelRvAdapter(data: MutableList<NavigationData>) :
    BaseQuickAdapter<NavigationData, BaseViewHolder>(R.layout.rv_label_item, data) {

    override fun convert(helper: BaseViewHolder, item: NavigationData) {
        helper.setText(R.id.mLabelTitleTv, item.name)
        val floatLayout = helper.getView<QMUIFloatLayout>(R.id.mFloatLayout)
        floatLayout.removeAllViews()
        item.articles?.forEach {
            val textView = LayoutInflater.from(context).inflate(R.layout.search_history_float_item, null) as TextView
            textView.text = it.title
            textView.setOnClickListener { v ->
                context.startActivity(
                    it.link?.let { link ->
                        it.title?.let { title ->
                            ContentActivity.openUrl(
                                context,
                                link,
                                title,
                                2
                            )
                        }
                    }
                )
            }
            floatLayout.addView(textView)
        }
    }
}