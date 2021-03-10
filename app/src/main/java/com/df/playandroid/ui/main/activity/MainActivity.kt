package com.df.playandroid.ui.main.activity

import androidx.viewpager2.widget.ViewPager2
import com.df.playandroid.R
import com.df.playandroid.application.AppManager
import com.df.playandroid.presenter.home.HomePresenter
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.ui.main.adapter.MainPageAdapter
import com.df.playandroid.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : BaseMvpActivity<IHomeView, HomePresenter>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = HomePresenter(this)

    override fun initView() {
        initPage()
    }

    override fun initData() {}

    private fun initPage() {
        mViewPager2.apply {
            isUserInputEnabled = false
            offscreenPageLimit = 5
            adapter = MainPageAdapter(this@MainActivity)
            registerOnPageChangeCallback(mViewPagerCallback)
        }
        mBottomView.apply {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.homeFragment -> mViewPager2.setCurrentItem(0, false)
                    R.id.projectFragment -> mViewPager2.setCurrentItem(1, false)
                    R.id.officialAccountFragment -> mViewPager2.setCurrentItem(2, false)
                    R.id.projectSystemFragment -> mViewPager2.setCurrentItem(3, false)
                    R.id.userFragment -> mViewPager2.setCurrentItem(4, false)
                }
                true
            }
        }
    }

    private val mViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mBottomView.menu.getItem(position).isChecked = true
            when (position) {
                0, 1, 2, 3 -> {
                    ImmersionBar
                        .with(this@MainActivity)
                        .reset()
                        .fitsSystemWindows(true)
                        .keyboardEnable(false)
                        .statusBarDarkFont(true, 0.2f)
                        .statusBarColor(R.color.white).navigationBarDarkIcon(true)
                        .navigationBarColor(R.color.white)
                        .init()
                }
                4 -> {
                    ImmersionBar
                        .with(this@MainActivity)
                        .reset()
                        .transparentStatusBar()
                        .keyboardEnable(false)
                        .statusBarDarkFont(false)
                        .navigationBarColor(R.color.white)
                        .navigationBarDarkIcon(true)
                        .init()
                }
            }
        }
    }

    private var backPressedTime by Delegates.observable(0L) { pre, old, new ->
        if (new - old < 1000) {
            AppManager.instance.exitApplication(this)
        } else {
            ToastUtil.showToast(getString(R.string.common_exit_app))
        }
    }

    override fun initStatusBar() {}

    override fun onBackPressed() {
        backPressedTime = System.currentTimeMillis()
    }
}
