package com.df.playandroid.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.df.playandroid.R
import com.df.playandroid.base.adapter.WebViewMenuRvAdapter
import com.df.playandroid.base.bean.WebViewMenuItemBean
import com.df.playandroid.utils.DeviceUtil
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.base_header.*

class WebViewActivity : BaseActivity(), View.OnClickListener {

    private var mQMUIPopup: QMUIPopup? = null
    private var mMenuItem: MutableList<WebViewMenuItemBean> = ArrayList()
    private lateinit var mMenuAdapter: WebViewMenuRvAdapter

    override fun getLayoutId() = R.layout.activity_web_view

    override fun initView() {
        initWebView()
        initWebViewMenuAdapter()
        initPopup()
        header_title_tv.text = mTitle
        header_menu_iv.visibility = View.VISIBLE
        header_back_iv.setOnClickListener(this)
        header_menu_iv.setOnClickListener(this)
    }

    private fun initWebViewMenuAdapter() {
        mMenuAdapter = WebViewMenuRvAdapter(mMenuItem)
        mMenuItem.add(WebViewMenuItemBean("收藏", R.mipmap.icon_collect))
        mMenuItem.add(WebViewMenuItemBean("分享", R.mipmap.icon_share))
        mMenuItem.add(WebViewMenuItemBean("用浏览器打开", R.mipmap.icon_browser))
        mMenuAdapter.notifyDataSetChanged()
    }

    private fun initPopup() {
        if (mQMUIPopup == null) {
            mQMUIPopup = QMUIPopup(this, QMUIPopup.DIRECTION_NONE)
        }
        val menuLayout = LayoutInflater.from(this).inflate(R.layout.webview_menu, null)
        val menuRv = menuLayout.findViewById<RecyclerView>(R.id.webView_menu_rv)
        mQMUIPopup?.run {
            val params = generateLayoutParam(
                DeviceUtil.getScreenWidth() / 2,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(menuLayout)
            menuLayout.layoutParams = params
            menuRv.layoutManager = LinearLayoutManager(this@WebViewActivity)
            menuRv.adapter = mMenuAdapter
            setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
            setPreferredDirection(QMUIPopup.DIRECTION_NONE)
            setOnDismissListener {

            }
            if (!isShowing) show(header_menu_iv)
        }
    }

    private val mUrl by lazy {
        intent.getStringExtra("url")
    }

    private val mTitle by lazy {
        intent.getStringExtra("title")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        mWebView.run {
            webViewClient = QMUIWebViewClient(true, true)
            webChromeClient = mWebViewChromeClient
            settings.apply {
                javaScriptCanOpenWindowsAutomatically = true
                javaScriptEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                useWideViewPort = true
                loadWithOverviewMode = true
                setAppCacheEnabled(true)
                domStorageEnabled = true
            }
            mUrl.takeIf { it.isNullOrEmpty().not() }?.let { loadUrl(it) }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            header_back_iv -> finish()
            header_menu_iv -> initPopup()
        }
    }

    private val mWebViewClient = object : QMUIWebViewClient(true, true) {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private val mWebViewChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                mProgressBar.progress = newProgress
                mProgressBar.visibility = View.GONE
            } else {
                mProgressBar.visibility = View.VISIBLE
                mProgressBar.progress = newProgress
            }
        }
    }

    override fun initData() {}

    override fun initStatusBar() {
        ImmersionBar
            .with(this)
            .statusBarColor(R.color.mainColor)
            .init()
    }

    companion object {
        fun openWeb(context: Context, title: String, url: String): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra("url", url)
                .putExtra("title", title)
        }
    }
}
