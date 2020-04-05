package com.df.playandroid.base.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.df.playandroid.R
import com.df.playandroid.application.MyApplication
import com.df.playandroid.utils.LogUtil

/**
 * 作者：PeterWu
 * 时间：2020/4/5
 * 描述：LoadingView
 */
class LoadingViewHelper {

    private var mLoadingViewLayout: FrameLayout? = null

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LoadingViewHelper()
        }
    }

    fun show(viewGroup: ViewGroup) {
        if (mLoadingViewLayout == null) {
            mLoadingViewLayout = LayoutInflater.from(MyApplication.instance.applicationContext)
                .inflate(R.layout.base_loading_view, null) as FrameLayout
        }
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mLoadingViewLayout!!.layoutParams = params
        viewGroup.removeView(mLoadingViewLayout)
        viewGroup.addView(mLoadingViewLayout)
        LogUtil.info("loading view show")
    }

    fun dismiss(viewGroup: ViewGroup) {
        if (mLoadingViewLayout != null) {
            LogUtil.info("loading view hide")
            viewGroup.removeView(mLoadingViewLayout)
            mLoadingViewLayout = null
        }
    }
}