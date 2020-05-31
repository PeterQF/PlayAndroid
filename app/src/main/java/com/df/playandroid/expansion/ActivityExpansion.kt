package com.df.playandroid.expansion

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 作者：PeterWu
 * 时间：2020/5/30
 * 描述：
 */

/**
 * 显示软键盘
 */
fun Activity.showKeyboard(editText: EditText) {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    editText.isFocusable = true
    editText.isFocusableInTouchMode = true
    editText.requestFocus()
    editText.postDelayed({
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 500)
}

/**
 * 隐藏软键盘
 */
fun Activity.hideKeyboard(editText: EditText) {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}