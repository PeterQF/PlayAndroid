package com.df.playandroid.ui.main.activity

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.df.playandroid.R
import com.df.playandroid.application.AppManager
import com.df.playandroid.presenter.home.HomePresenter
import com.df.playandroid.view.home.IHomeView
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.ui.home.fragment.HomeFragment
import com.df.playandroid.ui.main.adapter.MainPageAdapter
import com.df.playandroid.ui.project.fragment.ProjectFragment
import com.df.playandroid.ui.system.fragment.ProjectSystemFragment
import com.df.playandroid.ui.officialaccount.fragment.OfficialAccountFragment
import com.df.playandroid.ui.user.fragment.UserFragment
import com.df.playandroid.utils.ToastUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : BaseMvpActivity<IHomeView, HomePresenter>(), TabLayout.OnTabSelectedListener {

    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = HomePresenter(this)

    override fun initView() {
        initFragment()
        mTabLayout.addOnTabSelectedListener(this)
    }

    override fun initData() {
        initTabAndViewPager()
    }

    private fun initTabAndViewPager() {
        val mainTitlesArray = resources.getStringArray(R.array.MainTabTitle)
        val mainIconsArray = resources.obtainTypedArray(R.array.MainTabIcon)
        mViewPager.adapter = MainPageAdapter(supportFragmentManager, lifecycle, fragments)
        mViewPager.isUserInputEnabled = false
        mViewPager.registerOnPageChangeCallback(mViewPagerCallback)
        mViewPager.offscreenPageLimit = 5
        TabLayoutMediator(mTabLayout, mViewPager, false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabView =
                    LayoutInflater.from(this).inflate(R.layout.layout_main_tab_custom, null)
                val tabTv = tabView.findViewById<TextView>(R.id.mTabTv)
                val tabIv = tabView.findViewById<ImageView>(R.id.mTabIv)
                tabTv.text = mainTitlesArray[position]
                tabIv.setImageDrawable(mainIconsArray.getDrawable(position))
                tab.customView = tabView
            }).attach()
        mainIconsArray.recycle()
    }

    private val mViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
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

    private fun initFragment() {
        fragments.add(HomeFragment())
        fragments.add(ProjectFragment())
        fragments.add(OfficialAccountFragment())
        fragments.add(ProjectSystemFragment())
        fragments.add(UserFragment())
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        val tabIv = tab?.customView?.findViewById<ImageView>(R.id.mTabIv)
        val tabTv = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        tabTv?.setTextColor(ContextCompat.getColor(this, R.color.color_main_sub_text))
        tabIv?.isSelected = false
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val tabIv = tab?.customView?.findViewById<ImageView>(R.id.mTabIv)
        val tabTv = tab?.customView?.findViewById<TextView>(R.id.mTabTv)
        tabTv?.setTextColor(ContextCompat.getColor(this, R.color.mainColor))
        tabIv?.isSelected = true
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
