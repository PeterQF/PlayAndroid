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
import com.df.playandroid.ui.projectrecommend.fragment.ProjectRecommendFragment
import com.df.playandroid.ui.system.fragment.ProjectSystemFragment
import com.df.playandroid.ui.officialaccount.fragment.OfficialAccountFragment
import com.df.playandroid.ui.user.fragment.UserFragment
import com.df.playandroid.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : BaseMvpActivity<IHomeView, HomePresenter>() {

    private lateinit var mTransition: FragmentTransaction
    private var mCurrentFragment = Fragment()

    private var lastIndex = 0
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = HomePresenter(this)

    override fun initView() {
        initFragment()
    }

    private fun initFragment() {
//        val homeFragment = HomeFragment()
//        val projectRecommendFragment = ProjectRecommendFragment()
//        val publicRecommendFragment = OfficialAccountFragment()
//        val projectSystemFragment = ProjectSystemFragment()
//        val userFragment = UserFragment()

        fragments.add(HomeFragment())
        fragments.add(ProjectRecommendFragment())
        fragments.add(OfficialAccountFragment())
        fragments.add(ProjectSystemFragment())
        fragments.add(UserFragment())

        main_group.setOnCheckedChangeListener { radioGroup, checkId ->
            var mFragment: Fragment? = null
            when (checkId) {
                R.id.main_home -> {
                    setFragmentPosition(0)
//                    mFragment = homeFragment
                    ImmersionBar.with(this).keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
//                    ImmersionBar.with(this).reset().keyboardEnable(true).transparentStatusBar().navigationBarColor(R.color.white).statusBarDarkFont(false).init()
                }
                R.id.main_project_recommend -> {
                    setFragmentPosition(1)
//                    mFragment = projectRecommendFragment
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_public_recommend -> {
                    setFragmentPosition(2)
//                    mFragment = publicRecommendFragment
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).init()
                }
                R.id.main_project_system -> {
                    setFragmentPosition(3)
//                    mFragment = projectSystemFragment
                    ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.mainColor).statusBarDarkFont(false).navigationBarColor(R.color.white).init()
                }
                R.id.main_user -> {
                    setFragmentPosition(4)
//                    mFragment = userFragment
                    ImmersionBar.with(this).reset().keyboardEnable(true).transparentStatusBar().navigationBarColor(R.color.white).init()
                }
            }
//            mFragment?.let { switchFragment(it) }
        }
        main_home.isChecked = true
    }

    override fun initStatusBar() {

    }

    /**
     * 切换fragment
     */
//    private fun switchFragment(fragment: Fragment) {
//        mTransition = supportFragmentManager.beginTransaction()
//        if (mCurrentFragment != fragment){
//            mTransition.hide(mCurrentFragment)
//            mCurrentFragment = fragment
//            if (!fragment.isAdded){
//                mTransition.add(R.id.main_frame,fragment).show(fragment).commit()
//            }else{
//                mTransition.show(fragment).commit()
//            }
//        }
//    }

    private fun setFragmentPosition(position: Int) {
//        if (position == 4) {
//            ImmersionBar.with(this).reset().keyboardEnable(true).transparentStatusBar().navigationBarColor(R.color.white).init()
//        } else {
//            ImmersionBar.with(this).reset().keyboardEnable(true).fitsSystemWindows(true).statusBarColor(R.color.mainColor).statusBarDarkFont(false).navigationBarColor(R.color.white).init()
//        }
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
