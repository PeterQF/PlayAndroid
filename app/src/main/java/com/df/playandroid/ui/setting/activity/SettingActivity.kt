package com.df.playandroid.ui.setting.activity

import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseActivity
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.utils.CacheDataManager
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_setting

    override fun initView() {
        mCacheTv.text = CacheDataManager.getTotalCacheSize(this)
    }

    override fun initData() {
    }
}
