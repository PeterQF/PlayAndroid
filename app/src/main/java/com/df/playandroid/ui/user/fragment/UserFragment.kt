package com.df.playandroid.ui.user.fragment

import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.df.playandroid.R
import com.df.playandroid.base.event.BaseEvent
import com.df.playandroid.base.event.EventManager
import com.df.playandroid.base.fragment.BaseFragment
import com.df.playandroid.base.helper.GlideHelper
import com.df.playandroid.base.helper.UserDataHelper
import com.df.playandroid.config.SpConstants
import com.df.playandroid.ui.setting.activity.SettingActivity
import com.df.playandroid.presenter.user.UserPresenter
import com.df.playandroid.response.user.UserData
import com.df.playandroid.ui.account.AccountActivity
import com.df.playandroid.ui.login.activity.LoginActivity
import com.df.playandroid.utils.AppUtils
import com.df.playandroid.view.user.IUserView
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import com.df.playandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.layout_user_option.*

/**
 * 作者：PeterWu
 * 时间：2020/1/1
 * 描述：我的Fragment
 */
class UserFragment : BaseFragment<IUserView, UserPresenter>(), View.OnClickListener {

    override fun getLayoutId() = R.layout.fragment_user

    override fun setupPresenter() = UserPresenter(requireContext())

    override fun initView() {
        mAccountFl.setOnClickListener(this)
        mSettingFl.setOnClickListener(this)
        mUserIcon.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mAccountFl -> {
                if (AppUtils.isLogin()) {
                    launch<AccountActivity>()
                } else {
                    ToastUtil.showToast(getString(R.string.common_please_login))
                }
            }
            R.id.mSettingFl -> {}
            R.id.mUserIcon -> {
                if (AppUtils.isLogin().not()) launch<LoginActivity>()
            }
        }
    }

    override fun initData() {
        if (AppUtils.isLogin()) {
            mUserName.text = UserDataHelper.getUserName()
            mSetSignatureTv.text = UserDataHelper.getUserSignature()
            UserDataHelper.getUserCover()?.let {
                GlideHelper.loadUserCover(requireContext(),
                    it, mUserCover)
            }
            UserDataHelper.getUserIcon()?.let {
                GlideHelper.loadUserIcon(requireContext(),
                    it, mUserIcon)
            }
        }
    }

    override fun hadEventBus() = true

    override fun onMessageEvent(event: BaseEvent) {
        if (event is EventManager.UpdateUserInfo) {
            mUserName.text = event.userInfo.username
            event.userInfo.icon?.let { GlideHelper.loadUserIcon(requireContext(), it, mUserIcon) }
            mSetSignatureTv.text = getString(R.string.user_no_signature)
        }
    }
}