package com.df.playandroid.content.activity

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
import com.df.playandroid.base.activity.BaseActivity
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.adapter.WebViewMenuRvAdapter
import com.df.playandroid.base.bean.WebViewMenuItemBean
import com.df.playandroid.content.presenter.ContentPresenter
import com.df.playandroid.content.view.ContentView
import com.df.playandroid.home.response.BannerResponse
import com.df.playandroid.home.response.HomeArticleResponse
import com.df.playandroid.utils.DeviceUtil
import com.df.playandroid.utils.IntentUtil
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.base_article_header.*

class WebViewActivity : BaseMvpActivity<ContentView, ContentPresenter>(), View.OnClickListener {

    private var mQMUIPopup: QMUIPopup? = null
    private var mMenuItem: MutableList<WebViewMenuItemBean> = ArrayList()
    private lateinit var mMenuAdapter: WebViewMenuRvAdapter

    override fun getLayoutId() = R.layout.activity_web_view

    override fun initView() {
        initWebView()
        initWebViewMenuAdapter()
        initPopup()
        header_title_tv.text = when(mType) {
            0 -> mItem.title
            else -> mBanner.title
        }
        header_menu_iv.visibility = View.VISIBLE
        header_back_iv.setOnClickListener(this)
        header_menu_iv.setOnClickListener(this)
    }

    private fun initWebViewMenuAdapter() {
        mMenuAdapter = WebViewMenuRvAdapter(mMenuItem)
        mMenuItem.add(WebViewMenuItemBean(getString(R.string.article_collect), R.mipmap.icon_collect))
        mMenuItem.add(WebViewMenuItemBean(getString(R.string.article_share), R.mipmap.icon_share))
        mMenuItem.add(WebViewMenuItemBean(getString(R.string.article_open_browser), R.mipmap.icon_browser))
        mMenuAdapter.notifyDataSetChanged()
    }

    private fun initPopup() {
        if (mQMUIPopup == null) mQMUIPopup = QMUIPopup(this, QMUIPopup.DIRECTION_NONE)
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
        mMenuAdapter.setOnItemClickListener { adapter, view, position ->
            when(position) {
                0 -> {
                    // TODO 收藏
                    mPresenter?.collectStationArticle(mItem.id)
                    mQMUIPopup?.dismiss()
                }
                1 -> {
                    // 分享
                    when(mType) {
                        0 -> IntentUtil.shareTo(this, mItem.title, mItem.link)
                        else -> IntentUtil.shareTo(this, mBanner.title, mBanner.url)
                    }
                    mQMUIPopup?.dismiss()
                }
                2 -> {
                    // 用浏览器打开
                    when(mType) {
                        0 -> IntentUtil.openBroswer(this, mItem.link)
                        else -> IntentUtil.openBroswer(this, mBanner.url)
                    }
                    mQMUIPopup?.dismiss()
                }
            }
        }
    }

//    private val mUrl by lazy {
//        intent.getStringExtra("url")
//    }
//
//    private val mTitle by lazy {
//        intent.getStringExtra("title")
//    }
//
//    private val mId by lazy {
//        intent.getIntExtra("id", 0)
//    }

    private val mItem by lazy {
        intent.getSerializableExtra("item") as HomeArticleResponse.ArticleData.ArticleInfo
    }

    private val mBanner by lazy {
        intent.getSerializableExtra("banner") as BannerResponse.BannerData
    }

    private val mType by lazy {
        intent.getIntExtra("type", 0)
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
//            mUrl.takeIf { it.isNullOrEmpty().not() }?.let { loadUrl(it) }
            when(mType) {
                0 -> loadUrl(mItem.link)
                else -> loadUrl(mBanner.url)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            header_back_iv -> finish()
            header_menu_iv -> {
                mQMUIPopup?.let {
                    if (it.isShowing.not()) it.show(header_menu_iv)
                }
            }
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
        fun openWeb(context: Context, item: HomeArticleResponse.ArticleData.ArticleInfo, type: Int): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra("item", item)
                .putExtra("type", type)
//                .putExtra("id", id)
//                .putExtra("url", url)
//                .putExtra("title", title)
        }

        fun openBanner(context: Context, banner: BannerResponse.BannerData, type: Int): Intent {
            return Intent(context, WebViewActivity::class.java)
                .putExtra("banner", banner)
                .putExtra("type", type)
        }
    }

    override fun setupPresenter() = ContentPresenter(this)
}