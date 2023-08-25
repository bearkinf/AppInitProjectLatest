package com.bearkinf.appinitprojectlatest.net.action

import android.util.Log
import com.bearkinf.appinitprojectlatest.net.api.TestApiClient
import com.bearkinf.appinitprojectlatest.net.api.TestApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

object GithubAction {

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    // by lazy 를 안하면 바로 disposable 된다.
    // 함수 사용 또는 by lazy
    val clearDisposable by lazy {
        Log.v("bear", "call Disposable")
        disposable.dispose()
    }

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

    //lambda 식 으로 구현.
    fun getUserRepos2(
        user: String,
        success: ((msg: String?) -> Unit)? = null,
        fail: ((throwable: String?) -> Unit)? = null
    ) {
        Log.w("bear", "getUserRepos")
        TestApiService.service.listRepos(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success?.invoke(it)
            }, {
                fail?.invoke(it.message)
            })
            .let {
                disposable.add(it)
            }
    }

    fun getUserRepos3(
        user: String,
        success: ((msg: String?) -> Unit)? = null,
        fail: ((throwable: String?) -> Unit)? = null
    ) {
        Log.w("bear", "getUserRepos")
        TestApiClient.service(TestApiService::class.java).listRepos(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success?.invoke(it)
            }, {
                fail?.invoke(it.message)
            })
            .let {
                disposable.add(it)
            }
    }

}