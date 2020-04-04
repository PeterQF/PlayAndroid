package com.df.playandroid.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.df.playandroid.R
import com.df.playandroid.application.MyApplication

/**
 * 作者：PeterWu
 * 时间：2020/1/5
 * 描述：
 */
object ToastUtil {

    private var mToat: Toast? = null

    fun showToast(content: String) {
        LogUtil.info("show toast")
        val view = LayoutInflater.from(MyApplication.instance.applicationContext).inflate(R.layout.toast, null) as TextView
        if (null == mToat) {
            mToat = Toast(MyApplication.instance.applicationContext)
        }
        mToat?.apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0,DeviceUtil.getScreenWidth() / 4)
            view.text = content
            this.view = view
            show()
        }
    }
}