package com.df.playandroid.base.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.base.bean.WebViewMenuItemBean

/**
 * 作者：PeterWu
 * 时间：2020/1/12
 * 描述：WebViewActivity菜单适配器
 */
class WebViewMenuRvAdapter(data: MutableList<WebViewMenuItemBean>) :
    BaseQuickAdapter<WebViewMenuItemBean, BaseViewHolder>(R.layout.rv_webview_meun_item, data) {
    override fun convert(helper: BaseViewHolder, item: WebViewMenuItemBean) {
        helper.setText(R.id.webView_menu_tv, item.content)
        helper.setImageDrawable(
            R.id.webView_menu_iv,
            ContextCompat.getDrawable(context, item.icon)
        )
    }
}