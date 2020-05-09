package com.df.playandroid.ui.content.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.adapter.WebViewMenuRvAdapter
import com.df.playandroid.base.bean.WebViewMenuItemBean
import com.df.playandroid.presenter.content.ContentPresenter
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.response.home.BannerData
import com.df.playandroid.view.content.IContentView
import com.df.playandroid.utils.DeviceUtil
import com.df.playandroid.utils.IntentUtil
import com.df.playandroid.utils.ToastUtil
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopups
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.base_article_header.*

class ContentActivity : BaseMvpActivity<IContentView, ContentPresenter>(), IContentView,
    View.OnClickListener {

    private var mQMUIPopup: QMUIPopup? = null
    private var mMenuItem: MutableList<WebViewMenuItemBean> = ArrayList()
    private lateinit var mMenuAdapter: WebViewMenuRvAdapter
    private var isCollect = false

    override fun getLayoutId() = R.layout.activity_content

    override fun initView() {
        initType()
        initWebView()
        initWebViewMenuAdapter()
        initPopup()
        header_menu_iv.visibility = View.VISIBLE
        header_back_iv.setOnClickListener(this)
        header_menu_iv.setOnClickListener(this)
    }

    private fun initType() {
        when(mType) {
            0 -> {
                isCollect = mArticle.collect
                header_title_tv.text = mArticle.title
            }
            1 -> header_title_tv.text = mBanner.title
            else -> header_title_tv.text = mTitle
        }
    }

    private fun initWebViewMenuAdapter() {
        mMenuAdapter = WebViewMenuRvAdapter(mMenuItem)
        if (mType == 0) {
            if (mArticle.collect) {
                mMenuItem.add(
                    WebViewMenuItemBean(
                        getString(R.string.article_had_collect),
                        R.mipmap.icon_collect
                    )
                )
            } else {
                mMenuItem.add(
                    WebViewMenuItemBean(
                        getString(R.string.article_collect),
                        R.mipmap.icon_uncollect
                    )
                )
            }
        }
        mMenuItem.add(WebViewMenuItemBean(getString(R.string.article_share), R.mipmap.icon_share))
        mMenuItem.add(
            WebViewMenuItemBean(
                getString(R.string.article_open_browser),
                R.mipmap.icon_browser
            )
        )
        mMenuAdapter.notifyDataSetChanged()
    }

    private fun initPopup() {
        if (mQMUIPopup == null) {
            mQMUIPopup = QMUIPopups.popup(this, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val menuLayout = LayoutInflater.from(this).inflate(R.layout.webview_menu, null)
        val menuRv = menuLayout.findViewById<RecyclerView>(R.id.webView_menu_rv)
        menuRv.layoutManager = LinearLayoutManager(this@ContentActivity)
        menuRv.adapter = mMenuAdapter
        mQMUIPopup?.run {
            preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
            view(menuLayout)
            edgeProtection(DeviceUtil.dp2px(5.0f))
            shadow(true)
            arrow(true)
            dimAmount(0.3f)
            animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
        }

        mMenuAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    if (isCollect.not()) {
                        mPresenter?.collectStationArticle(mArticle.id)
                    } else {
                        mPresenter?.unCollectArticle(mArticle.id)
                    }
                    mQMUIPopup?.dismiss()
                }
                1 -> {
                    // 分享
                    when (mType) {
                        0 -> mArticle.title?.let { mArticle.link?.let { it1 ->
                            IntentUtil.shareTo(this, it,
                                it1
                            )
                        } }
                        1 -> mBanner.title?.let { mBanner.url?.let { it1 ->
                            IntentUtil.shareTo(this, it,
                                it1
                            )
                        } }
                        2 -> IntentUtil.shareTo(this, mTitle, mUrl)
                    }
                    mQMUIPopup?.dismiss()
                }
                2 -> {
                    // 用浏览器打开
                    when (mType) {
                        0 -> mArticle.link?.let { IntentUtil.openBroswer(this, it) }
                        1 -> mBanner.url?.let { IntentUtil.openBroswer(this, it) }
                        2 -> IntentUtil.openBroswer(this, mUrl)
                    }
                    mQMUIPopup?.dismiss()
                }
            }
        }
    }

    /**
     * 收藏成功
     */
    override fun collectSuccess() {
        isCollect = true
        mMenuItem.removeAt(0)
        mMenuItem.add(
            0,
            WebViewMenuItemBean(
                getString(R.string.article_had_collect),
                R.mipmap.icon_collect
            )
        )
        mMenuAdapter.notifyItemChanged(0)
        ToastUtil.showToast(getString(R.string.article_collect_success))
    }

    /**
     * 取消收藏
     */
    override fun unCollectSuccess() {
        isCollect = false
        mMenuItem.removeAt(0)
        mMenuItem.add(
            0,
            WebViewMenuItemBean(
                getString(R.string.article_collect),
                R.mipmap.icon_uncollect
            )
        )
        mMenuAdapter.notifyItemChanged(0)
        ToastUtil.showToast(getString(R.string.article_un_collect_success))
    }

    private val mUrl by lazy {
        intent.getStringExtra("url")
    }

    private val mTitle by lazy {
        intent.getStringExtra("title")
    }

    private val mArticle by lazy {
        intent.getSerializableExtra("article") as ArticleInfo
    }

    private val mBanner by lazy {
        intent.getSerializableExtra("banner") as BannerData
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
            when (mType) {
                0 -> loadUrl(mArticle.link)
                1 -> loadUrl(mBanner.url)
                2 -> loadUrl(mUrl)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            header_back_iv -> finish()
            header_menu_iv -> mQMUIPopup?.show(header_menu_iv)
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
            .keyboardEnable(true)
            .navigationBarColor(R.color.white)
            .autoNavigationBarDarkModeEnable(true )
            .init()
    }

    companion object {
        fun openArticle(
            context: Context,
            article: ArticleInfo,
            type: Int
        ): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra("article", article)
                .putExtra("type", type)
        }

        fun openUrl(
            context: Context,
            url: String,
            title: String,
            type: Int
        ): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra("url", url)
                .putExtra("title", title)
                .putExtra("type", type)
        }

        fun openBanner(context: Context, banner: BannerData, type: Int): Intent {
            return Intent(context, ContentActivity::class.java)
                .putExtra("banner", banner)
                .putExtra("type", type)
        }
    }

    override fun setupPresenter() = ContentPresenter(this)
}
