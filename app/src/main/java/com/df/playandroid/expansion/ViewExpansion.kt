package com.df.playandroid.expansion

import android.widget.EditText
import com.df.playandroid.widget.textwatcher.QFTextWatcher

/**
 * 作者：PeterWu
 * 时间：2020/5/30
 * 描述：View的扩展方法
 */

/**
 * 监测EditText的内容变化
 */
fun EditText.OnTextChange(fn: QFTextWatcher.() -> Unit) {
    val watcher = QFTextWatcher().apply(fn)
    addTextChangedListener(watcher)
}