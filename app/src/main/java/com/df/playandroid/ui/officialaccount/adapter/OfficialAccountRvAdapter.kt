package com.df.playandroid.ui.officialaccount.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.df.playandroid.R
import com.df.playandroid.config.Constants
import com.df.playandroid.response.officialaccount.OfficialAccountResponse
import com.qmuiteam.qmui.widget.QMUIRadiusImageView

/**
 * 作者：PeterWu
 * 时间：2020/1/15
 * 描述：公众号列表适配器
 */
class OfficialAccountRvAdapter(data: MutableList<OfficialAccountResponse.PublicData>) :
    BaseQuickAdapter<OfficialAccountResponse.PublicData, BaseViewHolder>(
        R.layout.rv_public_recommend_item, data
    ) {
    override fun convert(helper: BaseViewHolder, item: OfficialAccountResponse.PublicData) {
        item?.let {
//            val publicLayout = helper.getView<RelativeLayout>(R.id.public_item_rl)
//            val params = publicLayout.layoutParams
//            params.width = (DeviceUtil.getScreenWidth() - DeviceUtil.dp2px(40f)) / 2
//            publicLayout.layoutParams = params
            helper.setText(R.id.public_item_name, it.name)
            val publicIv = helper.getView<QMUIRadiusImageView>(R.id.public_item_icon)
            when(it.id) {
                Constants.PublicId.PUBLIC_408 -> {
                    publicIv.setImageResource(R.mipmap.icon_408)
                }
                Constants.PublicId.PUBLIC_409 -> {
                    publicIv.setImageResource(R.mipmap.icon_409)
                }
                Constants.PublicId.PUBLIC_410 -> {
                    publicIv.setImageResource(R.mipmap.icon_410)
                }
                Constants.PublicId.PUBLIC_411 -> {
                    publicIv.setImageResource(R.mipmap.icon_411)
                }
                Constants.PublicId.PUBLIC_413 -> {
                    publicIv.setImageResource(R.mipmap.icon_413)
                }
                Constants.PublicId.PUBLIC_414 -> {
                    publicIv.setImageResource(R.mipmap.icon_414)
                }
                Constants.PublicId.PUBLIC_415 -> {
                    publicIv.setImageResource(R.mipmap.icon_415)
                }
                Constants.PublicId.PUBLIC_416 -> {
                    publicIv.setImageResource(R.mipmap.icon_416)
                }
                Constants.PublicId.PUBLIC_417 -> {
                    publicIv.setImageResource(R.mipmap.icon_417)
                }
                Constants.PublicId.PUBLIC_420 -> {
                    publicIv.setImageResource(R.mipmap.icon_420)
                }
                Constants.PublicId.PUBLIC_421 -> {
                    publicIv.setImageResource(R.mipmap.icon_421)
                }
                Constants.PublicId.PUBLIC_427 -> {
                    publicIv.setImageResource(R.mipmap.icon_427)
                }
                Constants.PublicId.PUBLIC_428 -> {
                    publicIv.setImageResource(R.mipmap.icon_428)
                }
                Constants.PublicId.PUBLIC_434 -> {
                    publicIv.setImageResource(R.mipmap.icon_434)
                }
            }
        }
    }
}