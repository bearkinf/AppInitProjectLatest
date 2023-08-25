package com.bearkinf.appinitprojectlatest.net.action

import android.util.Log
import com.bearkinf.appinitprojectlatest.net.api.TestApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

object GithubAction {

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val clearDisposable by lazy {
        Log.v("bear","call Disposable")
        disposable.dispose()
    }

//    fun runDisposable() = disposable.dispose()

    fun getUserRepos(
        user: String, callback: ((success: Boolean, msg: String?) -> Unit)? = null
    ) {
        Log.w("bear", "getUserRepos")
        TestApiService.service.listRepos(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback?.invoke(true, it)
            }, {
                callback?.invoke(false, it.message)
            })
            .let {
                disposable.add(it)
            }
    }


}