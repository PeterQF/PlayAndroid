package com.df.playandroid.ui.system.adapter

import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.response.category.CategoryData
import com.df.playandroid.ui.system.activity.SortDetailActivity
import com.qmuiteam.qmui.widget.QMUIFloatLayout

/**
 * 作者：PeterWu
 * 时间：2020/4/12
 * 描述：
 */
class SortLabelRvAdapter(data: MutableList<CategoryData>) :
    BaseQuickAdapter<CategoryData, BaseViewHolder>(R.layout.rv_label_item, data) {

    override fun convert(helper: BaseViewHolder, item: CategoryData) {
        helper.setText(R.id.mLabelTitleTv, item.name)
        val floatLayout = helper.getView<QMUIFloatLayout>(R.id.mFloatLayout)
        floatLayout.removeAllViews()
        item.children?.forEach {
            val textView = LayoutInflater.from(context).inflate(R.layout.search_history_float_item, null) as TextView
            textView.text = it.name
            textView.setOnClickListener { v ->
                context.startActivity(
                    it.name?.let { title ->
                        SortDetailActivity.open(context,
                            it.id,
                            title
                        )
                    }
                )
            }
            floatLayout.addView(textView)
        }
    }
}