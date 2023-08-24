package com.bearkinf.appinitprojectlatest

import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bearkinf.appinitprojectlatest.net.api.TestApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestApiService.service.listRepos("bearkinf")
            .concatMap {
                Log.v("bear", "it ${it}")
                TestApiService.service.listRepos("fdsavzcx fsadfsx")
                    .map {
                        "bearfdhsajk"
                    }
            }
            .concatMap {
                Log.v("bear", "it ${it}")
                TestApiService.service.listRepos("bearkinf")
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("bear", "it : ${it}")
            }, {
                it.printStackTrace()
                Log.e("bear", "error : ${it.message}")

                errorDialog(it.message)
            })
            .let {
                disposable.add(it)
            }


//        TestApiService.service2(TestApiService::class.java).listRepos("bearkinf")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.w("bear", "it : ${it}")
//            }, {
//                it.printStackTrace()
//            })
//            .let {
//                disposable.add(it)
//            }
//
//        TestApiService.service33(TestApiService::class.java).listRepos("bear")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("bear", "it : ${it}")
//            }, {
//                it.printStackTrace()
//            })
//            .let {
//                disposable.add(it)
//            }

    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


    private fun errorDialog(msg: String?) {

        AlertDialog.Builder(this@MainActivity)

            .setTitle("error")
            .setMessage(msg)
            .setCancelable(false)
            .setNegativeButton("확인", OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .create().show()


    }


}