package com.df.playandroid.ui.login.activity

import android.text.TextUtils
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.config.SpConstants
import com.df.playandroid.presenter.login.LoginPresenter
import com.df.playandroid.response.user.UserData
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import com.df.playandroid.utils.ToastUtil
import com.df.playandroid.view.login.ILoginView
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<ILoginView, LoginPresenter>(), ILoginView {

    override fun setupPresenter() = LoginPresenter(this)

    override fun getLayoutId() = R.layout.activity_login

    override fun initView() {
        mLoginBtn.setOnClickListener { checkInput() }
        mRegisterTv.setOnClickListener {  }
    }

    private fun checkInput() {
        val account = mAccountEt.text.toString()
        val password = mPasswordEt.text.toString()
        if (TextUtils.isEmpty(account) || account.contains(" ")) {
            mAccountEt.setText("")
            mAccountEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_account))
        } else if (TextUtils.isEmpty(password) || password.contains(" ")) {
            mPasswordEt.setText("")
            mPasswordEt.requestFocus()
            ToastUtil.showToast(getString(R.string.login_input_pwd))
        } else {
            mPresenter?.login(account, password)
        }
    }

    override fun initData() {}

    override fun loginSuccess(result: UserData) {
        SPUtil.setObject(SpConstants.KEY_USER_BEAN, result)
        SPUtil.setBoolean(SpConstants.KEY_IS_LOGIN, true)
        EventBus.getDefault().post(EventManager.UpdateUserInfo(result))
        finish()
    }

    override fun initStatusBar() {
        ImmersionBar
            .with(this)
            .statusBarColor(R.color.white)
            .autoDarkModeEnable(true)
            .keyboardEnable(true)
            .navigationBarColor(R.color.white)
            .init()
    }
}
