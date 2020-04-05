package com.df.playandroid.ui.main.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.df.playandroid.R
import com.df.playandroid.application.AppManager
import com.df.playandroid.presenter.home.HomePresenter
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.ui.home.fragment.HomeFragment
import com.df.playandroid.ui.project.fragment.ProjectFragment
import com.df.playandroid.ui.system.fragment.ProjectSystemFragment
import com.df.playandroid.ui.officialaccount.fragment.OfficialAccountFragment
import com.df.playandroid.ui.user.fragment.UserFragment
import com.df.playandroid.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : BaseMvpActivity<IHomeView, HomePresenter>() {

    private var lastIndex = 0
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = HomePresenter(this)

    override fun initView() {
        initFragment()
    }

    private fun initFragment() {

        fragments.add(HomeFragment())
        fragments.add(ProjectFragment())
        fragments.add(OfficialAccountFragment())
        fragments.add(ProjectSystemFragment())
        fragments.add(UserFragment())

        main_group.setOnCheckedChangeListener { radioGroup, checkId ->
            when (checkId) {
                R.id.main_home -> {
                    setFragmentPosition(0)
                    ImmersionBar.with(this).keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_project_recommend -> {
                    setFragmentPosition(1)
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_public_recommend -> {
                    setFragmentPosition(2)
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_project_system -> {
                    setFragmentPosition(3)
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_user -> {
                    setFragmentPosition(4)
                    ImmersionBar.with(this).reset().keyboardEnable(true).transparentStatusBar().navigationBarColor(R.color.white).init()
                }
            }
        }
        main_home.isChecked = true
    }

    override fun initStatusBar() {

    }

    private fun setFragmentPosition(position: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val currentFragment: Fragment = fragments[position]
        val lastFragment: Fragment = fragments[lastIndex]
        lastIndex = position
        ft.hide(lastFragment)
        if (!currentFragment.isAdded) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            ft.add(R.id.main_frame, currentFragment)
            ft.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        }
        ft.show(currentFragment)
        ft.commit()
    }

    private var backPressedTime by Delegates.observable(0L) { pre, old, new ->
        if (new - old < 1000) {
            AppManager.instance.exitApplication(this)
        } else {
            ToastUtil.showToast(getString(R.string.common_exit_app))
        }
    }

    override fun onBackPressed() {
        backPressedTime = System.currentTimeMillis()
    }

    override fun initData() {}
}
