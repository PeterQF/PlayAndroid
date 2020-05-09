package com.df.playandroid.ui.account

import android.text.InputType
import android.view.View
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseActivity
import com.df.playandroid.base.helper.UserDataHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val TYPE_NICKNAME = 0
        const val TYPE_SIGNATURE = 1
    }

    private var mEditTextDialog: QMUIDialog.EditTextDialogBuilder? = null

    override fun getLayoutId() = R.layout.activity_account

    override fun initView() {
        mSetIconRl.setOnClickListener(this)
        mSetCoverFl.setOnClickListener(this)
        mSetNicknameFl.setOnClickListener(this)
        mSetSignatureFl.setOnClickListener(this)
        mLoginOutBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mSetIconRl -> {
            }
            R.id.mSetCoverFl -> {
            }
            R.id.mSetNicknameFl -> initEditTextDialog(
                getString(R.string.account_set_nickname),
                getString(R.string.account_please_input_nickname),
                TYPE_NICKNAME
            )
            R.id.mSetSignatureFl -> initEditTextDialog(
                getString(R.string.account_set_signature),
                getString(R.string.account_please_input_signature),
                TYPE_NICKNAME
            )
            R.id.mLoginOutBtn -> { }
        }
    }

    private fun initEditTextDialog(title: String, placeholder: String, type: Int) {
        mEditTextDialog = QMUIDialog.EditTextDialogBuilder(this)
        mEditTextDialog?.run {
            setTitle(title)
            setPlaceholder(placeholder)
            setInputType(InputType.TYPE_CLASS_TEXT)
            addAction(getString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            addAction(getString(R.string.common_ok)) { dialog, index ->
                val text = mEditTextDialog?.editText?.text
                if (text != null && text.isNotEmpty()) {
                    when(type) {
                        TYPE_NICKNAME -> UserDataHelper.setUserNickname(text.toString())
                        TYPE_SIGNATURE -> UserDataHelper.setUserSignature(text.toString())
                    }
                }
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    override fun initData() {}
}
