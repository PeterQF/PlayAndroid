package com.df.playandroid.ui.login.activity

import android.text.TextUtils
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.event.BaseEvent
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.helper.GlideHelper
import com.df.playandroid.base.helper.UserDataHelper
import com.df.playandroid.config.SpConstants
import com.df.playandroid.presenter.login.LoginPresenter
import com.df.playandroid.response.user.UserData
import com.df.playandroid.ui.register.RegisterActivity
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import com.df.playandroid.utils.ToastUtil
import com.df.playandroid.view.login.ILoginView
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<ILoginView, LoginPresenter>(), ILoginView {

    private var mLoadingDialog: QMUITipDialog? = null

    override fun setupPresenter() = LoginPresenter(this)

    override fun getLayoutId() = R.layout.activity_login

    override fun initView() {
        initDialog()
        mCloseIv.setOnClickListener { finish() }
        mLoginBtn.setOnClickListener { checkInput() }
        mRegisterTv.setOnClickListener { launch<RegisterActivity>() }
    }

    private fun initDialog() {
        mLoadingDialog = QMUITipDialog.Builder(this)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord(getString(R.string.register_ing))
            .create()
    }

    private fun checkInput() {
        val account = mAccountEt.text.toString()
        val password = mPasswordEt.text.toString()
        if (TextUtils.isEmpty(account) || account.contains(" ")) {
            mAccountEt.setText("")
            mAccountEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_username))
        } else if (TextUtils.isEmpty(password) || password.contains(" ")) {
            mPasswordEt.setText("")
            mPasswordEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_pwd))
        } else {
            mPresenter?.login(account, password)
        }
    }

    override fun showLoadingView() {
        mLoadingDialog?.show()
    }

    override fun hideLoadingView() {
        mLoadingDialog?.dismiss()
    }

    override fun initData() {}

    override fun loginSuccess(result: UserData) {
//        SPUtil.setObject(SpConstants.KEY_USER_BEAN, result)
        result.icon = UserDataHelper.getUserIcon()
        UserDataHelper.saveUser(result)
        SPUtil.setBoolean(SpConstants.KEY_IS_LOGIN, true)
        EventBus.getDefault().post(EventManager.UpdateUserInfo(result))
        finish()
    }

    override fun hadEventBus() = true

    override fun onMessageEvent(event: BaseEvent) {
        if (event is EventManager.RegisterSuccessEvent) {
            event.icon?.let { GlideHelper.loadUserIcon(this, it, mUserIcon) }
            mAccountEt.setText(event.username)
            mPasswordEt.setText(event.pwd)
        }
    }
}
