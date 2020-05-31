package com.df.playandroid.ui.search.activity

import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.df.playandroid.R
import com.df.playandroid.base.activity.BaseMvpActivity
import com.df.playandroid.base.helper.LoadingViewHelper
import com.df.playandroid.config.SpConstants
import com.df.playandroid.expansion.OnTextChange
import com.df.playandroid.expansion.hideKeyboard
import com.df.playandroid.expansion.showKeyboard
import com.df.playandroid.presenter.search.SearchPresenter
import com.df.playandroid.response.article.ArticleData
import com.df.playandroid.response.article.ArticleInfo
import com.df.playandroid.ui.content.activity.ContentActivity
import com.df.playandroid.ui.home.adapter.HomeArticleRvAdapter
import com.df.playandroid.utils.LogUtil
import com.df.playandroid.utils.SPUtil
import com.df.playandroid.view.search.ISearchView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.base_refresh.*

class SearchActivity : BaseMvpActivity<ISearchView, SearchPresenter>(), ISearchView,
    View.OnClickListener {

    private lateinit var mAdapter: HomeArticleRvAdapter
    private var mItems: MutableList<ArticleInfo> = ArrayList()
    private var mPage = 0
    private var mIsOver = false
    private var mLastPosition = -1
    private var mKeyword: String? = null
    private var mIsFirstLoad = true
    private var mSearchHistoryList = ArrayList<String>()
    private var mDialog: QMUIDialog? = null

    override fun setupPresenter() = SearchPresenter(this)

    override fun getLayoutId() = R.layout.activity_search

    override fun initView() {
        initAdapter()
        initSearchEt()
        initRefreshEvent()
        initListener()
        initDialog()
    }

    private fun initDialog() {
        mDialog = QMUIDialog.MessageDialogBuilder(this)
            .setTitle(getString(R.string.search_dialog_title))
            .setMessage(getString(R.string.search_dialog_content))
            .addAction(getString(R.string.common_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .addAction(0, getString(R.string.common_delete), QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, _ ->
                cleanHistory()
                dialog.dismiss()
            }
            .create()
    }

    private fun cleanHistory() {
        SPUtil.removeKey(SpConstants.KEY_SEARCH_HISTORY_LIST)
        mSearchHistoryList.clear()
        mFloatLayout.removeAllViews()
    }

    private fun initListener() {
        mBackIv.setOnClickListener(this)
        mCleanEtIv.setOnClickListener(this)
        mCleanHistoryTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            mBackIv -> finish()
            mCleanEtIv -> mSearchEt.setText("")
            mCleanHistoryTv -> mDialog?.show()
        }
    }

    private fun initRefreshEvent() {
        mRefreshLayout.visibility = View.GONE
        mRefreshLayout.setEnableRefresh(false)
        mRefreshLayout.setEnableOverScrollDrag(true)
        mRefreshLayout.setEnableOverScrollBounce(true)
        mRefreshLayout.setOnLoadMoreListener {
            mKeyword?.let { key -> mPresenter?.goSearch(++mPage, key, true) }
        }
    }

    private fun initSearchEt() {
        showKeyboard(mSearchEt)
        mSearchEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(mSearchEt)
                mKeyword = mSearchEt.text.toString().trim()
                if (mKeyword != null && mKeyword != "") {
                    // search
                    startSearch(mKeyword!!)
                }
            }
            false
        }

        // EditText内容发生变化后
        mSearchEt.OnTextChange {
            setOnAfterTextChange {
                val text = mSearchEt.text
                if (text.isEmpty()) {
                    hideKeyboard(mSearchEt)
                    mSearchHistoryRl.visibility = View.VISIBLE
                    mRefreshLayout.visibility = View.GONE
                }
            }
        }
    }

    /**
     * 更新搜索历史
     */
    private fun updateSearchHistory() {
        if (mIsFirstLoad) {
            mSearchHistoryList.forEach {
                val view = LayoutInflater.from(this).inflate(R.layout.search_history_float_item, null)
                val textView = view as TextView
                textView.text = it
                textView.setOnClickListener { v ->
                    startSearch(it)
                    mSearchEt.setText(it)
                    hideKeyboard(mSearchEt)
                    mSearchEt.setSelection(it.length)
                }
                mFloatLayout.addView(textView)
            }
        } else {
            val newHistory = mSearchHistoryList.first()
            val view = LayoutInflater.from(this).inflate(R.layout.search_history_float_item, null)
            val textView = view as TextView
            textView.text = newHistory
            textView.setOnClickListener { v ->
                startSearch(newHistory)
                mSearchEt.setText(newHistory)
                hideKeyboard(mSearchEt)
                mSearchEt.setSelection(newHistory.length)
            }
            // 最新的添加到第一位
            mFloatLayout.addView(textView, 0)
            SPUtil.setList(SpConstants.KEY_SEARCH_HISTORY_LIST, mSearchHistoryList)
        }
    }

    private fun initAdapter() {
        mAdapter = HomeArticleRvAdapter(mItems)
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                ContentActivity.openArticle(
                    this,
                    mItems[position],
                    0
                )
            )
        }
    }

    override fun initData() {
        initSearchHistory()
    }

    private fun startSearch(keyword: String) {
        mPresenter?.goSearch(0, keyword)
    }

    /**
     * 初始化搜索历史
     */
    private fun initSearchHistory() {
        val historyList = SPUtil.getList(SpConstants.KEY_SEARCH_HISTORY_LIST)
        LogUtil.info("get search history list ----> $historyList")
        if (historyList.isNotEmpty()) {
            historyList.forEach {
                val s = it as String
                mSearchHistoryList.add(s)
            }
            updateSearchHistory()
            mIsFirstLoad = false
        } else {
            mIsFirstLoad = false
        }
    }

    override fun showLoadingView() {
        LoadingViewHelper.instance.show(mSearchRl)
    }

    override fun hideLoadingView() {
        LoadingViewHelper.instance.dismiss(mSearchRl)
    }

    override fun stopLoadMore() {
        if (mIsOver) {
            mRefreshLayout.finishLoadMoreWithNoMoreData()
        } else {
            mRefreshLayout.finishLoadMore()
        }
    }

    override fun getSearchResult(result: ArticleData, isLoadMore: Boolean, keyword: String) {
        val searchList = result.datas
        mIsOver = result.over
        if (searchList != null && searchList.isNotEmpty()) {
            mSearchHistoryRl.visibility = View.GONE
            mRefreshLayout.visibility = View.VISIBLE
            mLastPosition = if (isLoadMore) {
                mItems.addAll(searchList)
                mAdapter.notifyItemInserted(mLastPosition)
                mItems.count()
            } else {
                // 记录搜索关键字
                // 记录是否包含关键字，防止重复添加
                if (mSearchHistoryList.contains(keyword).not()) {
                    // 如果达到最大限制数则把最后一个删掉
                    if (mSearchHistoryList.count() == mFloatLayout.maxNumber) {
                        mSearchHistoryList.remove(mSearchHistoryList.last())
                    }
                    mSearchHistoryList.add(0, keyword)
                    updateSearchHistory()
                }
                mItems.clear()
                mItems.addAll(searchList)
                mAdapter.notifyDataSetChanged()
                mItems.count()
            }
        }
    }

    override fun onDestroy() {
        if (mSearchRl != null) {
            LoadingViewHelper.instance.dismiss(mSearchRl)
        }
        super.onDestroy()
    }

}
