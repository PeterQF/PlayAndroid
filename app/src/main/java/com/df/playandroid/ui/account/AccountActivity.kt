package com.df.playandroid.ui.account

import android.content.Intent
import android.text.InputType
import android.view.View
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseActivity
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.helper.GlideHelper
import com.df.playandroid.base.helper.PictureSelectorHelper
import com.df.playandroid.base.helper.UserDataHelper
import com.df.playandroid.expansion.hideKeyboard
import com.df.playandroid.utils.ToastUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_account.*
import org.greenrobot.eventbus.EventBus

class AccountActivity : BaseActivity(), View.OnClickListener {

    private var isChooseIcon = false

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
        mCloseIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mSetIconRl -> {
                isChooseIcon = true
                PictureSelectorHelper.openGallery(this)
            }
            R.id.mSetCoverFl -> {
                isChooseIcon = false
                PictureSelectorHelper.openGallery(this)
            }
            R.id.mSetNicknameFl -> initEditTextDialog(
                getString(R.string.account_set_nickname),
                getString(R.string.account_please_input_nickname),
                TYPE_NICKNAME
            )
            R.id.mSetSignatureFl -> initEditTextDialog(
                getString(R.string.account_set_signature),
                getString(R.string.account_please_input_signature),
                TYPE_SIGNATURE

            )
            R.id.mLoginOutBtn -> { }
            R.id.mCloseIv -> EventBus.getDefault().post(EventManager.UpdateUserPage())
        }
    }

    private fun initEditTextDialog(title: String, placeholder: String, type: Int) {
        mEditTextDialog = QMUIDialog.EditTextDialogBuilder(this)
        mEditTextDialog?.run {
            setTitle(title)
            setPlaceholder(placeholder)
            setInputType(InputType.TYPE_CLASS_TEXT)
            addAction(getString(R.string.common_cancel)) { dialog, _ ->
                mEditTextDialog?.editText?.let { hideKeyboard(it) }
                dialog.dismiss()
            }
            addAction(getString(R.string.common_ok)) { dialog, index ->
                val text = mEditTextDialog?.editText?.text
                if (text != null && text.isNotEmpty()) {
                    when(type) {
                        TYPE_NICKNAME -> {
                            UserDataHelper.setUserNickname(text.toString())
                            mNicknameTv.text = text
                            ToastUtil.showToast(getString(R.string.account_set_success))
                        }
                        TYPE_SIGNATURE -> {
                            UserDataHelper.setUserSignature(text.toString())
                            mSetSignatureTv.text = text
                            ToastUtil.showToast(getString(R.string.account_set_success))
                        }
                    }
                }
                mEditTextDialog?.editText?.let { hideKeyboard(it) }
                dialog.dismiss()
            }
            setCanceledOnTouchOutside(false)
            create()
            show()
        }
    }

    override fun initData() {
        UserDataHelper.getUserIcon()?.let { GlideHelper.loadUserIcon(this, it, mUserIcon) }
        mNicknameTv.text = UserDataHelper.getUserNickname()
        mSetSignatureTv.text = UserDataHelper.getUserSignature()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val selectList = PictureSelector.obtainMultipleResult(data)
                if (selectList.isNullOrEmpty().not()) {
                    if (isChooseIcon) {
                        UserDataHelper.setUserIcon(selectList[0].compressPath)
                        GlideHelper.loadUserIcon(this, selectList[0].compressPath, mUserIcon)
                        ToastUtil.showToast(getString(R.string.account_set_success))
                    } else {
                        UserDataHelper.setUserCover(selectList[0].compressPath)
                        ToastUtil.showToast(getString(R.string.account_set_success))
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        EventBus.getDefault().post(EventManager.UpdateUserPage())
        super.onBackPressed()
    }
}
