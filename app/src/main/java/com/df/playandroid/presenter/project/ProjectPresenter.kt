package com.df.playandroid.presenter.project

import android.content.Context
import com.df.playandroid.base.observer.BaseObserver
import com.df.playandroid.base.presenter.BasePresenter
import com.df.playandroid.http.ApiRetrofit
import com.df.playandroid.response.category.CategoryResponse
import com.df.playandroid.view.project.IProjectView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 作者：
 * 时间：2020/1/1
 * 描述：
 */
class ProjectPresenter(context: Context) : BasePresenter<IProjectView>(context) {

    /**
     * 项目分类
     */
    fun getProjectCategory() {
        ApiRetrofit
            .instance
            .api
            .requestProjectCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<CategoryResponse>() {
                override fun onFailed() {}

                override fun onResult(
                    errorCode: Int,
                    errorMsg: String?,
                    result: CategoryResponse
                ) {
                    result.data?.let { getView()?.getProjectCategorySuccess(it) }
                }

                override fun addDisposable(d: Disposable) {
                    mDisposables.add(d)
                }
            })
    }
}