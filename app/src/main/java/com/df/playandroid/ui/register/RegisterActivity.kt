package com.df.playandroid.ui.register

import android.content.Intent
import android.text.TextUtils
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.helper.GlideHelper
import com.df.playandroid.base.helper.PictureSelectorHelper
import com.df.playandroid.base.helper.UserDataHelper
import com.df.playandroid.presenter.register.RegisterPresenter
import com.df.playandroid.response.user.UserData
import com.df.playandroid.utils.ToastUtil
import com.df.playandroid.view.register.IRegisterView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseMvpActivity<IRegisterView, RegisterPresenter>(), IRegisterView {

    override fun setupPresenter() = RegisterPresenter(this)

    override fun getLayoutId() = R.layout.activity_register

    override fun initView() {
        mCloseIv.setOnClickListener { finish() }
        mRegisterBtn.setOnClickListener { checkInput() }
        mChooseIconRl.setOnClickListener { PictureSelectorHelper.openGallery(this) }
    }

    private fun checkInput() {
        val account = mAccountEt.text.toString()
        val password = mPasswordEt.text.toString()
        val passwordAgain = mPasswordAgainEt.text.toString()
        if (TextUtils.isEmpty(account) || account.contains(" ")) {
            mAccountEt.setText("")
            mAccountEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_username))
        } else if (TextUtils.isEmpty(password) || password.contains(" ")) {
            mPasswordEt.setText("")
            mPasswordEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_pwd))
        } else if (TextUtils.isEmpty(passwordAgain) || passwordAgain.contains(" ")) {
            mPasswordAgainEt.setText("")
            mPasswordAgainEt.requestFocus()
            ToastUtil.showToast(getString(R.string.register_input_pwd_again))
        } else if (password != passwordAgain) {
            mPasswordEt.setText("")
            mPasswordAgainEt.setText("")
            mPasswordEt.requestFocus()
            ToastUtil.showToast(getString(R.string.register_pwd_not_equal))
        } else {
            mPresenter?.register(account, password, passwordAgain)
        }
    }

    override fun registerSuccess(result: UserData, username: String, pwd: String, rePwd: String) {
        EventBus.getDefault().post(EventManager.RegisterSuccessEvent(username, pwd, UserDataHelper.getUserIcon()))
        finish()
    }

    override fun initData() {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val selectList = PictureSelector.obtainMultipleResult(data)
                if (selectList.isNullOrEmpty().not()) {
                    UserDataHelper.setUserIcon(selectList[0].compressPath)
                    GlideHelper.loadUserIcon(this, selectList[0].compressPath, mUserIcon)
                }
            }
        }
    }
}
