package com.df.playandroid.base.activity

import android.content.Context
import android.content.Intent
import android.widget.FrameLayout
import com.df.playandroid.R
import com.df.playandroid.ui.content.activity.ContentActivity
import com.gyf.immersionbar.ImmersionBar
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_agent_web.*

class AgentWebActivity : BaseActivity() {

    private var mAgentWeb: AgentWeb? = null

    override fun getLayoutId() = R.layout.activity_agent_web

    override fun initView() {
        mAgentWeb = AgentWeb
            .with(this)
            .setAgentWebParent(mAgentWebFl, FrameLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    private val mUrl by lazy {
        intent.getStringExtra("url")
    }

    override fun initData() {}

    override fun initStatusBar() {
        ImmersionBar
            .with(this)
            .statusBarColor(R.color.mainColor)
            .init()
    }

    companion object {
        fun openWeb(context: Context, url: String): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra("url", url)
        }
    }
}
