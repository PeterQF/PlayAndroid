package com.df.playandroid.ui.system.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.navigation.NavigationData
import com.df.playandroid.ui.content.activity.ContentActivity
import com.donkingliang.labels.LabelsView

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class NavigationLabelRvAdapter(data: MutableList<NavigationData>) :
    BaseQuickAdapter<NavigationData, BaseViewHolder>(R.layout.rv_label_item, data) {

    override fun convert(helper: BaseViewHolder, item: NavigationData) {
        helper.setText(R.id.mLabelTitleTv, item.name)
        helper.getView<LabelsView>(R.id.mLabelView).apply {
            setLabels(item.articles) { _, _, data ->
                data.title
            }
            setOnLabelClickListener { label, data, position ->
                context.startActivity(
                    item.articles?.get(position)?.link?.let {
                        ContentActivity.openUrl(
                            context,
                            it,
                            label.text as String
                        )
                    }
                )
            }
        }
    }
}