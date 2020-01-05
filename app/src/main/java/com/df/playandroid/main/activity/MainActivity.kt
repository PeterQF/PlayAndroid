package com.df.playandroid.main.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.widget.RadioGroup
import com.df.playandroid.R
import com.df.playandroid.home.presenter.HomePresenter
import com.df.playandroid.home.view.IHomeView
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.home.fragment.HomeFragment
import com.df.playandroid.project_recommend.fragment.ProjectRecommendFragment
import com.df.playandroid.project_system.fragment.ProjectSystemFragment
import com.df.playandroid.public_recommend.fragment.PublicRecommendFragment
import com.df.playandroid.user.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<IHomeView, HomePresenter>() {

    private lateinit var mTransition: FragmentTransaction
    private var mCurrentFragment = Fragment()

    override fun getLayoutId() = R.layout.activity_main

    override fun setupPresenter() = HomePresenter(this)

    override fun initView() {
        initFragment()
    }

    private fun initFragment() {
        val homeFragment = HomeFragment()
        val projectRecommendFragment = ProjectRecommendFragment()
        val publicRecommendFragment = PublicRecommendFragment()
        val projectSystemFragment = ProjectSystemFragment()
        val userFragment = UserFragment()
        main_group.setOnCheckedChangeListener { radioGroup, checkId ->
            var mFragment: Fragment? = null
            when(checkId) {
                R.id.main_home -> mFragment = homeFragment
                R.id.main_project_recommend -> mFragment = projectRecommendFragment
                R.id.main_public_recommend -> mFragment = publicRecommendFragment
                R.id.main_project_system -> mFragment = projectSystemFragment
                R.id.main_user -> mFragment = userFragment
            }
            mFragment?.let { switchFragment(it) }
        }
        main_home.isChecked = true
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(fragment: Fragment) {
        mTransition = supportFragmentManager.beginTransaction()
        if (mCurrentFragment != fragment){
            mTransition.hide(mCurrentFragment)
            mCurrentFragment = fragment
            if (!fragment.isAdded){
                mTransition.add(R.id.main_frame,fragment).show(fragment).commit()
            }else{
                mTransition.show(fragment).commit()
            }
        }
    }

    override fun initData() {}
}
