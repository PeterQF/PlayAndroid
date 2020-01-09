package com.df.playandroid.base.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.df.playandroid.R
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.base_header.*

class WebViewActivity : BaseActivity(), View.OnClickListener {

    override fun getLayoutId() = R.layout.activity_web_view

    override fun initView() {
        initWebView()
        header_menu_iv.visibility = View.VISIBLE
        header_title_tv.text = mTitle
        header_back_iv.setOnClickListener(this)
        header_menu_iv.setOnClickListener(this)
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
            header_menu_iv -> {}
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
