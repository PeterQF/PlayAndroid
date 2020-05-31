package com.df.playandroid.widget.textwatcher

import android.text.Editable
import android.text.TextWatcher

/**
 * 作者：PeterWu
 * 时间：2020/5/30
 * 描述：
 */
class QFTextWatcher : TextWatcher {

    private var textAfterChange: ((Editable?) -> Unit)? = null
    private var textBeforeChange: ((CharSequence?, Int, Int, Int) -> Unit)?= null
    private var textChange: ((CharSequence?, Int, Int, Int) -> Unit)? = null

    override fun afterTextChanged(s: Editable?) {
        textAfterChange?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        textBeforeChange?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChange?.invoke(s, start, before, count)
    }

    fun setOnTextChanged(textChange: (CharSequence?, Int, Int, Int) -> Unit) {
        this.textChange = textChange
    }

    fun setOnBeforeTextChanged(textBeforeChange: (CharSequence?, Int, Int, Int) -> Unit) {
        this.textBeforeChange = textBeforeChange
    }

    fun setOnAfterTextChange(textAfterChange: (Editable?) -> Unit) {
        this.textAfterChange = textAfterChange
    }
}