package com.df.playandroid.ui.system.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.category.CategoryData
import com.df.playandroid.response.category.Children
import com.df.playandroid.ui.system.activity.SortDetailActivity
import com.donkingliang.labels.LabelsView
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.tools.ScreenUtils

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class SortLabelRvAdapter(data: MutableList<CategoryData>) :
    BaseQuickAdapter<CategoryData, BaseViewHolder>(R.layout.rv_label_item, data) {

    override fun convert(helper: BaseViewHolder, item: CategoryData) {
        helper.setText(R.id.mLabelTitleTv, item.name)
        helper.getView<LabelsView>(R.id.mLabelView).apply {
            setLabels(item.children) { _, _, data ->
                data.name
            }
            setOnLabelClickListener { label, _, position ->
                context.startActivity(item.children?.get(position)?.id?.let {
                    SortDetailActivity.open(context,
                        it,
                        label.text as String
                    )
                })
            }
        }
    }
}